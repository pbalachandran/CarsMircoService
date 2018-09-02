package com.cars.controllers;

import com.cars.CarsMicroServiceApp;
import com.cars.requests.CarTrimAddRequest;
import com.cars.requests.ManufacturerAddRequest;
import com.cars.respositories.ManufacturerCarTrimsRepository;
import com.cars.respositories.TrimRepository;
import com.cars.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {CarsMicroServiceApp.class})
public class CarTrimsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManufacturerCarTrimsRepository manufacturerCarTrimsRepository;

    @Autowired
    private TrimRepository trimRepository;

    @Test
    public void trims_returnsTrimsByManufacturer() throws Exception {
        mockMvc.perform(get("/api/autos/trims/Honda/Accord")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/manufacturer-trims.json")));
    }

    @Test
    public void trim_addsTrimForManufacturerCar() throws Exception {
        String jsonPayload =
                new ObjectMapper().writeValueAsString(CarTrimAddRequest
                        .builder()
                        .carName("Accord")
                        .manufacturerName("Honda")
                        .trimName("LimitedEdition")
                        .build());

        mockMvc.perform(post("/api/autos/trim")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/trim.json")));

        trimRepository.delete("LimitedEdition");
    }
}