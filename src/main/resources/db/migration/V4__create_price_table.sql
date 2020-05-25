CREATE TABLE `price`
(
    `id`               INT UNSIGNED   NOT NULL AUTO_INCREMENT,
    `value`            DECIMAL(13, 4) NOT NULL,
    `created_at`       TIMESTAMP      NULL,
    `last_modified_at` TIMESTAMP      NULL,
    `item_id`          INT UNSIGNED,
    PRIMARY KEY (`id`),
    FOREIGN KEY `price_item_fk` (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
