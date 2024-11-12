package org.cdi.storemanager.store.services;

import org.cdi.storemanager.store.entities.Store;
import org.cdi.storemanager.store.exceptions.ResourceException;
import org.cdi.storemanager.store.repositories.StoreRepository;
import org.cdi.storemanager.store.util.OperationStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    private static final String STORE_NAME1 = "Store name 1";
    private static final String STORE_ADDRESS1 = "Store address 1";
    private static final String STORE_NAME2 = "Store name 2";
    private static final String STORE_ADDRESS2 = "Store address 2";

    private Store store1;
    private Store store2;

    private UUID idStore1 = UUID.randomUUID();
    private UUID idStore2 = UUID.randomUUID();
    private List<Store> stores;

    @Mock
    private StoreRepository storeRepository;

    @Captor
    ArgumentCaptor<Store> storeArgumentCaptor;

    @InjectMocks
    private StoreService storeService;

    @Test
    void getAllStores_storesFound_storesReturned() {
        store1 = Store.builder()
                .id(idStore1)
                .name(STORE_NAME1)
                .address(STORE_ADDRESS1)
                .build();
        store2 = Store.builder()
                .id(idStore2)
                .name(STORE_NAME2)
                .address(STORE_ADDRESS2)
                .build();
        stores = Arrays.asList(store1, store2);
        Mockito.when(storeRepository.findAll()).thenReturn(stores);

        List<Store> allStores = storeService.getAllStores();

        assertEquals(stores, allStores);
    }

    @Test
    void createStore_storeOk_storeCreated() {
        store1 = Store.builder()
                .id(idStore1)
                .name(STORE_NAME1)
                .address(STORE_ADDRESS1)
                .build();
        Mockito.when(storeRepository.save(Mockito.eq(store1))).thenReturn(store1);

        Store createdStore = storeService.createStore(store1);

        assertEquals(store1, createdStore);
        Mockito.verify(storeRepository).save(store1);
    }

    @Test
    void updateStore_storeExists_storeUpdated() {
        Store storeUpdate = Store.builder()
                .id(idStore2)
                .name("Store name updated")
                .address("Store address updated")
                .build();
        Mockito.when(storeRepository.existsById(idStore1)).thenReturn(true);
        Mockito.when(storeRepository.save(Mockito.any(Store.class))).thenReturn(storeUpdate);

        Store updateStore = storeService.updateStore(idStore1, storeUpdate);

        assertNotNull(updateStore);
        Mockito.verify(storeRepository).save(storeArgumentCaptor.capture());
        assertEquals(idStore1, storeArgumentCaptor.getValue().getId());
        assertEquals("Store name updated", storeArgumentCaptor.getValue().getName());
        assertEquals("Store address updated", storeArgumentCaptor.getValue().getAddress());
    }

    @Test
    void updateStore_storeNotExists_exceptionThrown() {
        ResourceException resourceException = assertThrows(ResourceException.class, () -> storeService.updateStore(idStore1, store1));

        assertEquals("4", resourceException.getErrorCode());
        assertEquals("Store not found for update", resourceException.getErrorMessage());
    }

    @Test
    void deleteStore_storeExists_storeDeleted() {
        Store storeDelete = Store.builder()
                .id(idStore1)
                .name("Store name to delete")
                .address("Store address to delete")
                .build();
        Mockito.when(storeRepository.existsById(idStore1)).thenReturn(true);

        ResponseEntity<OperationStatusEnum> responseEntity = storeService.deleteStore(idStore1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("5", responseEntity.getBody().getStatusCode());
        assertEquals("Store deleted successfully.", responseEntity.getBody().getStatusMessage());
    }
}