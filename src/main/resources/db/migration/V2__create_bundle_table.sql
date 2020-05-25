CREATE TABLE `bundle`
(
    `id`               INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(255) NOT NULL,
    `description`      TEXT         NULL,
    `created_at`       TIMESTAMP    NULL,
    `last_modified_at` TIMESTAMP    NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

CREATE INDEX bundle_name_idx ON bundle (name);
