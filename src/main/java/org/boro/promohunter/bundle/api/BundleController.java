package org.boro.promohunter.bundle.api;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.bundle.Bundle;
import org.boro.promohunter.bundle.BundleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
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
@RequestMapping("/bundles")
class BundleController {

    private final BundleService service;

    @PostMapping
    public Bundle create(@RequestBody @Valid BundleRequest request) {
        return service.create(request.bundle());
    }

    @GetMapping("/{id}")
    public Bundle findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @GetMapping("/all")
    public List<Bundle> getAll(@SortDefault(sort = "id", direction = Sort.Direction.ASC) Sort sort) {
        return service.getAll(sort);
    }

    @GetMapping
    public Page<Bundle> getAllPaged(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PutMapping("/{id}")
    public Bundle update(@PathVariable int id, @RequestBody @Valid BundleRequest request) {
        return service.update(id, request.bundle());
    }

    @PutMapping("/{bundleId}/items/{itemId}")
    public Bundle assignItem(@PathVariable int bundleId, @PathVariable int itemId) {
        return service.assignItemToBundle(itemId, bundleId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
