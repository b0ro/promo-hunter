package org.boro.promohunter.bundle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface BundleRepository extends JpaRepository<Bundle, Integer> {

    @EntityGraph("bundle-graph")
    List<Bundle> findAll();

    @EntityGraph("bundle-graph")
    Page<Bundle> findAll(Pageable pageable);
}
