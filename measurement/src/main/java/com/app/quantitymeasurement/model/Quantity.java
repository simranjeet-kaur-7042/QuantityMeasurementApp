package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.enums.IMeasurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    // UC16: Logger added for monitoring operations
    private static final Logger LOGGER = LoggerFactory.getLogger(Quantity.class);

    private static final double EPSILON = 1e-6;

    private final double value;
    private final U unit;

    // ================= CONSTRUCTOR =================

    public Quantity(double value, U unit) {
        if (unit == null) {
            LOGGER.error("Constructor failed: Unit is null");
            throw new IllegalArgumentException("Unit should not be null");
        }

        if (!Double.isFinite(value)) {
            LOGGER.error("Constructor failed: Value {} is not finite", value);
            throw new IllegalArgumentException("Invalid value");
        }

        this.value = value;
        this.unit = unit;
    }

    // ================= ROUNDING =================

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // ================= CONVERSION =================

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        LOGGER.debug("Conversion: {} {} -> {} {}", value, unit, convertedValue, targetUnit);
        return new Quantity<>(convertedValue, targetUnit);
    }

    // ================= ARITHMETIC OPERATIONS =================

    private enum ArithmeticOperation {
        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if (Math.abs(b) < EPSILON) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        });

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        double apply(double a, double b) {
            return operator.applyAsDouble(a, b);
        }
    }

    // ================= GETTERS =================

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // ================= VALIDATION =================

    private void validate(Quantity<U> other, U targetUnit, boolean requireTarget) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity must not be null");
        }

        if (requireTarget && targetUnit == null) {
            throw new IllegalArgumentException("Target unit must not be null");
        }

        if (!this.unit.getClass().equals(other.unit.getClass())) {
            LOGGER.warn("Type Mismatch: Cannot operate on {} and {}", this.unit.getClass().getSimpleName(), other.unit.getClass().getSimpleName());
            throw new IllegalArgumentException("Different measurement types");
        }
    }

    // ================= INTERNAL HELPERS =================

    private double toBaseValue(U unit, double value) {
        return unit.convertToBaseUnit(value);
    }

    private double operate(Quantity<U> other, ArithmeticOperation operation) {
        double first = toBaseValue(this.unit, this.value);
        double second = toBaseValue(other.unit, other.value);
        return operation.apply(first, second);
    }

    // ================= ADDITION =================

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validate(other, targetUnit, true);
        double resultBase = operate(other, ArithmeticOperation.ADD);
        double convertedResult = targetUnit.convertFromBaseUnit(resultBase);

        LOGGER.info("Addition: {} {} + {} {} = {} {}", this.value, this.unit, other.value, other.unit, convertedResult, targetUnit);
        return new Quantity<>(convertedResult, targetUnit);
    }

    // ================= SUBTRACTION =================

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validate(other, targetUnit, true);
        double resultBase = operate(other, ArithmeticOperation.SUBTRACT);
        double convertedResult = targetUnit.convertFromBaseUnit(resultBase);

        // UC15 math logic preserved (rounding for subtraction)
        convertedResult = Math.round(convertedResult * 100.0) / 100.0;

        LOGGER.info("Subtraction: {} {} - {} {} = {} {}", this.value, this.unit, other.value, other.unit, convertedResult, targetUnit);
        return new Quantity<>(convertedResult, targetUnit);
    }

    // ================= DIVISION =================

    public double divide(Quantity<U> other) {
        validate(other, null, false);
        double result = round(operate(other, ArithmeticOperation.DIVIDE));
        LOGGER.info("Division: {} {} / {} {} = {}", this.value, this.unit, other.value, other.unit, result);
        return result;
    }

    // ================= EQUALS =================

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity<?> other = (Quantity<?>) obj;
        if (!this.unit.getClass().equals(other.unit.getClass())) return false;

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        boolean isEqual = Math.abs(thisBase - otherBase) < EPSILON;
        if (isEqual) {
            LOGGER.debug("Equality Check: {} {} equals {} {}", this.value, this.unit, other.value, other.unit);
        }
        return isEqual;
    }

    // ================= HASHCODE =================

    @Override
    public int hashCode() {
        double baseValue = unit.convertToBaseUnit(value);
        return Objects.hash(Math.round(baseValue / EPSILON));
    }

    // ================= TOSTRING =================

    @Override
    public String toString() {
        return value + " " + unit;
    }
}