package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;

public interface IQuantityMeasurementService {

    QuantityDTO add(QuantityDTO q1,
                    QuantityDTO q2,
                    String targetUnit);

    QuantityDTO subtract(QuantityDTO q1,
                         QuantityDTO q2,
                         String targetUnit);

    QuantityDTO divide(QuantityDTO q1,
                       QuantityDTO q2);

    QuantityDTO convert(QuantityDTO q,
                        String targetUnit);

    QuantityDTO compare(QuantityDTO q1,
                        QuantityDTO q2);
}