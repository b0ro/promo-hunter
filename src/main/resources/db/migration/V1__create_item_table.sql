CREATE TABLE `item`
(
    `id`               INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(255) NOT NULL,
    `description`      TEXT         NULL,
    `created_at`       TIMESTAMP    NULL,
    `last_modified_at` TIMESTAMP    NULL,
    `url`              VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

CREATE INDEX item_name_id ON item (name);
