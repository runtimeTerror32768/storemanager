package org.cdi.storemanager.store.repositories;

import org.cdi.storemanager.store.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}

