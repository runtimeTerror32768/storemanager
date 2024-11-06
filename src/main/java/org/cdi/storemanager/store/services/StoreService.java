package org.cdi.storemanager.store.services;

import jakarta.validation.Valid;
import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.exceptions.ResourceException;
import org.cdi.storemanager.store.repositories.StoreRepository;
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

    public Store updateStore(UUID id, @Valid Store store) {
        if(storeRepository.existsById(id)) {
            store.setId(id);
            return storeRepository.save(store);
        }
        throw new ResourceException("4", "Store not found for update");
    }
}
