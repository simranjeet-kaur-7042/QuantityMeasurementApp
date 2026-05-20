package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurement APIs")
public class QuantityMeasurementController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(QuantityMeasurementController.class);

    @Autowired
    private IQuantityMeasurementService service;

    // ==========================
    // ADD
    // ==========================

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityDTO> performAdd(
            @Valid @RequestBody QuantityInputDTO input) {

        LOGGER.info("REST Request: ADD");

        QuantityDTO response =
                service.add(
                        input.getFirst(),
                        input.getSecond(),
                        input.getTargetUnit()
                );

        return ResponseEntity.ok(response);
    }

    // ==========================
    // SUBTRACT
    // ==========================

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityDTO> performSubtract(
            @Valid @RequestBody QuantityInputDTO input) {

        LOGGER.info("REST Request: SUBTRACT");

        QuantityDTO response =
                service.subtract(
                        input.getFirst(),
                        input.getSecond(),
                        input.getTargetUnit()
                );

        return ResponseEntity.ok(response);
    }

    // ==========================
    // DIVIDE
    // ==========================

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityDTO> performDivide(
            @Valid @RequestBody QuantityInputDTO input) {

        LOGGER.info("REST Request: DIVIDE");

        QuantityDTO response =
                service.divide(
                        input.getFirst(),
                        input.getSecond()
                );

        return ResponseEntity.ok(response);
    }

    // ==========================
    // CONVERT
    // ==========================

    @PostMapping("/convert")
    @Operation(summary = "Convert quantity")
    public ResponseEntity<QuantityDTO> performConvert(
            @RequestParam String targetUnit,
            @Valid @RequestBody QuantityDTO quantityDTO) {

        LOGGER.info("REST Request: CONVERT");

        QuantityDTO response =
                service.convert(quantityDTO, targetUnit);

        return ResponseEntity.ok(response);
    }

    // ==========================
    // COMPARE
    // ==========================

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityDTO> performCompare(
            @Valid @RequestBody QuantityInputDTO input) {

        LOGGER.info("REST Request: COMPARE");

        QuantityDTO response =
                service.compare(
                        input.getFirst(),
                        input.getSecond()
                );

        return ResponseEntity.ok(response);
    }
}