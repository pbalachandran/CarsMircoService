package com.cars.controllers;


import com.cars.requests.CarTrimAddRequest;
import com.cars.responses.ManufacturerTrimsResponse;
import com.cars.responses.TrimResponse;
import com.cars.services.CarTrimsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autos")
public class CarTrimsController {

    private CarTrimsService carTrimsService;

    @Autowired
    public CarTrimsController(CarTrimsService carTrimsService) {
        this.carTrimsService = carTrimsService;
    }

    @GetMapping("/trims/{manufacturerName}/{carName}")
    public ResponseEntity<ManufacturerTrimsResponse> trims(@PathVariable("manufacturerName") String manufacturerName,
                                                           @PathVariable("carName") String carName) {
        return ResponseEntity.ok().body(carTrimsService.trims(manufacturerName, carName));
    }

    @PostMapping("/trim")
    public ResponseEntity<TrimResponse> trim(@RequestBody CarTrimAddRequest carTrimAddRequest) {
        return ResponseEntity.ok().body(carTrimsService.trim(carTrimAddRequest));
    }
}
