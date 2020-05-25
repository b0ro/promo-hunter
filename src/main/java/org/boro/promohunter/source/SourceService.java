package org.boro.promohunter.source;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SourceService {

    Source create(Source item);

    Source findOne(int id);

    List<Source> getAll();

    Page<Source> getAll(Pageable pageable);

    Source update(int id, Source source);

    void delete(int id);

    Source findByUrl(String url);
}
