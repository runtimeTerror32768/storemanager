package org.cdi.storemanager.store.exceptions;

import lombok.Getter;

@Getter
public class ResourceException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public ResourceException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
