<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230929160251 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE passe DROP INDEX UNIQ_D317EE5FDCDFA082, ADD INDEX IDX_D317EE5FDCDFA082 (evennement_id)');
        $this->addSql('ALTER TABLE passe CHANGE evennement_id evennement_id INT DEFAULT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE passe DROP INDEX IDX_D317EE5FDCDFA082, ADD UNIQUE INDEX UNIQ_D317EE5FDCDFA082 (evennement_id)');
        $this->addSql('ALTER TABLE passe CHANGE evennement_id evennement_id INT NOT NULL');
    }
}
