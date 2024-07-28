package com.cheise_proj.auditing;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@Slf4j
class CustomerControllerIT extends IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCustomer_returns_201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.createCustomer(objectMapper))

                ).andExpectAll(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")));
    }

    @Test
    void createCustomer_With_Address_returns_201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.createCustomerWithAddress(objectMapper))

                ).andExpectAll(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")));
    }

    @Test
    void createCustomer_returns_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")

                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")));
    }

    @Test
    void getCustomers_With_Address_returns_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.createCustomerWithAddress(objectMapper))
                ).andExpectAll(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpectAll(MockMvcResultMatchers.status().isOk())
                .andDo(result -> log.info("result: {}", result.getResponse().getContentAsString()));
    }

    @Test
    void getCustomer_by_id_returns_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.createCustomerWithAddress(objectMapper))
                ).andExpectAll(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpectAll(MockMvcResultMatchers.status().isOk())
                .andDo(result -> log.info("result: {}", result.getResponse().getContentAsString()));
    }

    @Test
    void getCustomer_by_id_returns_404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.createCustomerWithAddress(objectMapper))
                ).andExpectAll(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/10")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpectAll(MockMvcResultMatchers.status().isNotFound())
                .andDo(result -> log.info("result: {}", result.getResponse().getContentAsString()));
    }

    @Test
    void updateCustomer_returns_200() throws Exception {
        String customerLocation = (String) mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.createCustomerWithAddress(objectMapper))

                ).andExpectAll(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")))
                .andReturn().getResponse().getHeaderValue("location");

        assert customerLocation != null;
        mockMvc.perform(MockMvcRequestBuilders.put(customerLocation)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.updateCustomer(objectMapper))

                ).andExpectAll(MockMvcResultMatchers.status().isOk())
                .andDo(result -> log.info("result: {}", result.getResponse().getContentAsString()));
    }

    @Test
    void updateCustomer_With_Address_returns_200() throws Exception {
        String customerLocation = (String) mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.createCustomerWithAddress(objectMapper))

                ).andExpectAll(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")))
                .andReturn().getResponse().getHeaderValue("location");

        assert customerLocation != null;
        mockMvc.perform(MockMvcRequestBuilders.put(customerLocation)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.updateCustomerWithAddress(objectMapper))

                ).andExpectAll(MockMvcResultMatchers.status().isOk())
                .andDo(result -> log.info("result: {}", result.getResponse().getContentAsString()));
    }

    @Test
    void updateCustomer_returns_404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.createCustomerWithAddress(objectMapper))

                ).andExpectAll(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")));

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.updateCustomer(objectMapper))

                ).andExpectAll(MockMvcResultMatchers.status().isNotFound())
                .andDo(result -> log.info("result: {}", result.getResponse().getContentAsString()));
    }

    @Test
    void deleteCustomer_returns_204() throws Exception {
        String customerLocation = (String) mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomerFixture.createCustomerWithAddress(objectMapper))

                ).andExpectAll(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> log.info("result: {}", result.getResponse().getHeaderValue("location")))
                .andReturn().getResponse().getHeaderValue("location");

        assert customerLocation != null;
        mockMvc.perform(MockMvcRequestBuilders.delete(customerLocation)
                        .contentType(MediaType.APPLICATION_JSON)

                ).andExpectAll(MockMvcResultMatchers.status().isNoContent())
                .andDo(result -> log.info("result: {}", result.getResponse().getContentAsString()));
    }
}