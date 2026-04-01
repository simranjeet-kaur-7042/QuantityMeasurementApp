package com.quantity.measurement;
import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;

public class QuantityMeasurementApp {
    public static void main(String[] args) {
            double value1 = 1.0;
            LengthUnit unit1 = LengthUnit.FEET;

            double value2 = 12.0;
            LengthUnit unit2 = LengthUnit.INCH;

            QuantityLength length1 = new QuantityLength(value1, unit1);
            QuantityLength length2 = new QuantityLength(value2, unit2);  
    }
}