package org.boro.promohunter.item;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.source.SourceNotFoundException;
import org.boro.promohunter.source.SourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final SourceService sourceService;

    @Override
    public Item create(Item item) {
        try {
            var url = new URL(item.getUrl());
            var domain = String.format("%s://%s", url.getProtocol(), url.getHost());
            var source = sourceService.findByUrl(domain);

            item.setSource(source);
        } catch (MalformedURLException | SourceNotFoundException cause) {
            throw new NoSourceForItemUrlFoundException(item.getUrl(), cause);
        }
        return repository.save(item);
    }

    @Override
    public Item findOne(int id) {
        return repository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    @Override
    public List<Item> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Item update(int id, Item item) {
        var entity = findOne(id);
        entity.updateFrom(item);
        entity = repository.save(entity);

        return entity;
    }

    @Override
    public void delete(int id) {
        var item = findOne(id);
        repository.delete(item);
    }
}
