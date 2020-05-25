package org.boro.promohunter.bundle;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.item.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BundleServiceImpl implements BundleService {

    private final BundleRepository repository;
    private final ItemService itemService;

    @Override
    public Bundle create(Bundle item) {
        return repository.save(item);
    }

    @Override
    public Bundle findOne(int id) {
        return repository.findById(id).orElseThrow(() -> new BundleNotFoundException(id));
    }

    @Override
    public List<Bundle> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<Bundle> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Bundle update(int id, Bundle bundle) {
        var entity = findOne(id);
        entity.updateFrom(bundle);
        entity = repository.save(entity);

        return entity;
    }

    @Override
    public Bundle assignItemToBundle(int itemId, int bundleId) {
        var item = itemService.findOne(itemId);
        var bundle = findOne(bundleId);
        bundle.addItem(item);

        return repository.save(bundle);
    }

    @Override
    public void delete(int id) {
        var item = findOne(id);
        repository.delete(item);
    }
}
