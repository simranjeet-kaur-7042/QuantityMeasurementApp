package com.quantity.measurement;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;
import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter first value: ");
            double value1 = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter first unit (FEET, INCH): ");
            String unit1 = scanner.nextLine();

            System.out.print("Enter second value: ");
            double value2 = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter second unit (FEET, INCH): ");
            String unit2 = scanner.nextLine();

            // Convert input to uppercase before using valueOf
            QuantityLength length1 = new QuantityLength(value1, LengthUnit.valueOf(unit1.toUpperCase()));
            QuantityLength length2 = new QuantityLength(value2, LengthUnit.valueOf(unit2.toUpperCase()));

            if (length1.equals(length2)) {
                System.out.println("The two lengths are equal.");
            } else {
                System.out.println("The two lengths are NOT equal.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}