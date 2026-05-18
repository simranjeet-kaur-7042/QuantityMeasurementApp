package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.enumimpl.TemperatureUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityTemperature {

    // UC16: Logger added for temperature specific operations
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityTemperature.class);

    private final Quantity<TemperatureUnit> quantity;

    public QuantityTemperature(double value, TemperatureUnit unit) {
        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    public TemperatureUnit getUnit() {
        return quantity.getUnit();
    }

    public QuantityTemperature convertTo(TemperatureUnit targetUnit) {
        LOGGER.info("Temperature: Attempting conversion from {} to {}", 
                    this.quantity.getUnit(), targetUnit);

        Quantity<TemperatureUnit> result = quantity.convertTo(targetUnit);

        LOGGER.debug("Temperature: Conversion result - {} {}", result.getValue(), result.getUnit());

        return new QuantityTemperature(
                result.getValue(),
                result.getUnit()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityTemperature other)) {
            return false;
        }

        boolean isEqual = this.quantity.equals(other.quantity);
        
        if (isEqual) {
            LOGGER.debug("Temperature Equality: {} equals {}", this.quantity, other.quantity);
        }
        
        return isEqual;
    }

    @Override
    public int hashCode() {
        return quantity.hashCode();
    }

    @Override
    public String toString() {
        return quantity.toString();
    }
}