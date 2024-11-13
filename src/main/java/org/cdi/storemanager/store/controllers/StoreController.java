package org.cdi.storemanager.store.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.services.StoreService;
import org.cdi.storemanager.store.util.OperationStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Slf4j
@RequestMapping("/api/v1/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PreAuthorize("hasRole('ROLE_VIEWER')")
    @GetMapping("")
    public ResponseEntity<?> getAllStores() {
        List<Store> allStores = storeService.getAllStores();
        if (allStores.isEmpty()) {
            log.info("Get all stores: error code: {} {}", OperationStatusEnum.NO_STORES_FOUND_GET.getStatusCode(), OperationStatusEnum.NO_STORES_FOUND_GET.getStatusMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(OperationStatusEnum.NO_STORES_FOUND_GET);
        }

        log.info("GET all stores completed successfully.");
        return ResponseEntity.ok(allStores);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public Store createStore(@RequestBody @Valid Store store) {
        return storeService.createStore(store);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Store updateStore(@PathVariable UUID id, @RequestBody @Valid Store store) {
        return storeService.updateStore(id, store);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<OperationStatusEnum> deleteStore(@PathVariable @NotNull UUID id) {
        return storeService.deleteStore(id);
    }
}
