package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {

    // UC16: Added Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public QuantityDTO performAdd(QuantityDTO q1, QuantityDTO q2, String target) {
        LOGGER.info("Controller: Performing Addition for {} and {} to {}", q1.getValue(), q2.getValue(), target);
        return service.add(q1, q2, target);
    }

    public QuantityDTO performSubtract(QuantityDTO q1, QuantityDTO q2, String target) {
        LOGGER.info("Controller: Performing Subtraction for {} and {} to {}", q1.getValue(), q2.getValue(), target);
        return service.subtract(q1, q2, target);
    }

    public QuantityDTO performDivide(QuantityDTO q1, QuantityDTO q2) {
        LOGGER.info("Controller: Performing Division for {} and {}", q1.getValue(), q2.getValue());
        return service.divide(q1, q2);
    }

    public QuantityDTO performConvert(QuantityDTO q, String target) {
        LOGGER.info("Controller: Performing Conversion for {} to {}", q.getValue(), target);
        return service.convert(q, target);
    }

    public QuantityDTO performCompare(QuantityDTO q1, QuantityDTO q2) {
        LOGGER.info("Controller: Performing Comparison between {} and {}", q1.getValue(), q2.getValue());
        return service.compare(q1, q2);
    }
}