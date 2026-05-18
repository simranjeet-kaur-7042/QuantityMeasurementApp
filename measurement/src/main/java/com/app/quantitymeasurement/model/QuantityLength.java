package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.enumimpl.LengthUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityLength {

    // UC16: Logger added for monitoring length-specific operations
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityLength.class);

    private final Quantity<LengthUnit> quantity;

    public QuantityLength(double value, LengthUnit unit) {
        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    public LengthUnit getUnit() {
        return quantity.getUnit();
    }

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            LOGGER.error("Addition failed: Missing operand or target unit");
            throw new IllegalArgumentException("Other quantity and target unit must not be null");
        }

        LOGGER.info("Length Addition: Adding {} and {} to target unit {}", 
                    this.quantity, other.quantity, targetUnit);

        Quantity<LengthUnit> result = this.quantity.add(other.quantity, targetUnit);
        return new QuantityLength(result.getValue(), result.getUnit());
    }

    // ADD
    public QuantityLength add(QuantityLength other) {
        return add(other, this.getUnit());
    }

    // CONVERT
    public QuantityLength toConvert(LengthUnit targetUnit) {
        LOGGER.debug("Length Conversion: Converting {} to {}", this.quantity, targetUnit);
        Quantity<LengthUnit> result = this.quantity.convertTo(targetUnit);
        return new QuantityLength(result.getValue(), result.getUnit());
    }

    // EQUALS
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;
        QuantityLength other = (QuantityLength) obj;
        
        boolean isEqual = this.quantity.equals(other.quantity);
        if (isEqual) {
            LOGGER.debug("Equality Check: {} matches {}", this.quantity, other.quantity);
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