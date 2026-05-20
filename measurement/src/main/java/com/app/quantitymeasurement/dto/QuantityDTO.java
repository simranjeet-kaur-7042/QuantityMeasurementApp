package com.app.quantitymeasurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class QuantityDTO {

    @NotNull(message = "Value cannot be null")
    @Positive(message = "Value must be positive")
    private Double value;

    @NotBlank(message = "Unit cannot be empty")
    private String unit;

    @NotBlank(message = "Measurement type cannot be empty")
    private String measurementType;

    private boolean error;

    private String errorMessage;

    // Default constructor
    public QuantityDTO() {
    }

    // Full constructor
    public QuantityDTO(Double value,
                       String unit,
                       String measurementType,
                       boolean error,
                       String errorMessage) {

        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
        this.error = error;
        this.errorMessage = errorMessage;
    }

    // Success constructor
    public QuantityDTO(Double value,
                       String unit,
                       String measurementType) {

        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
        this.error = false;
    }

    // Error constructor
    public QuantityDTO(boolean error,
                       String errorMessage) {

        this.error = error;
        this.errorMessage = errorMessage;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {

        if (error) {

            return "QuantityDTO [error=" +
                    error +
                    ", message=" +
                    errorMessage +
                    "]";
        }

        return "QuantityDTO [value=" +
                value +
                ", unit=" +
                unit +
                ", type=" +
                measurementType +
                "]";
    }
}