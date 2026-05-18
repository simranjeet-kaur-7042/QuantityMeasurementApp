package com.app.quantitymeasurement.enumimpl;

import com.app.quantitymeasurement.enums.IMeasurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LengthUnit implements IMeasurable {

    FEET(1.0),
    INCH(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    // UC16: Logger added for tracking conversion factors
    private static final Logger LOGGER = LoggerFactory.getLogger(LengthUnit.class);
    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    @Override
    public double getConversionFactor() {
        return toFeetFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            LOGGER.error("Conversion failed: Value is not finite: {}", value);
            throw new IllegalArgumentException("Invalid value");
        }
        double result = value * toFeetFactor;
        LOGGER.debug("Converted {} {} to {} BaseUnits (FEET)", value, this.name(), result);
        return result;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            LOGGER.error("Conversion failed: Value is not finite: {}", value);
            throw new IllegalArgumentException("Invalid value");
        }
        return value / toFeetFactor;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }
}