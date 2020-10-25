package org.boro.promohunter.source;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SourceRepository extends JpaRepository<Source, Integer> {

    Optional<Source> findByUrlStartingWith(String url);
}
