package com.quantity.measurement.model;

import com.quantity.measurement.enumimpl.TemperatureUnit;

public class QuantityTemperature {

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

        Quantity<TemperatureUnit> result =
                quantity.convertTo(targetUnit);

        return new QuantityTemperature(
                result.getValue(),
                result.getUnit()
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof QuantityTemperature other)) {
            return false;
        }

        return this.quantity.equals(other.quantity);
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