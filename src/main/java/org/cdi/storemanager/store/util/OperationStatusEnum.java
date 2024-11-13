package org.cdi.storemanager.store.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OperationStatusEnum {

    NO_STORES_FOUND_GET("1", "No stores were found."),
    CREATE_STORE_ALREADY_EXISTS_ERROR("2", "Could not create store because another one with the same name exists."),
    CREATE_STORE_MISSING_DATA_ERROR("3", "Data needed for store creation is missing, please check the correct values."),
    STORE_DELETE_SUCCESSFULLY("5", "Store deleted successfully."),
    NO_STORE_FOUND_DELETE("6", "No store found to delete.");

    private final String statusCode;
    private final String statusMessage;

    OperationStatusEnum(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
