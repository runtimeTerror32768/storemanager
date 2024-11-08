package org.cdi.storemanager.store.errorhandling;

import org.cdi.storemanager.store.dto.ResourceOperationStatusDto;
import org.cdi.storemanager.store.exceptions.ResourceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StoreExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResourceOperationStatusDto> handleUniqueIndexOrPrimaryKeyViolationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResourceOperationStatusDto("2", "Could not create store because another one with the same name exists."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResourceOperationStatusDto> handleNotValidStoreCreationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResourceOperationStatusDto("3", "Data needed for store creation is missing, please check the correct values."));
    }

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ResourceOperationStatusDto> handleResourceException(ResourceException resourceException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResourceOperationStatusDto(resourceException.getErrorCode(), resourceException.getErrorMessage()));
    }
}
