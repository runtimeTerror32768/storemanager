package org.cdi.storemanager.store.repositories;

import org.cdi.storemanager.store.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
}

