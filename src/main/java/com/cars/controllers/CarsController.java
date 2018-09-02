package com.cars.controllers;


import com.cars.requests.CarAddRequest;
import com.cars.requests.ManufacturerAddRequest;
import com.cars.responses.CarResponse;
import com.cars.responses.ManufacturerResponse;
import com.cars.services.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autos")
public class CarsController {

    private CarsService carsService;

    @Autowired
    public CarsController(CarsService carsService) {
        this.carsService = carsService;
    }

    @GetMapping("/manufacturers")
    public ResponseEntity<List<ManufacturerResponse>> manufacturers() {
        return ResponseEntity.ok().body(carsService.manufacturers());
    }

    @GetMapping("/cars/{manufacturer}")
    public ResponseEntity<List<CarResponse>> cars(@PathVariable("manufacturer") String manufacturer) {
        return ResponseEntity.ok().body(carsService.carsByManufacturer(manufacturer));
    }

    @PostMapping("/manufacturer")
    public ResponseEntity<ManufacturerResponse>
                manufacturer(@RequestBody ManufacturerAddRequest manufacturerAddRequest) {
        return ResponseEntity.ok().body(carsService.addManufacturer(manufacturerAddRequest));
    }

    @PostMapping("/car")
    public ResponseEntity<CarResponse> car(@RequestBody CarAddRequest carAddRequest) {
        return ResponseEntity.ok().body(carsService.addCar(carAddRequest));
    }
}
