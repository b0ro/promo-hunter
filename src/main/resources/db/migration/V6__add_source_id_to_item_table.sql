ALTER TABLE `item`
    ADD COLUMN source_id INT UNSIGNED NOT NULL,
    ADD FOREIGN KEY `item_source_fk` (`source_id`) REFERENCES `source` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
