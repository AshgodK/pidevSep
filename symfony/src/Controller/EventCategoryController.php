<?php

namespace App\Controller;

use App\Entity\EventCategory;
use App\Form\EventCategoryType;
use App\Repository\EventCategoryRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;

#[Route('/event/category')]
class EventCategoryController extends AbstractController
{
    #[Route('/', name: 'app_event_category_index', methods: ['GET'])]
    public function index(Request $request, PaginatorInterface $paginator, EventCategoryRepository $eventCategoryRepository): Response
    {
        $query = $eventCategoryRepository->createQueryBuilder('p')
            ->orderBy('p.id', 'ASC')
            ->getQuery();

        $pagination = $paginator->paginate(
            $query,
            $request->query->getInt('page', 1),
            2
        );



        return $this->render('event_category/index.html.twig', [

            'event_categories' => $pagination,

        ]);
    }

    #[Route('/fronti', name: 'app_event_category_front_index', methods: ['GET'])]
    public function indexF(EventCategoryRepository $eventCategoryRepository): Response
    {
        return $this->render('event_category/frontindex.html.twig', [
            'event_categories' => $eventCategoryRepository->findAll(),
        ]);
    }

    #[Route('/fronti/{id}', name: 'app_event_category_front_show', methods: ['GET'])]
    public function showF(EventCategory $eventCategory): Response
    {
        return $this->render('event_category/frontshow.html.twig', [
            'event_category' => $eventCategory,
        ]);
    }

    #[Route('/new', name: 'app_event_category_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EventCategoryRepository $eventCategoryRepository): Response
    {
        $eventCategory = new EventCategory();
        $form = $this->createForm(EventCategoryType::class, $eventCategory);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $eventCategoryRepository->save($eventCategory, true);

            return $this->redirectToRoute('app_event_category_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('event_category/new.html.twig', [
            'event_category' => $eventCategory,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_event_category_show', methods: ['GET'])]
    public function show(EventCategory $eventCategory): Response
    {
        return $this->render('event_category/show.html.twig', [
            'event_category' => $eventCategory,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_event_category_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, EventCategory $eventCategory, EventCategoryRepository $eventCategoryRepository): Response
    {
        $form = $this->createForm(EventCategoryType::class, $eventCategory);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $eventCategoryRepository->save($eventCategory, true);

            return $this->redirectToRoute('app_event_category_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('event_category/edit.html.twig', [
            'event_category' => $eventCategory,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_event_category_delete', methods: ['POST'])]
    public function delete(Request $request, EventCategory $eventCategory, EventCategoryRepository $eventCategoryRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $eventCategory->getId(), $request->request->get('_token'))) {
            $eventCategoryRepository->remove($eventCategory, true);
        }

        return $this->redirectToRoute('app_event_category_index', [], Response::HTTP_SEE_OTHER);
    }
}
