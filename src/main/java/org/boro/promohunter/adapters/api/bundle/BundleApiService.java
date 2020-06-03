package org.boro.promohunter.adapters.api.bundle;

import lombok.RequiredArgsConstructor;
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

    public BundleResponse create(BundleRequest request) {
        var bundle = service.create(request.asBundle());
        return BundleResponse.of(bundle);
    }

    public BundleResponse findOne(int id) {
        return BundleResponse.of(service.findOne(id));
    }

    public List<BundleResponse> getAll() {
        return service.getAll()
                .stream()
                .map(BundleResponse::of)
                .collect(Collectors.toList());
    }

    public Page<BundleResponse> getAll(Pageable pageable) {
        return service.getAll(pageable)
                .map(BundleResponse::of);
    }

    public BundleResponse update(int id, BundleRequest request) {
        var bundle = service.update(id, request.asBundle());
        return BundleResponse.of(bundle);
    }

    public BundleWithItemsResponse assignItemToBundle(int itemId, int bundleId) {
        var bundle = service.assignItemToBundle(itemId, bundleId);
        return BundleWithItemsResponse.of(bundle);
    }

    public void delete(int id) {
        service.delete(id);
    }
}
