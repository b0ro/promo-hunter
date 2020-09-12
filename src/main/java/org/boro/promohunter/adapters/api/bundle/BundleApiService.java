package org.boro.promohunter.adapters.api.bundle;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.adapters.api.bundle.dto.BundleRequest;
import org.boro.promohunter.adapters.api.bundle.dto.BundleResource;
import org.boro.promohunter.bundle.BundleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class BundleApiService {

    private final BundleService service;

    public BundleResource create(BundleRequest request) {
        var bundle = service.create(request.bundle());
        return BundleResource.of(bundle);
    }

    public BundleResource findOne(int id) {
        var bundle = service.findOne(id);
        return BundleResource.of(bundle);
    }

    public List<BundleResource> getAll() {
        return service.getAll()
                .stream()
                .map(BundleResource::of)
                .collect(Collectors.toList());
    }

    public Page<BundleResource> getAll(Pageable pageable) {
        return service.getAll(pageable)
                .map(BundleResource::of);
    }

    public BundleResource update(int id, BundleRequest request) {
        var bundle = service.update(id, request.bundle());
        return BundleResource.of(bundle);
    }

    public BundleResource assignItemToBundle(int itemId, int bundleId) {
        var bundle = service.assignItemToBundle(itemId, bundleId);
        return BundleResource.of(bundle);
    }

    public void delete(int id) {
        service.delete(id);
    }
}
