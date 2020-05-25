package org.boro.promohunter.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    Item create(Item item);

    Item findOne(int id);

    List<Item> getAll();

    Page<Item> getAll(Pageable pageable);

    Item update(int id, Item item);

    void delete(int id);
}
