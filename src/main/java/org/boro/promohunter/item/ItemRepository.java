package org.boro.promohunter.item;

import org.boro.jpa.ExtendedJpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

interface ItemRepository extends ExtendedJpaRepository<Item, Integer> {

    @Override
    @EntityGraph("item-graph")
    List<Item> findAll();

    @Override
    @EntityGraph("item-graph")
    List<Item> findAllById(Iterable<Integer> ids);

    @Override
    @EntityGraph("item-graph")
    Optional<Item> findById(Integer id);
}
