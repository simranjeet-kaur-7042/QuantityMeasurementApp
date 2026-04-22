package com.quantity.measurement.enums;

public enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    TON(1000.0),
    POUND(0.453592);  

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be a finite number");

        return value * conversionFactor;
    }

    public double convertFromBaseUnit(double valueInBase) {
        if (!Double.isFinite(valueInBase))
            throw new IllegalArgumentException("Value must be a finite number");

        return valueInBase / conversionFactor;
    }
}