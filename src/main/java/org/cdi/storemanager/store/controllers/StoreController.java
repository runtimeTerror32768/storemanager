package org.cdi.storemanager.store.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.cdi.storemanager.store.dto.ResourceOperationStatusDto;
import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.services.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllStores() {
        List<Store> allStores = storeService.getAllStores();
        if (allStores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResourceOperationStatusDto("1", "No stores were found."));
        }
        return ResponseEntity.ok(allStores);
    }

    @PostMapping("")
    public Store createStore(@RequestBody @Valid Store store) {
        return storeService.createStore(store);
    }

    @PutMapping("/{id}")
    public Store updateStore(@PathVariable UUID id, @RequestBody @Valid Store store) {
        return storeService.updateStore(id, store);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceOperationStatusDto> deleteStore(@PathVariable @NotNull UUID id) {
        return storeService.deleteStore(id);
    }
}
