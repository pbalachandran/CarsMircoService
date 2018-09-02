package com.cars.controllers;

import com.cars.CarsMicroServiceApp;
import com.cars.requests.CarAddRequest;
import com.cars.requests.ManufacturerAddRequest;
import com.cars.respositories.ManufacturerRepository;
import com.cars.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Manufacturer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {CarsMicroServiceApp.class})
public class CarsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    public void manufacturers_returnsCarManufacturers() throws Exception {
        mockMvc.perform(get("/api/autos/manufacturers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/manufacturers.json")));
    }

    @Test
    public void cars_returnsCarsByManufacturer() throws Exception {
        mockMvc.perform(get("/api/autos/cars/Ford")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/cars.json")));
    }

    @Test
    public void manufacturer_addsCarManufacturer() throws Exception {
      String jsonPayload =
              new ObjectMapper().writeValueAsString(ManufacturerAddRequest
                      .builder()
                      .name("Nissan")
                      .countryOfOrigin("Japan")
                      .build());

        mockMvc.perform(post("/api/autos/manufacturer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/manufacturer.json")));

        manufacturerRepository.delete("Nissan");
    }

    @Test
    public void car_addsCarForManufacturer() throws Exception {
        manufacturerRepository.save(Manufacturer
                .builder()
                .name("Nissan")
                .countryOfOrigin("Japan")
                .cars(Collections.EMPTY_LIST)
                .build());

        String jsonPayload =
                new ObjectMapper().writeValueAsString(CarAddRequest
                        .builder()
                        .name("Pathfinder")
                        .manufacturerName("Nissan")
                        .numberOfCylinders(6)
                        .build());

        mockMvc.perform(post("/api/autos/car")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/car.json")));

        manufacturerRepository.delete("Nissan");
    }
}