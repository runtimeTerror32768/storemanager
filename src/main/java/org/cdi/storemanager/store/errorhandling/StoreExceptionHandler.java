package org.cdi.storemanager.store.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.cdi.storemanager.store.dto.ResourceOperationStatusDto;
import org.cdi.storemanager.store.exceptions.ResourceException;
import org.cdi.storemanager.store.util.OperationStatusEnum;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class StoreExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<OperationStatusEnum> handleUniqueIndexOrPrimaryKeyViolationException() {
        log.error("Error while creating store: error code: {} {}",
                OperationStatusEnum.CREATE_STORE_ALREADY_EXISTS_ERROR.getStatusCode(), OperationStatusEnum.CREATE_STORE_ALREADY_EXISTS_ERROR.getStatusMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OperationStatusEnum.CREATE_STORE_ALREADY_EXISTS_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<OperationStatusEnum> handleNotValidStoreCreationException() {
        log.error("Error while creating store - bad data: error code: {} {}",
                OperationStatusEnum.CREATE_STORE_MISSING_DATA_ERROR.getStatusCode(), OperationStatusEnum.CREATE_STORE_MISSING_DATA_ERROR.getStatusMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OperationStatusEnum.CREATE_STORE_MISSING_DATA_ERROR);
    }

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ResourceOperationStatusDto> handleResourceException(ResourceException resourceException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResourceOperationStatusDto(resourceException.getErrorCode(), resourceException.getErrorMessage()));
    }
}
