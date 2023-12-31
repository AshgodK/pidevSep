<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230930173401 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE passe DROP FOREIGN KEY FK_D317EE5FDCDFA082');
        $this->addSql('ALTER TABLE passe CHANGE evennement_id evennement_id INT NOT NULL');
        $this->addSql('ALTER TABLE passe ADD CONSTRAINT FK_D317EE5FDCDFA082 FOREIGN KEY (evennement_id) REFERENCES evennement (id) ON DELETE CASCADE');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE passe DROP FOREIGN KEY FK_D317EE5FDCDFA082');
        $this->addSql('ALTER TABLE passe CHANGE evennement_id evennement_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE passe ADD CONSTRAINT FK_D317EE5FDCDFA082 FOREIGN KEY (evennement_id) REFERENCES evennement (id)');
    }
}
