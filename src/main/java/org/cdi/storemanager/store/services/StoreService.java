package org.cdi.storemanager.store.services;

import org.cdi.storemanager.store.dto.ResourceOperationStatusDto;
import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.exceptions.ResourceException;
import org.cdi.storemanager.store.repositories.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    public Store updateStore(UUID id, Store store) {
        if (storeRepository.existsById(id)) {
            store.setId(id);
            return storeRepository.save(store);
        }
        throw new ResourceException("4", "Store not found for update");
    }

    public ResponseEntity<ResourceOperationStatusDto> deleteStore(UUID id) {
        if (storeRepository.existsById(id)) {
            storeRepository.deleteById(id);
            return ResponseEntity.ok(new ResourceOperationStatusDto("5", "Store deleted successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResourceOperationStatusDto("6", "No store found to delete."));
        }
    }
}
