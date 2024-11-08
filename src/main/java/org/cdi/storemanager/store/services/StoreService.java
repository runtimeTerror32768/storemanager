package org.cdi.storemanager.store.services;

import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.exceptions.ResourceException;
import org.cdi.storemanager.store.repositories.StoreRepository;
import org.cdi.storemanager.store.util.OperationStatusEnum;
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

    public ResponseEntity<OperationStatusEnum> deleteStore(UUID id) {
        if (storeRepository.existsById(id)) {
            storeRepository.deleteById(id);
            return ResponseEntity.ok(OperationStatusEnum.STORE_DELETE_SUCCESSFULLY);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(OperationStatusEnum.NO_STORE_FOUND_DELETE);
        }
    }
}
