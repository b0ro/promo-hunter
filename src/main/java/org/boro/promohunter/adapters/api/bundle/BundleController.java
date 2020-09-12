package org.boro.promohunter.adapters.api.bundle;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.adapters.api.bundle.dto.BundleRequest;
import org.boro.promohunter.adapters.api.bundle.dto.BundleResource;
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

    private final BundleApiService apiService;

    @PostMapping
    public BundleResource create(@RequestBody @Valid BundleRequest request) {
        return apiService.create(request);
    }

    @GetMapping("/{id}")
    public BundleResource findOne(@PathVariable int id) {
        return apiService.findOne(id);
    }

    @GetMapping("/all")
    public List<BundleResource> getAll() {
        return apiService.getAll();
    }

    @GetMapping
    public Page<BundleResource> getAllPaged(Pageable pageable) {
        return apiService.getAll(pageable);
    }

    @PutMapping("/{id}")
    public BundleResource update(@PathVariable int id, @RequestBody @Valid BundleRequest request) {
        return apiService.update(id, request);
    }

    @PutMapping("/{bundleId}/items/{itemId}")
    public BundleResource assignItem(@PathVariable int bundleId, @PathVariable int itemId) {
        return apiService.assignItemToBundle(itemId, bundleId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        apiService.delete(id);
    }
}
