package com.cars.services;

import com.cars.requests.CarAddRequest;
import com.cars.requests.ManufacturerAddRequest;
import com.cars.responses.CarResponse;
import com.cars.responses.ManufacturerResponse;
import com.cars.respositories.ManufacturerRepository;
import com.cars.entities.Car;
import com.cars.entities.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarsService {

    private ManufacturerRepository manufacturerRepository;

    @Autowired
    public CarsService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<ManufacturerResponse> manufacturers() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        return manufacturers.stream().map(manufacturer -> ManufacturerResponse
                .builder()
                .name(manufacturer.getName())
                .countryOfOrigin(manufacturer.getCountryOfOrigin())
                .build())
                .collect(Collectors.toList());
    }

    public List<CarResponse> carsByManufacturer(String manufacturer) {
        Manufacturer retrievedManufacturer = manufacturerRepository.findByName(manufacturer);

        return retrievedManufacturer.getCars().stream().map(car -> CarResponse
                .builder()
                .name(car.getName())
                .numberOfCylinders(car.getNumberOfCylinders())
                .manufacturerName(car.getManufacturer().getName())
                .build())
                .collect(Collectors.toList());
    }

    public ManufacturerResponse addManufacturer(ManufacturerAddRequest manufacturerAddRequest) {
        Manufacturer savedEntity = manufacturerRepository.save(Manufacturer
                .builder()
                .name(manufacturerAddRequest.getName())
                .countryOfOrigin(manufacturerAddRequest.getCountryOfOrigin())
                .cars(Collections.EMPTY_LIST)
                .build());
        return ManufacturerResponse
                .builder()
                .name(savedEntity.getName())
                .countryOfOrigin(savedEntity.getCountryOfOrigin())
                .build();
    }

    public CarResponse addCar(CarAddRequest carAddRequest) {
        Manufacturer manufacturer =
                manufacturerRepository.findOne(carAddRequest.getManufacturerName());

        Car car = Car
                .builder()
                .name(carAddRequest.getName())
                .numberOfCylinders(carAddRequest.getNumberOfCylinders())
                .manufacturer(manufacturer)
                .build();

        List<Car> existingCars = new ArrayList<>(manufacturer.getCars());
        existingCars.add(car);
        manufacturer.setCars(existingCars);

        Manufacturer updatedManufacturer = manufacturerRepository.save(manufacturer);

        Car updatedCar = updatedManufacturer
                .getCars()
                .stream()
                .filter(carEntity -> carAddRequest.getName().equals(carEntity.getName()))
                .findFirst()
                .orElse(null);


        CarResponse carResponse = CarResponse
                .builder()
                .name(updatedCar.getName())
                .numberOfCylinders(updatedCar.getNumberOfCylinders())
                .manufacturerName(updatedManufacturer.getName())
                .build();

        return carResponse;
    }
}
