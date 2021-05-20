package org.boro.promohunter.bundle;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.item.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BundleService {

    private final BundleRepository repository;
    private final ItemService itemService;

    public Bundle create(Bundle item) {
        return repository.save(item);
    }

    public Bundle findOne(int id) {
        return repository.findById(id).orElseThrow(() -> new BundleNotFoundException(id));
    }

    public List<Bundle> getAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Page<Bundle> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Bundle update(int id, Bundle bundle) {
        var entity = findOne(id);
        entity.updateFrom(bundle);
        return repository.save(entity);
    }

    public Bundle assignItemToBundle(int itemId, int bundleId) {
        var item = itemService.findOne(itemId);
        var bundle = findOne(bundleId);
        bundle.addItem(item);

        return repository.save(bundle);
    }

    public void delete(int id) {
        repository.delete(findOne(id));
    }
}
