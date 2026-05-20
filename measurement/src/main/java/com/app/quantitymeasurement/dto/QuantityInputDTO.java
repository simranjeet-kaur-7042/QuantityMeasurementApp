package com.app.quantitymeasurement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class QuantityInputDTO {

    @Valid
    private QuantityDTO first;

    @Valid
    private QuantityDTO second;

    @NotBlank(message = "Target unit cannot be empty")
    private String targetUnit;

    public QuantityInputDTO() {
    }

    public QuantityInputDTO(QuantityDTO first,
                            QuantityDTO second,
                            String targetUnit) {

        this.first = first;
        this.second = second;
        this.targetUnit = targetUnit;
    }

    public QuantityDTO getFirst() {
        return first;
    }

    public void setFirst(QuantityDTO first) {
        this.first = first;
    }

    public QuantityDTO getSecond() {
        return second;
    }

    public void setSecond(QuantityDTO second) {
        this.second = second;
    }

    public String getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(String targetUnit) {
        this.targetUnit = targetUnit;
    }
}