package org.boro.promohunter.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ItemRepository extends JpaRepository<Item, Integer> {

    @EntityGraph("item-graph")
    List<Item> findAll();

    @EntityGraph("item-graph")
    Page<Item> findAll(Pageable pageable);
}
