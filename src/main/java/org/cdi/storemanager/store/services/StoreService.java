package org.cdi.storemanager.store.services;

import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.repositories.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
