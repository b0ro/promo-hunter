package org.boro.promohunter.bundle;

import lombok.RequiredArgsConstructor;
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
@RequestMapping("/bundles")
class BundleController {

    private final BundleService service;

    @PostMapping
    public Bundle create(@RequestBody @Valid Bundle bundle) {
        return service.create(bundle);
    }

    @GetMapping("/{id}")
    public Bundle findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @GetMapping("/all")
    public List<Bundle> getAll() {
        return service.getAll();
    }

    @GetMapping
    public Page<Bundle> getAllPaged(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PutMapping("/{id}")
    public Bundle update(@PathVariable int id, @RequestBody @Valid Bundle bundle) {
        return service.update(id, bundle);
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
