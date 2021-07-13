package org.boro.promohunter.source.api;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.item.ItemImporter;
import org.boro.promohunter.item.ItemImporterException;
import org.boro.promohunter.source.SourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/sources")
class SourceController {

    private final SourceService service;
    private final ItemImporter importer;

    @PostMapping("/check")
    public CheckSourceResponse check(@RequestBody @Valid CheckSourceRequest request) {
        var item = importer.importItem(request.getUrl(), request.getSource().source())
                .orElseThrow(ItemImporterException::new);
        return CheckSourceResponse.of(item);
    }

    @PostMapping
    public SourceResponse create(@RequestBody @Valid SourceRequest request) {
        var source = service.create(request.source());
        return SourceResponse.of(source);
    }

    @GetMapping("/{id}")
    public SourceResponse findOne(@PathVariable int id) {
        var source = service.findOne(id);
        return SourceResponse.of(source);
    }

    @GetMapping
    public Page<SourceResponse> getAllPaged(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return service.getAll(pageable)
                .map(SourceResponse::of);
    }

    @PutMapping("/{id}")
    public SourceResponse update(@PathVariable int id, @RequestBody @Valid SourceRequest request) {
        var source = service.update(id, request.source());
        return SourceResponse.of(source);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
