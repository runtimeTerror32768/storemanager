package org.cdi.storemanager.store.controllers;

import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.services.StoreService;
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

    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }
}
