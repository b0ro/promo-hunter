package org.boro.promohunter.bundle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BundleService {

    Bundle create(Bundle item);

    Bundle findOne(int id);

    List<Bundle> getAll();

    Page<Bundle> getAll(Pageable pageable);

    Bundle update(int id, Bundle item);

    Bundle assignItemToBundle(int itemId, int bundleId);

    void delete(int id);
}
