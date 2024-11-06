package org.cdi.storemanager.store.errorhandling;

import org.cdi.storemanager.store.dto.ResourceErrorDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResourceErrorDto> handleUniqueIndexOrPrimaryKeyViolationException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResourceErrorDto("2", "Could not create store because another one with the same name exists."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResourceErrorDto> handleNotValidStoreCreationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResourceErrorDto("3", "Data needed for store creation is missing, please check the correct values."));
    }
}
