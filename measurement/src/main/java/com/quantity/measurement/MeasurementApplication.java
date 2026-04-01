package com.quantity.measurement;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeasurementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeasurementApplication.class, args);

        // Static values logic (moved here)
        double value1 = 1.0;
        LengthUnit unit1 = LengthUnit.FEET;

        double value2 = 12.0;
        LengthUnit unit2 = LengthUnit.INCH;

        QuantityLength length1 = new QuantityLength(value1, unit1);
        QuantityLength length2 = new QuantityLength(value2, unit2);

    }
}