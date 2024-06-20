CREATE SCHEMA `anime_store` ;

CREATE TABLE `animes_store`.`anime` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `animes_store`.`producer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `id`
    FOREIGN KEY (`id`)
    REFERENCES `animes_store`.`anime` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

