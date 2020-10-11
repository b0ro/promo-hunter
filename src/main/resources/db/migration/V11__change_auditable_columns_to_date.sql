ALTER TABLE `source` MODIFY `created_at` DATE NULL;
ALTER TABLE `source` MODIFY `last_modified_at` DATE NULL;

ALTER TABLE `item` MODIFY `created_at` DATE NULL;
ALTER TABLE `item` MODIFY `last_modified_at` DATE NULL;

ALTER TABLE `bundle` MODIFY `created_at` DATE NULL;
ALTER TABLE `bundle` MODIFY `last_modified_at` DATE NULL;

ALTER TABLE `price_update` MODIFY `created_at` DATE NULL;
ALTER TABLE `price_update` MODIFY `last_modified_at` DATE NULL;
