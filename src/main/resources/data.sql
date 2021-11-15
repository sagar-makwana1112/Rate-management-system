CREATE TABLE `Rate` (
  `id` BIGINT(10) NOT NULL,
  `RateDescription` VARCHAR(1000) NULL,
  `RateEffectiveDate` DATE NOT NULL,
  `RateExpirationDate` DATE NOT NULL,
  `Amount` INT NOT NULL,
  PRIMARY KEY (`id`));