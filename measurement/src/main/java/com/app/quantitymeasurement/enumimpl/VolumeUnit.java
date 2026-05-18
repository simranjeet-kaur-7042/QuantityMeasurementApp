package com.app.quantitymeasurement.enumimpl;

import com.app.quantitymeasurement.enums.IMeasurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    // UC16: Logger added
    private static final Logger LOGGER = LoggerFactory.getLogger(VolumeUnit.class);
    private final double conversionFactor;

    VolumeUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        double result = value * conversionFactor;
        LOGGER.debug("Volume: Converting {} {} to {} LITRE (Base Unit)", value, this.name(), result);
        return result;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        double result = baseValue / conversionFactor;
        LOGGER.debug("Volume: Converting {} LITRE back to {} {}", baseValue, result, this.name());
        return result;
    }

    @Override
    public String getUnitName() {
        return name();
    }
}