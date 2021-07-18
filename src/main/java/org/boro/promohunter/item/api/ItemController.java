package org.boro.promohunter.item.api;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.item.ItemService;
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
@RequestMapping("/items")
class ItemController {

    private final ItemService service;

    @PostMapping("/import")
    public ItemResponse importItem(@RequestBody @Valid ItemImportRequest request) {
        var item = service.createFromUrl(request.getUrl());
        return ItemResponse.of(item);
    }

    @PostMapping
    public ItemResponse create(@RequestBody @Valid ItemRequest request) {
        var item = service.create(request.item());
        return ItemResponse.of(item);
    }

    @GetMapping("/{id}")
    public FullItemResponse findOne(@PathVariable int id) {
        var item = service.findOne(id);
        return FullItemResponse.of(item);
    }

    @GetMapping
    public Page<ItemResponse> getAllPaged(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return service.getAll(pageable)
                .map(ItemResponse::of);
    }

    @PutMapping("/{id}")
    public FullItemResponse update(@PathVariable int id, @RequestBody @Valid ItemRequest request) {
        var item = service.update(id, request.item());
        return FullItemResponse.of(item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
