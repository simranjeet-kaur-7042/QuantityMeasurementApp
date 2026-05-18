package com.app.quantitymeasurement.serviceimpl;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.Entity;
import com.app.quantitymeasurement.enums.IMeasurable;
import com.app.quantitymeasurement.enumimpl.LengthUnit;
import com.app.quantitymeasurement.enumimpl.VolumeUnit;
import com.app.quantitymeasurement.enumimpl.WeightUnit;
import com.app.quantitymeasurement.enumimpl.TemperatureUnit;
import com.app.quantitymeasurement.model.Quantity;
import com.app.quantitymeasurement.repository.Repository;
import com.app.quantitymeasurement.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceImpl implements Service {

    // UC16: Logger added for monitoring business logic
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImpl.class);
    private final Repository repository;

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    private IMeasurable getUnit(String unit, String type) throws Exception {
        if (unit == null || type == null) {
            LOGGER.error("Validation failed: Unit or Type is null");
            throw new Exception("Invalid unit or type");
        }

        try {
            return switch (type.toUpperCase()) {
                case "LENGTH" -> LengthUnit.valueOf(unit.toUpperCase());
                case "WEIGHT" -> WeightUnit.valueOf(unit.toUpperCase());
                case "VOLUME" -> VolumeUnit.valueOf(unit.toUpperCase());
                case "TEMPERATURE" -> TemperatureUnit.valueOf(unit.toUpperCase());
                default -> throw new Exception("Invalid type: " + type);
            };
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid unit {} for type {}", unit, type);
            throw new Exception("Unit not found");
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        LOGGER.info("Service Request: ADD {} and {} to {}", q1, q2, targetUnit);
        try {
            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());
            IMeasurable target = getUnit(targetUnit, q1.getMeasurementType());

            Quantity<?> result = new Quantity<>(q1.getValue(), u1)
                    .add(new Quantity<>(q2.getValue(), u2), target);

            // UC16: Save audit log to repository
            repository.save(new Entity("ADD", q1.getValue() + " + " + q2.getValue(), String.valueOf(result.getValue())));
            
            return new QuantityDTO(result.getValue(), targetUnit, q1.getMeasurementType());
        } catch (Exception e) {
            LOGGER.error("Add Operation Failed: {}", e.getMessage());
            repository.save(new Entity("ADD_ERROR", "input_fail", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        LOGGER.info("Service Request: SUBTRACT {} from {} to {}", q2, q1, targetUnit);
        try {
            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());
            IMeasurable target = getUnit(targetUnit, q1.getMeasurementType());

            Quantity<?> result = new Quantity<>(q1.getValue(), u1)
                    .subtract(new Quantity<>(q2.getValue(), u2), target);

            repository.save(new Entity("SUBTRACT", q1.getValue() + " - " + q2.getValue(), String.valueOf(result.getValue())));
            return new QuantityDTO(result.getValue(), targetUnit, q1.getMeasurementType());
        } catch (Exception e) {
            LOGGER.error("Subtract Operation Failed: {}", e.getMessage());
            repository.save(new Entity("SUBTRACT_ERROR", "input_fail", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO divide(QuantityDTO q1, QuantityDTO q2) {
        LOGGER.info("Service Request: DIVIDE {} by {}", q1, q2);
        try {
            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            double result = new Quantity<>(q1.getValue(), u1)
                    .divide(new Quantity<>(q2.getValue(), u2));

            repository.save(new Entity("DIVIDE", q1.getValue() + " / " + q2.getValue(), String.valueOf(result)));
            return new QuantityDTO(result, "SCALAR", q1.getMeasurementType());
        } catch (Exception e) {
            LOGGER.error("Divide Operation Failed: {}", e.getMessage());
            repository.save(new Entity("DIVIDE_ERROR", "input_fail", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO q, String targetUnit) {
        LOGGER.info("Service Request: CONVERT {} to {}", q, targetUnit);
        
        // --- SAFE CHECK FOR NULL (UC16 Tests protection) ---
        if (q == null) {
            LOGGER.error("Convert Operation Failed: Input QuantityDTO is null");
            return new QuantityDTO(true, "Input cannot be null");
        }
        // ----------------------------------------------------

        try {
            IMeasurable u = getUnit(q.getUnit(), q.getMeasurementType());
            IMeasurable target = getUnit(targetUnit, q.getMeasurementType());

            Quantity<?> result = new Quantity<>(q.getValue(), u).convertTo(target);

            repository.save(new Entity("CONVERT", q.getValue() + " " + q.getUnit(), result.getValue() + " " + targetUnit));
            return new QuantityDTO(result.getValue(), targetUnit, q.getMeasurementType());
        } catch (Exception e) {
            LOGGER.error("Convert Operation Failed: {}", e.getMessage());
            // repository check: agar q null nahi hai tabhi save karein warna logic crash hoga
            repository.save(new Entity("CONVERT_ERROR", (q != null ? q.getUnit() : "NULL"), e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO compare(QuantityDTO q1, QuantityDTO q2) {
        LOGGER.info("Service Request: COMPARE {} and {}", q1, q2);
        try {
            if (!q1.getMeasurementType().equalsIgnoreCase(q2.getMeasurementType())) {
                throw new Exception("Cross-category comparison not allowed");
            }

            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            boolean result = new Quantity<>(q1.getValue(), u1)
                    .equals(new Quantity<>(q2.getValue(), u2));

            repository.save(new Entity("COMPARE", q1.getValue() + " vs " + q2.getValue(), String.valueOf(result)));
            return new QuantityDTO(result ? 1 : 0, "BOOLEAN", q1.getMeasurementType());
        } catch (Exception e) {
            LOGGER.error("Compare Operation Failed: {}", e.getMessage());
            repository.save(new Entity("COMPARE_ERROR", "input_fail", e.getMessage()));
            return new QuantityDTO(true, e.getMessage());
        }
    }
}