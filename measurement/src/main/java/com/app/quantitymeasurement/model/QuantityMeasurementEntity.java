//package com.app.quantitymeasurement.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "quantity_measurements")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class QuantityMeasurementEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String operation;
//
//    @Column(length = 1000)
//    private String input;
//
//    @Column(length = 1000)
//    private String result;
//
//    @Column(nullable = false)
//    private boolean error;
//
//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;
//
//    // Constructor for successful operation
//    public QuantityMeasurementEntity(String operation,
//                                     String input,
//                                     String result) {
//
//        this.operation = operation;
//        this.input = input;
//        this.result = result;
//        this.error = false;
//    }
//
//    // Constructor for failed operation
//    public QuantityMeasurementEntity(String operation,
//                                     String errorMessage) {
//
//        this.operation = operation;
//        this.input = null;
//        this.result = errorMessage;
//        this.error = true;
//    }
//
//    @PrePersist
//    public void prePersist() {
//
//        this.createdAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//
//        this.updatedAt = LocalDateTime.now();
//    }
//}

package com.app.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String operation;

    @Column(length = 1000)
    private String input;

    @Column(length = 1000)
    private String result;

    @Column(nullable = false)
    private boolean error;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Success constructor
    public QuantityMeasurementEntity(String operation, String input, String result) {
        this.operation = operation;
        this.input = input;
        this.result = result;
        this.error = false;
    }

    // Error constructor
    public QuantityMeasurementEntity(String operation, String errorMessage) {
        this.operation = operation;
        this.input = null;
        this.result = errorMessage;
        this.error = true;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}