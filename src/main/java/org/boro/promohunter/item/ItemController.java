package org.boro.promohunter.item;

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
@RequestMapping("/api/items")
class ItemController {

    private final ItemService service;

    @PostMapping
    public Item create(@RequestBody @Valid Item item) {
        return service.create(item);
    }

    @GetMapping("/{id}")
    public Item findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @GetMapping("/all")
    public List<Item> getAll() {
        return service.getAll();
    }

    @GetMapping
    public Page<Item> getAllPaged(Pageable pageable) {
        return service.getAll(pageable);
    }

    @PutMapping("/{id}")
    public Item update(@PathVariable int id, @RequestBody @Valid Item item) {
        return service.update(id, item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
