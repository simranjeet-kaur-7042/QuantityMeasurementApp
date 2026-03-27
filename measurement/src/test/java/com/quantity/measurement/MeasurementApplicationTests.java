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
    @Test
    void testFeetEquality_ClassComparison() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(89.6);
    

        assertFalse(f1.equals("Some String"));
    }
    
    
    @Test
    void testFeetEquality_SameReference() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(89.6);
    

        assertTrue(f1.equals(f1));
    }
    
    @Test
    void testInchesEquality_SameValue() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(10.5);
        QuantityMeasurementApp.Inches i2 = new QuantityMeasurementApp.Inches(10.5);

        assertEquals(i1, i2);
    }

    @Test
    void testInchesEquality_DifferentValue() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(10.5);
        QuantityMeasurementApp.Inches i2 = new QuantityMeasurementApp.Inches(5.2);

        assertNotEquals(i1, i2);
    }

    @Test
    void testInchesEquality_NullValue() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(7.0);

        assertNotEquals(i1, null);
    }

    @Test
    void testInchesEquality_DifferentType() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(7.0);
        String other = "7.0";

        assertNotEquals(i1, other);
    }

    @Test
    void testInchesEquality_ClassComparison() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(10.5);

        assertFalse(i1.equals("Some String"));
    }

    @Test
    void testInchesEquality_SameReference() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(10.5);

        assertTrue(i1.equals(i1));
    }
}