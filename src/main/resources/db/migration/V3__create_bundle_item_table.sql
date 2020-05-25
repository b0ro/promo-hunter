CREATE TABLE `bundle_item`
(
    `bundle_id` INT UNSIGNED NOT NULL,
    `item_id`   INT UNSIGNED NOT NULL,
    PRIMARY KEY (`bundle_id`, `item_id`),
    FOREIGN KEY `set_fk` (`bundle_id`) REFERENCES `bundle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY `item_fk` (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
