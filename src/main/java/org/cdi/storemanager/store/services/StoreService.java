package org.cdi.storemanager.store.services;

import lombok.extern.slf4j.Slf4j;
import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.exceptions.ResourceException;
import org.cdi.storemanager.store.repositories.StoreRepository;
import org.cdi.storemanager.store.util.OperationStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
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
        Store savedStore = storeRepository.save(store);
        log.info("Store created successfully: {}", store.getId());
        return savedStore;
    }

    public Store updateStore(UUID id, Store store) {
        if (storeRepository.existsById(id)) {
            store.setId(id);
            Store updatedStore = storeRepository.save(store);
            log.info("Store updated successfully: {}", id);
            return updatedStore;
        }

        log.info("Store not found for update: error code: {} store id: {}", 4, id);
        throw new ResourceException("4", "Store not found for update");
    }

    public ResponseEntity<OperationStatusEnum> deleteStore(UUID id) {
        if (storeRepository.existsById(id)) {
            storeRepository.deleteById(id);

            log.info("Delete store with id: {} {}", id, OperationStatusEnum.STORE_DELETE_SUCCESSFULLY.getStatusMessage());
            return ResponseEntity.ok(OperationStatusEnum.STORE_DELETE_SUCCESSFULLY);
        } else {
            log.info("Store not found to delete: error code: {} store id: {}", OperationStatusEnum.NO_STORE_FOUND_DELETE.getStatusCode(), OperationStatusEnum.NO_STORE_FOUND_DELETE.getStatusMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(OperationStatusEnum.NO_STORE_FOUND_DELETE);
        }
    }
}
