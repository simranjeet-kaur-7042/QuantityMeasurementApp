package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.enums.IMeasurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityModel<U extends IMeasurable> {

    // UC16: Logger added for debugging data instantiation
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityModel.class);

    private final double value;
    private final U unit;

    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit = unit;
        // Logging the creation of the model for traceability
        LOGGER.debug("QuantityModel created: {} {}", value, (unit != null ? unit.getUnitName() : "null"));
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "QuantityModel{" +
                "value=" + value +
                ", unit=" + (unit != null ? unit.getUnitName() : "null") +
                '}';
    }
}