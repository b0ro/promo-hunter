CREATE TABLE `source`
(
    `id`                   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`                 VARCHAR(255) NOT NULL,
    `description`          TEXT         NULL,
    `url`                  VARCHAR(255) NULL,
    `title_selector`       VARCHAR(255) NOT NULL,
    `description_selector` VARCHAR(255) NULL,
    `price_selector`       VARCHAR(255) NOT NULL,
    `created_at`           TIMESTAMP    NULL,
    `last_modified_at`     TIMESTAMP    NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

CREATE INDEX source_name_idx ON source (name);
