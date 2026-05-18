package com.app.quantitymeasurement.dto;

import java.io.Serializable;

// UC16: QuantityDTO maintained for cross-package visibility
public class QuantityDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private double value;
    private String unit;
    private String measurementType;
    private boolean error;
    private String errorMessage;

    // Constructor for success
    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
        this.error = false;
    }

    // Constructor for errors
    public QuantityDTO(boolean error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    @Override
    public String toString() {
        if (error) {
            return "QuantityDTO [error=" + error + ", message=" + errorMessage + "]";
        }
        return "QuantityDTO [value=" + value + ", unit=" + unit + ", type=" + measurementType + "]";
    }
}