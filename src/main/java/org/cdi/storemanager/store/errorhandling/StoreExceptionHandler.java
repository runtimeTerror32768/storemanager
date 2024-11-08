package org.cdi.storemanager.store.errorhandling;

import org.cdi.storemanager.store.dto.ResourceOperationStatusDto;
import org.cdi.storemanager.store.exceptions.ResourceException;
import org.cdi.storemanager.store.util.OperationStatusEnum;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StoreExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<OperationStatusEnum> handleUniqueIndexOrPrimaryKeyViolationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OperationStatusEnum.CREATE_STORE_ALREADY_EXISTS_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<OperationStatusEnum> handleNotValidStoreCreationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OperationStatusEnum.CREATE_STORE_MISSING_DATA_ERROR);
    }

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ResourceOperationStatusDto> handleResourceException(ResourceException resourceException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResourceOperationStatusDto(resourceException.getErrorCode(), resourceException.getErrorMessage()));
    }
}
