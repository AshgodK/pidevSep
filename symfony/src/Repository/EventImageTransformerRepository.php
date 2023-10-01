<?php

namespace App\Repository;

use App\Entity\EventImageTransformer;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<EventImageTransformer>
 *
 * @method EventImageTransformer|null find($id, $lockMode = null, $lockVersion = null)
 * @method EventImageTransformer|null findOneBy(array $criteria, array $orderBy = null)
 * @method EventImageTransformer[]    findAll()
 * @method EventImageTransformer[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EventImageTransformerRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, EventImageTransformer::class);
    }

    public function save(EventImageTransformer $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(EventImageTransformer $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

//    /**
//     * @return EventImageTransformer[] Returns an array of EventImageTransformer objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('e.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?EventImageTransformer
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
