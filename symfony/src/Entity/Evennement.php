<?php

namespace App\Entity;

use App\Repository\EvennementRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: EvennementRepository::class)]
class Evennement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "titre evennement ne peux pas être vide! ")]
    #[Assert\Length(
        min: 3,
        max: 10,
        minMessage: "The name must be at least {{ limit }} characters long",
        maxMessage: "The name cannot be longer than {{ limit }} characters"
    )]
    private ?string $titre = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "description evennement ne peux pas être vide! ")]
    #[Assert\Length(
        min: 7,
        max: 20,
        minMessage: "The name must be at least {{ limit }} characters long",
        maxMessage: "The name cannot be longer than {{ limit }} characters"
    )]
    private ?string $description = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    #[Assert\GreaterThan("today", message: "date inferieur a aujourd'hui.")]
    #[Assert\NotBlank(message: "description evennement ne peux pas être vide! ")]
    #[Assert\NotNull(message: "description evennement ne peux pas être vide! ")]
    //#[Assert\DateTime(message: "description evennement ne peux pas être vide! ")]
    private ?\DateTimeInterface $dateDebut = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    #[Assert\NotBlank(message: "description evennement ne peux pas être vide! ")]
    #[Assert\NotNull(message: "description evennement ne peux pas être vide! ")]
    // #[Assert\DateTime(message: "description evennement ne peux pas être vide! ")]
    private ?\DateTimeInterface $dateFin = null;

    #[ORM\ManyToOne(inversedBy: 'evennements')]
    #[ORM\JoinColumn(nullable: true)]
    private ?EventCategory $category = null;

    #[ORM\OneToMany(mappedBy: 'evennement', targetEntity: Passe::class, orphanRemoval: true, cascade: ['persist', 'remove'])]
    private Collection $passes;


    #[ORM\Column(length: 255, nullable: true, options: ["default" => "logo.png"])]
    private ?string $eventimg = "logo.png";

    #[ORM\Column(nullable: true)]
    #[Assert\NotBlank(message: "price evennement ne peux pas être vide! ")]
    #[Assert\Positive(message: "price doit etre positive! ")]
    private ?float $prix = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "adresse evennement ne peux pas être vide! ")]
    #[Assert\Length(
        min: 3,
        max: 20,
        minMessage: "The name must be at least {{ limit }} characters long",
        maxMessage: "The name cannot be longer than {{ limit }} characters"
    )]
    private ?string $adresse = null;

    #[ORM\Column(nullable: true)]
    #[Assert\NotBlank(message: "nombre passe ne peux pas être vide! ")]
    #[Assert\Positive(message: "nombre passe doit etre positive! ")]
    private ?int $nbrp = null;



    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): self
    {
        $this->titre = $titre;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getDateDebut(): ?\DateTimeInterface
    {
        return $this->dateDebut;
    }

    public function setDateDebut(\DateTimeInterface $dateDebut): self
    {
        $this->dateDebut = $dateDebut;

        return $this;
    }

    public function getDateFin(): ?\DateTimeInterface
    {
        return $this->dateFin;
    }

    public function setDateFin(\DateTimeInterface $dateFin): self
    {
        $this->dateFin = $dateFin;

        return $this;
    }


    public function getCategory(): ?EventCategory
    {
        return $this->category;
    }

    public function setCategory(?EventCategory $category): self
    {
        $this->category = $category;

        return $this;
    }

    public function __toString()
    {
        return $this->titre;
    }

    public function getEventimg(): ?string
    {
        return  $this->eventimg;
    }


    public function setEventimg(?string $eventimg): self
    {
        $this->eventimg = $eventimg;

        // Save the file path to the database
        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(?float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getNbrP(): ?int
    {
        return $this->nbrp;
    }

    public function setNbrP(int $nbrp): self
    {
        $this->nbrp = $nbrp;

        return $this;
    }
}
