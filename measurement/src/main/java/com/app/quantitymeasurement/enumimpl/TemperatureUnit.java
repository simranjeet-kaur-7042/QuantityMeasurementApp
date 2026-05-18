package com.app.quantitymeasurement.enumimpl;

import com.app.quantitymeasurement.enums.IMeasurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            1.0,
            celsius -> celsius,
            celsius -> celsius
    ),

    FAHRENHEIT(
            1.0,
            fahrenheit -> (fahrenheit - 32) * 5.0 / 9.0,
            celsius -> (celsius * 9.0 / 5.0) + 32
    ),

    KELVIN(
            1.0,
            kelvin -> kelvin - 273.15,
            celsius -> celsius + 273.15
    );

    // UC16: Logger initialization
    private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureUnit.class);

    private final double conversionFactor;
    private final Function<Double, Double> toCelsius;
    private final Function<Double, Double> fromCelsius;

    TemperatureUnit(
            double conversionFactor,
            Function<Double, Double> toCelsius,
            Function<Double, Double> fromCelsius
    ) {
        this.conversionFactor = conversionFactor;
        this.toCelsius = toCelsius;
        this.fromCelsius = fromCelsius;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public double convertToBaseUnit(double value) {
        validate(value);
        double result = toCelsius.apply(value);
        LOGGER.debug("Temperature: Converted {} {} to {} CELSIUS", value, this.name(), result);
        return result;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        validate(value);
        double result = fromCelsius.apply(value);
        LOGGER.debug("Temperature: Converted {} CELSIUS back to {} {}", value, result, this.name());
        return result;
    }

    @Override
    public boolean supportsArithmetic() {
        return false;
    }

    @Override
    public void validateOperationSupport(String operation) {
        LOGGER.warn("Temperature: Arithmetic operation '{}' attempted but not supported", operation);
        throw new UnsupportedOperationException(
                "Temperature does not support " +
                        operation +
                        " operation. Arithmetic operations are not meaningful for absolute temperatures."
        );
    }

    private void validate(double value) {
        if (!Double.isFinite(value)) {
            LOGGER.error("Temperature Validation Error: Value is not finite ({})", value);
            throw new IllegalArgumentException("Invalid value");
        }
    }
}