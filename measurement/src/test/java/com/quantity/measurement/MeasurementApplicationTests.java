package com.quantity.measurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MeasurementApplicationTests {

    @Test
    void testFeetEquality_SameValue() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(89.6);
        QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(89.6);

        assertEquals(f1, f2);
    }

    @Test
    void testFeetEquality_DifferentValue() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(89.6);
        QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(34.6);

        assertNotEquals(f1, f2);
    }

    @Test
    void testFeetEquality_NullValue() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(5.0);

        assertNotEquals(f1, null);
    }

    @Test
    void testFeetEquality_DifferentType() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(5.0);
        String other = "5.0";

        assertNotEquals(f1, other);
    }
}