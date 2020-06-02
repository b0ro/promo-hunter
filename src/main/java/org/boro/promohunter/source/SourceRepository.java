package org.boro.promohunter.source;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface SourceRepository extends JpaRepository<Source, Integer> {

    @EntityGraph("source-graph")
    List<Source> findAll();

    @EntityGraph("source-graph")
    Page<Source> findAll(Pageable pageable);

    Optional<Source> findByUrlStartingWith(String url);
}
