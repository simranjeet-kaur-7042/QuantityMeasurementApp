package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    // Find records by operation type (e.g., ADD, CONVERT, etc.)
    List<QuantityMeasurementEntity> findByOperation(String operation);

    // Find records by input value
    List<QuantityMeasurementEntity> findByInput(String input);

    // Find records created after a specific date
    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);

    // Find all failed/error records
    List<QuantityMeasurementEntity> findByErrorTrue();

    // Count successful operations by type
    long countByOperationAndErrorFalse(String operation);
}