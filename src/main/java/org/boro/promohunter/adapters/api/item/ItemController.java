package org.boro.promohunter.adapters.api.item;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.adapters.api.item.dto.ItemImportRequest;
import org.boro.promohunter.adapters.api.item.dto.ItemRequest;
import org.boro.promohunter.adapters.api.item.dto.ItemResource;
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
@RequestMapping("/items")
class ItemController {

    private final ItemApiService apiService;

    @PostMapping("/import")
    public ItemResource importItem(@RequestBody @Valid ItemImportRequest request) {
        return apiService.createFromUrl(request.getUrl());
    }

    @PostMapping
    public ItemResource create(@RequestBody @Valid ItemRequest request) {
        return apiService.create(request);
    }

    @GetMapping("/{id}")
    public ItemResource findOne(@PathVariable int id) {
        return apiService.findOne(id);
    }

    @GetMapping("/all")
    public List<ItemResource> getAll() {
        return apiService.getAll();
    }

    @GetMapping
    public Page<ItemResource> getAllPaged(Pageable pageable) {
        return apiService.getAll(pageable);
    }

    @PutMapping("/{id}")
    public ItemResource update(@PathVariable int id, @RequestBody @Valid ItemRequest request) {
        return apiService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        apiService.delete(id);
    }
}
