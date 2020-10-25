package org.boro.promohunter.source;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SourceService {

    private final SourceRepository repository;

    public Source create(Source source) {
        return repository.save(source);
    }

    public Source findOne(int id) {
        return repository.findById(id).orElseThrow(() -> SourceNotFoundException.withId(id));
    }

    public List<Source> getAll() {
        return repository.findAll();
    }

    public Page<Source> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Source update(int id, Source source) {
        var entity = findOne(id);
        entity.updateFrom(source);
        return repository.save(entity);
    }

    public void delete(int id) {
        repository.delete(findOne(id));
    }

    public Source findByUrlStartingWith(String url) {
        return repository.findByUrlStartingWith(url).orElseThrow(() -> SourceNotFoundException.withUrl(url));
    }
}
