package com.app.quantitymeasurement.enumimpl;

import com.app.quantitymeasurement.enums.IMeasurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(1.0/2.20462);

    // UC16: Logger added for tracking weight conversions
    private static final Logger LOGGER = LoggerFactory.getLogger(WeightUnit.class);
    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            LOGGER.error("Weight conversion failed: Value {} is not finite", value);
            throw new IllegalArgumentException("Invalid value");
        }
        double result = value * conversionFactor;
        LOGGER.debug("Weight: Converted {} {} to {} KILOGRAM (Base Unit)", value, this.name(), result);
        return result;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            LOGGER.error("Weight conversion failed: Value {} is not finite", value);
            throw new IllegalArgumentException("Invalid value");
        }
        double result = value / conversionFactor;
        LOGGER.debug("Weight: Converted {} KILOGRAM back to {} {}", value, result, this.name());
        return result;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }
}