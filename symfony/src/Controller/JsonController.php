<?php

namespace App\Controller;

use App\Entity\Evennement;
use App\Entity\EventCategory;
use App\Repository\EvennementRepository;
use App\Repository\EventCategoryRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\AbstractNormalizer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Validator\Validator\ValidatorInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Component\Mime\Address;

class JsonController extends AbstractController
{
    #[Route('/json', name: 'app_json')]
    public function index(): Response
    {
        return $this->render('json/index.html.twig', [
            'controller_name' => 'JsonController',
        ]);
    }

    #[Route('/json/getAll', name: 'app_evenement_JSON', methods: ['GET'])]
    public function index_JSON(SerializerInterface $serializer, EvennementRepository $evennementRepository): JsonResponse
    {
        $evenements = $evennementRepository->findAll();
        $json = $serializer->serialize($evenements, 'json', [
            AbstractNormalizer::IGNORED_ATTRIBUTES => ['category', 'passe'],
        ]);

        return new JsonResponse($json, 200, [], true);
    }

    #[Route('/json/getAllSorted', name: 'app_evenement_JSONSorted', methods: ['GET'])]
    public function index_JSONSorted(SerializerInterface $serializer, EvennementRepository $evennementRepository): JsonResponse
    {
        $queryBuilder = $evennementRepository->createQueryBuilder('e')
            ->orderBy('e.titre', 'ASC');

        $evenements = $queryBuilder->getQuery()->getResult();
        $json = $serializer->serialize($evenements, 'json', [
            AbstractNormalizer::IGNORED_ATTRIBUTES => ['category', 'passe'],
        ]);

        return new JsonResponse($json, 200, [], true);
    }

    #[Route('/json/getAllcats', name: 'app_cats_JSON', methods: ['GET'])]
    public function index_JSONcats(SerializerInterface $serializer, EventCategoryRepository $evennementRepository): JsonResponse
    {
        $evenements = $evennementRepository->findAll();
        $json = $serializer->serialize($evenements, 'json', [
            AbstractNormalizer::IGNORED_ATTRIBUTES => ['evennements'],
        ]);

        return new JsonResponse($json, 200, [], true);
    }

    #[Route('/json/delete', name: 'app_evenement_delete_JSON', methods: ['GET'])]
    public function delete_JSON(Request $request, EvennementRepository $evennementRepository): Response
    {
        $id = $request->get("id");

        $evenement = $evennementRepository->find($id);

        if ($evenement != null) {

            $evennementRepository->remove($evenement, true);

            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize("evenement has been deleted successfully.");
            return new JsonResponse($formatted);
        }

        $formatted = ["error" => "Invalid evenement ID."];
        return new JsonResponse($formatted);
    }

    #[Route('/json/deletec', name: 'app_c_delete_JSON', methods: ['GET'])]
    public function deletec(Request $request, EventCategoryRepository $evennementRepository): Response
    {
        $id = $request->get("id");

        $evenement = $evennementRepository->find($id);

        if ($evenement != null) {

            $evennementRepository->remove($evenement, true);

            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize("Category has been deleted successfully.");
            return new JsonResponse($formatted);
        }

        $formatted = ["error" => "Invalid category ID."];
        return new JsonResponse($formatted);
    }




    #[Route('/json/new', name: 'create_evenement', methods: ['GET'])]
    public function createevenementAction(Request $request, ValidatorInterface $validator, SerializerInterface $serializer, EvennementRepository $evennementRepository, EventCategoryRepository $evr, MailerInterface $mailer): JsonResponse
    {
        $titre = $request->get('titre');
        $description = $request->get('description');
        $dateDebut = new \DateTime($request->get('dateDebut'));
        $dateFin = new \DateTime($request->get('dateFin'));
        $prix = (float)$request->get('prix');
        $adresse = $request->get('adresse');
        $nbrp = (int)$request->get('nbrp');
        $idc = (int)$request->get('idc');


        $evennement = new Evennement();
        $evennement->setTitre($titre);
        $evennement->setDescription($description);
        $evennement->setDateDebut($dateDebut);
        $evennement->setDateFin($dateFin);
        $evennement->setPrix($prix);
        $evennement->setAdresse($adresse);
        $evennement->setNbrp($nbrp);
        $evennement->setCategory($evr->FindOneById($idc));


        // Validate the entity
        $errors = $validator->validate($evennement);
        if (count($errors) > 0) {
            $errorMessages = [];
            foreach ($errors as $error) {
                $errorMessages[$error->getPropertyPath()] = $error->getMessage();
            }
            return new JsonResponse(['errors' => $errorMessages], Response::HTTP_BAD_REQUEST);
        }

        // Save the entity to the database
        $evennementRepository->save($evennement, true);

        // Return a JSON response with the serialized entity data
        $jsonContent = $serializer->serialize($evennement, 'json', [
            AbstractNormalizer::IGNORED_ATTRIBUTES => ['category', 'passe', 'zone', 'nbrPasse'],
        ]);
        $email = (new TemplatedEmail())
            ->from(new Address('mailjavam@gmail.com', 'UltraHealth Bot'))
            ->to('achref.essid@esprit.tn')
            ->subject('Ajout ')
            ->htmlTemplate('mailTemplate.html.twig');

        $mailer->send($email);
        return new JsonResponse($jsonContent, Response::HTTP_CREATED, [], true);
    }

    #[Route('/json/newCat', name: 'create_cat', methods: ['GET'])]
    public function addcat(Request $request, ValidatorInterface $validator, SerializerInterface $serializer, EventCategoryRepository $evennementRepository): JsonResponse
    {
        $titre = $request->get('titre');
        $description = $request->get('description');


        $c = new EventCategory();
        $c->setTitre($titre);
        $c->setDescription($description);
        // Validate the entity  
        $errors = $validator->validate($c);
        if (count($errors) > 0) {
            $errorMessages = [];
            foreach ($errors as $error) {
                $errorMessages[$error->getPropertyPath()] = $error->getMessage();
            }
            return new JsonResponse(['errors' => $errorMessages], Response::HTTP_BAD_REQUEST);
        }

        // Save the entity to the database
        $evennementRepository->save($c, true);

        // Return a JSON response with the serialized entity data
        $jsonContent = $serializer->serialize($c, 'json', [
            AbstractNormalizer::IGNORED_ATTRIBUTES => ['evennements'],
        ]);

        return new JsonResponse($jsonContent, Response::HTTP_CREATED, [], true);
    }

    #[Route('/json/edit', name: 'edit_evenement', methods: ['GET'])]
    public function editEvenementAction(Request $request, ValidatorInterface $validator, SerializerInterface $serializer, EvennementRepository $evennementRepository): JsonResponse
    {
        $evennement = $evennementRepository->find($request->get('id'));
        if (!$evennement) {
            return new JsonResponse(['error' => 'evenement not found'], Response::HTTP_NOT_FOUND);
        }

        $titre = $request->get('titre');
        $description = $request->get('description');
        $dateDebut = new \DateTime($request->get('dateDebut'));
        $dateFin = new \DateTime($request->get('dateFin'));
        $prix = (float)$request->get('prix');
        $adresse = $request->get('adresse');
        $nbrp = (int)$request->get('nbrp');

        $evennement->setTitre($titre);
        $evennement->setDescription($description);
        $evennement->setDateDebut($dateDebut);
        $evennement->setDateFin($dateFin);
        $evennement->setPrix($prix);
        $evennement->setAdresse($adresse);
        $evennement->setNbrp($nbrp);

        // Validate the entity
        $errors = $validator->validate($evennement);
        if (count($errors) > 0) {
            $errorMessages = [];
            foreach ($errors as $error) {
                $errorMessages[$error->getPropertyPath()] = $error->getMessage();
            }
            return new JsonResponse(['errors' => $errorMessages], Response::HTTP_BAD_REQUEST);
        }

        // Save the entity to the database
        $evennementRepository->save($evennement, true);

        $jsonContent = $serializer->serialize($evennement, 'json');
        return new JsonResponse($jsonContent, Response::HTTP_OK, [], true);
    }

    #[Route('/json/editc', name: 'edit_c', methods: ['GET'])]
    public function editc(Request $request, ValidatorInterface $validator, SerializerInterface $serializer, EventCategoryRepository $evennementRepository): JsonResponse
    {
        $c = $evennementRepository->find($request->get('id'));
        if (!$c) {
            return new JsonResponse(['error' => 'evenement not found'], Response::HTTP_NOT_FOUND);
        }

        $titre = $request->get('titre');
        $description = $request->get('description');


        $c->setTitre($titre);
        $c->setDescription($description);

        // Validate the entity
        $errors = $validator->validate($c);
        if (count($errors) > 0) {
            $errorMessages = [];
            foreach ($errors as $error) {
                $errorMessages[$error->getPropertyPath()] = $error->getMessage();
            }
            return new JsonResponse(['errors' => $errorMessages], Response::HTTP_BAD_REQUEST);
        }

        // Save the entity to the database
        $evennementRepository->save($c, true);

        $jsonContent = $serializer->serialize($c, 'json');
        return new JsonResponse($jsonContent, Response::HTTP_OK, [], true);
    }
}
