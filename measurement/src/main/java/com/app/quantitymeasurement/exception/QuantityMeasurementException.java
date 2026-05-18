package com.app.quantitymeasurement.exception;

import java.io.Serializable;

public class QuantityMeasurementException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String errorCode;

    // Message only (UC15 compatibility)
    public QuantityMeasurementException(String message) {
        super(message);
        this.errorCode = null;
    }

    // Message + cause (Important for wrapping SQL errors if needed)
    public QuantityMeasurementException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    // Message + error code
    public QuantityMeasurementException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "QuantityMeasurementException{" +
                "message=" + getMessage() +
                (errorCode != null ? ", code=" + errorCode : "") +
                '}';
    }
}