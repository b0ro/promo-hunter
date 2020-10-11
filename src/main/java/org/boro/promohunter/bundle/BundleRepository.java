package org.boro.promohunter.bundle;

import org.boro.jpa.ExtendedJpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

interface BundleRepository extends ExtendedJpaRepository<Bundle, Integer> {

    @Override
    @EntityGraph("bundle-graph")
    List<Bundle> findAll();

    @Override
    @EntityGraph("bundle-graph")
    List<Bundle> findAllById(Iterable<Integer> ids);

    @Override
    @EntityGraph("bundle-graph")
    Optional<Bundle> findById(Integer id);
}
