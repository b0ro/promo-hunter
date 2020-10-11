package org.boro.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ExtendedJpaRepository<T, ID> extends JpaRepository<T, ID> {

    @Query("select id from #{#entityName}")
    List<ID> findAllIds(Pageable pageable);

    @Override
    default Page<T> findAll(Pageable pageable) {
        var entries = findAllById(findAllIds(pageable));
        return new PageImpl<>(entries, pageable, count());
    }
}
