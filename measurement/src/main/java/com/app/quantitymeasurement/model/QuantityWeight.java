package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.enumimpl.WeightUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityWeight {

    // UC16: Logger added for monitoring weight-specific operations
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityWeight.class);

    private final Quantity<WeightUnit> quantity;

    public QuantityWeight(double value, WeightUnit unit) {
        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    public WeightUnit getUnit() {
        return quantity.getUnit();
    }

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.getUnit());
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null || targetUnit == null) {
            LOGGER.error("Weight Addition Error: Null operand or target unit");
            throw new IllegalArgumentException("Operand and target unit cannot be null");
        }

        LOGGER.info("Weight Addition: {} and {} to target {}", this.quantity, other.quantity, targetUnit);
        
        Quantity<WeightUnit> result = quantity.add(other.quantity, targetUnit);
        return new QuantityWeight(result.getValue(), result.getUnit());
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {
        LOGGER.debug("Weight Conversion: Converting {} to {}", this.quantity, targetUnit);
        
        Quantity<WeightUnit> result = quantity.convertTo(targetUnit);
        return new QuantityWeight(result.getValue(), result.getUnit());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityWeight)) {
            return false;
        }
        
        boolean isEqual = this.quantity.equals(((QuantityWeight) o).quantity);
        
        if (isEqual) {
            LOGGER.debug("Weight Equality Check: Quantities match");
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