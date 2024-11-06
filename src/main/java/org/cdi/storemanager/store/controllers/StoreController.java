package org.cdi.storemanager.store.controllers;

import jakarta.validation.Valid;
import org.cdi.storemanager.store.dto.ResourceErrorDto;
import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.services.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            return ResponseEntity.ok(new ResourceErrorDto("1", "No stores were found."));
        }
        return ResponseEntity.ok(allStores);
    }

    @PostMapping("/create-store")
    public Store createStore(@RequestBody @Valid Store store) {
        return storeService.createStore(store);
    }
}
