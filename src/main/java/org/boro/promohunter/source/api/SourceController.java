package org.boro.promohunter.source.api;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.item.Item;
import org.boro.promohunter.item.ItemImporter;
import org.boro.promohunter.item.ItemImporterException;
import org.boro.promohunter.source.Source;
import org.boro.promohunter.source.SourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sources")
class SourceController {

    private final SourceService service;
    private final ItemImporter importer;

    @PostMapping("/check")
    public Item check(@RequestBody @Valid CheckSourceRequest request) {
        return importer.importItem(request.getUrl(), request.getSource().source())
                .orElseThrow(ItemImporterException::new);
    }

    @PostMapping
    public Source create(@RequestBody @Valid SourceRequest request) {
        return service.create(request.source());
    }

    @GetMapping("/{id}")
    public Source findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @GetMapping("/all")
    public List<Source> getAll() {
        return service.getAll();
    }

    @GetMapping
    public Page<Source> getAllPaged(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PutMapping("/{id}")
    public Source update(@PathVariable int id, @RequestBody @Valid SourceRequest request) {
        return service.update(id, request.source());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
