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
public class ItemService {

    private final ItemRepository repository;
    private final SourceService sourceService;
    private final ItemImporter importer;

    public Item create(Item item) {
        try {
            var url = new URL(item.getUrl());
            var domain = String.format("%s://%s", url.getProtocol(), url.getHost());
            var source = sourceService.findByUrlStartingWith(domain);

            item.setSource(source);
        } catch (MalformedURLException | SourceNotFoundException cause) {
            throw new NoSourceForItemUrlFoundException(item.getUrl(), cause);
        }
        return repository.save(item);
    }

    public Item createFromUrl(String url) {
        try {
            var domain = getDomainFromUrl(url);
            var source = sourceService.findByUrlStartingWith(domain);
            var item = importer.importItem(url, source)
                    .orElseThrow(ItemImporterException::new);

            return repository.save(item);
        } catch (MalformedURLException cause) {
            throw new NoSourceForItemUrlFoundException(url, cause);
        }
    }

    public Item findOne(int id) {
        return repository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    public List<Item> getAll() {
        return repository.findAll();
    }

    public Page<Item> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Item update(int id, Item item) {
        var entity = findOne(id);
        entity.updateFrom(item);
        return repository.save(entity);
    }

    public void delete(int id) {
        repository.delete(findOne(id));
    }

    private String getDomainFromUrl(String urlString) throws MalformedURLException {
        var url = new URL(urlString);
        return String.format("%s://%s", url.getProtocol(), url.getHost());
    }
}
