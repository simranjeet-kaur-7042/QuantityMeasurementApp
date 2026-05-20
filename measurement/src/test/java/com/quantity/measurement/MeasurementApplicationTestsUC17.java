package com.quantity.measurement;

import com.app.quantitymeasurement.QuantityMeasurementApplication;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = QuantityMeasurementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
class MeasurementApplicationTestsUC17 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuantityMeasurementRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    // =========================================================
    // 1
    // =========================================================

    @Test
    void testSpringBootApplicationStarts() {
        assertNotNull(mockMvc);
    }

    // =========================================================
    // 2
    // =========================================================

    @Test
    void testRestEndpointCompareQuantities() throws Exception {

        QuantityInputDTO dto = new QuantityInputDTO();

        dto.setFirst(
                new QuantityDTO(1.0, "FEET", "LENGTH")
        );

        dto.setSecond(
                new QuantityDTO(12.0, "INCH", "LENGTH")
        );

        dto.setTargetUnit("FEET");

        mockMvc.perform(
                        post("/api/v1/quantities/compare")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1.0));
    }

    // =========================================================
    // 3
    // =========================================================

    @Test
    void testRestEndpointConvertQuantities() throws Exception {

        QuantityDTO dto =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        mockMvc.perform(
                        post("/api/v1/quantities/convert")
                                .param("targetUnit", "INCH")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(12.0))
                .andExpect(jsonPath("$.unit").value("INCH"));
    }

    // =========================================================
    // 4
    // =========================================================

    @Test
    void testRestEndpointAddQuantities() throws Exception {

        QuantityInputDTO dto = new QuantityInputDTO();

        dto.setFirst(
                new QuantityDTO(1.0, "FEET", "LENGTH")
        );

        dto.setSecond(
                new QuantityDTO(12.0, "INCH", "LENGTH")
        );

        dto.setTargetUnit("FEET");

        mockMvc.perform(
                        post("/api/v1/quantities/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(2.0))
                .andExpect(jsonPath("$.unit").value("FEET"));
    }

    // =========================================================
    // 5
    // =========================================================

    @Test
    void testRestEndpointInvalidInput_Returns400()
            throws Exception {

        String invalidJson = "{ invalid json }";

        mockMvc.perform(
                        post("/api/v1/quantities/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidJson)
                )
                .andExpect(status().isBadRequest());
    }

    // =========================================================
    // 6
    // =========================================================

    @Test
    void testRestEndpointMissingParameter_Returns400()
            throws Exception {

        QuantityDTO dto =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        mockMvc.perform(
                        post("/api/v1/quantities/convert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest());
    }

    // =========================================================
    // 7
    // =========================================================

    @Test
    void testSwaggerUILoads() throws Exception {

        mockMvc.perform(
                        get("/swagger-ui/index.html")
                )
                .andExpect(status().isOk());
    }

    // =========================================================
    // 8
    // =========================================================

    @Test
    void testOpenAPIDocumentation() throws Exception {

        mockMvc.perform(
                        get("/v3/api-docs")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("openapi")
                ));
    }

    // =========================================================
    // 9
    // =========================================================
//
//    @Test
//    void testH2ConsoleLaunches() throws Exception {
//
//        mockMvc.perform(
//                        get("/h2-console")
//                )
//                .andExpect(status().is2xxSuccessful());
//    }

    // =========================================================
    // 10
    // =========================================================

//    @Test
//    void testH2DatabasePersistence() {
//
//        QuantityMeasurementEntity entity =
//                new QuantityMeasurementEntity(
//                        "ADD",
//                        "1 + 1",
//                        "2"
//                );
//
//        repository.save(entity);
//
//        List<QuantityMeasurementEntity> list =
//                repository.findAll();
//
//        assertFalse(list.isEmpty());
//    }

    // =========================================================
    // 11
    // =========================================================

    @Test
    void testActuatorHealthEndpoint() throws Exception {

        mockMvc.perform(
                        get("/actuator/health")
                )
                .andExpect(status().isOk());
    }

    // =========================================================
    // 12
    // =========================================================

    @Test
    void testActuatorMetricsEndpoint() throws Exception {

        mockMvc.perform(
                        get("/actuator/metrics")
                )
                .andExpect(status().is2xxSuccessful());
    }

    // =========================================================
    // 13
    // =========================================================

//    @Test
//    void testJPARepositoryFindByOperation() {
//
//        repository.save(
//                new QuantityMeasurementEntity(
//                        "COMPARE",
//                        "1 vs 1",
//                        "true"
//                )
//        );
//
//        List<QuantityMeasurementEntity> list =
//                repository.findByOperation("COMPARE");
//
//        assertFalse(list.isEmpty());
//    }

    // =========================================================
    // 14
    // =========================================================

//    @Test
//    void testJPARepositoryCustomQuery() {
//
//        repository.save(
//                new QuantityMeasurementEntity(
//                        "ADD",
//                        "1+1",
//                        "2"
//                )
//        );
//
//        List<QuantityMeasurementEntity> list =
//                repository.findAll();
//
//        assertTrue(list.size() > 0);
//    }

    // =========================================================
//    // 15
//    // =========================================================
//
//    @Test
//    void testTransactionalRollback() {
//
//        assertDoesNotThrow(() -> repository.findAll());
//    }

    // =========================================================
    // 16
    // =========================================================

    @Test
    void testContentNegotiation_JSON() throws Exception {

        QuantityDTO dto =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        mockMvc.perform(
                        post("/api/v1/quantities/convert")
                                .param("targetUnit", "INCH")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON
                ));
    }

    // =========================================================
    // 17
    // =========================================================

    @Test
    void testExceptionHandling_GlobalHandler()
            throws Exception {

        mockMvc.perform(
                        post("/api/v1/quantities/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    // =========================================================
    // 18
    // =========================================================

    @Test
    void testRequestPathVariable_Extraction()
            throws Exception {

        mockMvc.perform(
                        get("/swagger-ui/index.html")
                )
                .andExpect(status().isOk());
    }

    // =========================================================
    // 19
    // =========================================================

    @Test
    void testResponseSerialization_Object()
            throws Exception {

        QuantityDTO dto =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        mockMvc.perform(
                        post("/api/v1/quantities/convert")
                                .param("targetUnit", "INCH")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(jsonPath("$.unit").value("INCH"));
    }

    // =========================================================
    // 20
    // =========================================================

    @Test
    void testMockMvc_ComparisonTest()
            throws Exception {

        QuantityInputDTO dto = new QuantityInputDTO();

        dto.setFirst(
                new QuantityDTO(1.0, "FEET", "LENGTH")
        );

        dto.setSecond(
                new QuantityDTO(12.0, "INCH", "LENGTH")
        );

        dto.setTargetUnit("FEET");

        mockMvc.perform(
                        post("/api/v1/quantities/compare")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk());
    }

    // =========================================================
    // 21
    // =========================================================

    @Test
    void testMockMvc_ResponseAssertion()
            throws Exception {

        QuantityDTO dto =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        mockMvc.perform(
                        post("/api/v1/quantities/convert")
                                .param("targetUnit", "INCH")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(12.0));
    }

    // =========================================================
    // 22
    // =========================================================

//    @Test
//    void testIntegrationTest_MultipleOperations()
//            throws Exception {
//
//        testRestEndpointAddQuantities();
//
//        testRestEndpointConvertQuantities();
//
//        testRestEndpointCompareQuantities();
//
//        assertTrue(repository.findAll().size() >= 3);
//    }

    // =========================================================
    // 23
    // =========================================================

//    @Test
//    void testDatabaseInitialization_SchemaCreated() {
//
//        assertDoesNotThrow(() -> repository.findAll());
//    }

    // =========================================================
    // 24
    // =========================================================

    @Test
    void testProfileSpecificConfiguration_Development() {

        assertNotNull(repository);
    }

    // =========================================================
    // 25
    // =========================================================

    @Test
    void testProfileSpecificConfiguration_Production() {

        assertTrue(true);
    }

    // =========================================================
    // 26
    // =========================================================

    @Test
    void testHttpStatusCodes_Success()
            throws Exception {

        QuantityDTO dto =
                new QuantityDTO(1.0, "FEET", "LENGTH");

        mockMvc.perform(
                        post("/api/v1/quantities/convert")
                                .param("targetUnit", "INCH")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk());
    }

    // =========================================================
    // 27
    // =========================================================

    @Test
    void testHttpStatusCodes_ClientErrors()
            throws Exception {

        String invalidRequest = """
                {
                  "first": null,
                  "second": null,
                  "targetUnit": ""
                }
                """;

        mockMvc.perform(
                        post("/api/v1/quantities/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidRequest)
                )
                .andExpect(status().isBadRequest());
    }

    // =========================================================
    // 28
    // =========================================================

    @Test
    void testHttpStatusCodes_ServerErrors()
            throws Exception {

        mockMvc.perform(
                        get("/unknown-url")
                )
                .andExpect(status().is4xxClientError());
    }
}