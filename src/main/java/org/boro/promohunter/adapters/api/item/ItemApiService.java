package org.boro.promohunter.adapters.api.item;

import lombok.RequiredArgsConstructor;
import org.boro.promohunter.adapters.api.item.dto.ItemRequest;
import org.boro.promohunter.adapters.api.item.dto.ItemResource;
import org.boro.promohunter.item.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ItemApiService {

    private final ItemService service;

    public ItemResource create(ItemRequest request) {
        var item = service.create(request.item());
        return ItemResource.of(item);
    }

    public ItemResource createFromUrl(String url) {
        var item = service.createFromUrl(url);
        return ItemResource.of(item);
    }

    public ItemResource findOne(int id) {
        var item = service.findOne(id);
        return ItemResource.of(item);
    }

    public List<ItemResource> getAll() {
        return service.getAll()
                .stream()
                .map(ItemResource::of)
                .collect(Collectors.toList());
    }

    public Page<ItemResource> getAll(Pageable pageable) {
        return service.getAll(pageable)
                .map(ItemResource::of);
    }

    public ItemResource update(int id, ItemRequest request) {
        var item = service.update(id, request.item());
        return ItemResource.of(item);
    }

    public void delete(int id) {
        service.delete(id);
    }
}
