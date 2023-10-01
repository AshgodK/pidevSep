<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230929152252 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE passe ADD passe_owner_id INT NOT NULL');
        $this->addSql('ALTER TABLE passe ADD CONSTRAINT FK_D317EE5F5D9E51AC FOREIGN KEY (passe_owner_id) REFERENCES utilisateur (id)');
        $this->addSql('CREATE INDEX IDX_D317EE5F5D9E51AC ON passe (passe_owner_id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE passe DROP FOREIGN KEY FK_D317EE5F5D9E51AC');
        $this->addSql('DROP INDEX IDX_D317EE5F5D9E51AC ON passe');
        $this->addSql('ALTER TABLE passe DROP passe_owner_id');
    }
}
