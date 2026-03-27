package com.quantity.measurement;

public class QuantityMeasurementApp {

    // Inner class for Feet
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Inner class for Inches
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Method to test Feet equality
    public static void testFeetEquality() {
        Feet f1 = new Feet(5.0);
        Feet f2 = new Feet(5.0);

        System.out.println("Feet equal? " + f1.equals(f2));
    }

    // Method to test Inches equality
    public static void testInchesEquality() {
        Inches i1 = new Inches(10.0);
        Inches i2 = new Inches(10.0);
        Inches i3 = new Inches(12.0);

        System.out.println("Inches equal (same values)? " + i1.equals(i2));
        System.out.println("Inches equal (different values)? " + i1.equals(i3));
    }

    // Main method
    public static void main(String[] args) {
        testFeetEquality();
        testInchesEquality();
    }
}