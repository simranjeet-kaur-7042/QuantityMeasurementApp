package com.app.quantitymeasurement.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IMeasurable {

    // UC16: Logger added for the interface to track validation failures
    Logger LOGGER = LoggerFactory.getLogger(IMeasurable.class);

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();

    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
        // Default implementation does nothing, but we log the check
        LOGGER.debug("Validating operation support for: {}", operation);
    }
}