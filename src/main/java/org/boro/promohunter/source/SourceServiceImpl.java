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
public class SourceServiceImpl implements SourceService {

    private final SourceRepository repository;

    @Override
    public Source create(Source source) {
        return repository.save(source);
    }

    @Override
    public Source findOne(int id) {
        return repository.findById(id).orElseThrow(() -> SourceNotFoundException.withId(id));
    }

    @Override
    public List<Source> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<Source> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Source update(int id, Source source) {
        var entity = findOne(id);
        entity.updateFrom(source);
        entity = repository.save(entity);

        return entity;
    }

    @Override
    public void delete(int id) {
        var item = findOne(id);
        repository.delete(item);
    }

    @Override
    public Source findByUrl(String url) {
        return repository.findByUrl(url).orElseThrow(() -> SourceNotFoundException.withUrl(url));
    }
}
