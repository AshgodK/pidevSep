<?php

namespace App\Entity;

use App\Repository\PasseRepository;
use App\Entity\Evennement;
use App\Entity\Utilisateur;
use App\Repository\EvennementRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: PasseRepository::class)]
class Passe
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 50)]
    /* #[Assert\NotBlank(message: "code ne peux pas être vide! ")]
    #[Assert\Length(
        min: 3,
        max: 10,
        minMessage: "The name must be at least {{ limit }} characters long",
        maxMessage: "The name cannot be longer than {{ limit }} characters"
    )]*/
    private ?string $code = null;

    #[ORM\Column]
    /*#[Assert\NotBlank(message: "price evennement ne peux pas être vide! ")]
    #[Assert\Positive(message: "price doit etre positive! ")]*/
    private ?float $prix = null;

    #[ORM\ManyToOne(inversedBy: 'passes')]
    #[ORM\JoinColumn(nullable: false,  onDelete: 'CASCADE',)]
    private ?Evennement $evennement = null;

    #[ORM\ManyToOne(inversedBy: 'tickets')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Utilisateur $PasseOwner = null;

    public function __construct($price = null, $event = null, Utilisateur $u)
    {
        $this->code = uniqid();
        $this->prix = $price;
        $this->evennement = $event;
        $this->PasseOwner = $u;
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCode(): ?string
    {
        return $this->code;
    }

    public function setCode(string $code): self
    {
        $this->code = $code;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getEvennement(): ?Evennement
    {
        return $this->evennement;
    }

    public function setEvennement(Evennement $evennement): self
    {
        $this->evennement = $evennement;

        return $this;
    }
    public function getPasseOwner(): ?Utilisateur
    {
        return $this->PasseOwner;
    }

    public function setPasseOwner(?Utilisateur $PasseOwner): self
    {
        $this->PasseOwner = $PasseOwner;

        return $this;
    }
}
