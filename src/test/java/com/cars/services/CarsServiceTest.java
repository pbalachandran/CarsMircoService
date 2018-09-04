package com.cars.services;

import com.cars.requests.CarAddRequest;
import com.cars.requests.ManufacturerAddRequest;
import com.cars.responses.CarResponse;
import com.cars.responses.ManufacturerResponse;
import com.cars.respositories.ManufacturerRepository;
import com.cars.entities.Car;
import com.cars.entities.Manufacturer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarsServiceTest {

    private CarsService subject;

    @Mock
    private ManufacturerRepository mockManufacturerRepository;


    @Before
    public void setUp() throws Exception {
        subject = new CarsService(mockManufacturerRepository);
    }

    @Test
    public void manufacturers_returnsAllManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();

        Manufacturer ford = Manufacturer
                .builder()
                .name("Ford")
                .countryOfOrigin("USA")
                .build();
        List<Car> fordCars = Collections.singletonList(Car
                .builder()
                .name("Focus")
                .numberOfCylinders(4)
                .manufacturer(ford)
                .build());
        ford.setCars(fordCars);

        Manufacturer honda = Manufacturer
                .builder()
                .name("Honda")
                .countryOfOrigin("Japan")
                .build();
        List<Car> hondaCars = Collections.singletonList(Car
                .builder()
                .name("Pilot")
                .numberOfCylinders(6)
                .manufacturer(honda)
                .build());
        honda.setCars(hondaCars);

        Manufacturer toyota = Manufacturer
                .builder()
                .name("Toyota")
                .countryOfOrigin("Japan")
                .build();
        List<Car> toyotaCars = Collections.singletonList(Car
                .builder()
                .name("Camry")
                .numberOfCylinders(4)
                .manufacturer(toyota)
                .build());
        toyota.setCars(toyotaCars);

        manufacturers.add(ford);
        manufacturers.add(honda);
        manufacturers.add(toyota);

        when(mockManufacturerRepository.findAll()).thenReturn(manufacturers);

        List<ManufacturerResponse> actualResponses = subject.manufacturers();

        List<ManufacturerResponse> expectedResponses = new ArrayList();
        expectedResponses.add(ManufacturerResponse
                .builder()
                .name("Ford")
                .countryOfOrigin("USA")
                .build());

        expectedResponses.add(ManufacturerResponse
                .builder()
                .name("Honda")
                .countryOfOrigin("Japan")
                .build());

        expectedResponses.add(ManufacturerResponse
                .builder()
                .name("Toyota")
                .countryOfOrigin("Japan")
                .build());

        assertEquals(expectedResponses, actualResponses);
    }

    @Test
    public void carsByManufacturer_returnsAllCarsForManufacturer() {
        Manufacturer ford = Manufacturer
                .builder()
                .name("Ford")
                .countryOfOrigin("USA")
                .build();
        List<Car> cars = new ArrayList();
        cars.add(Car
                .builder()
                .name("Focus")
                .numberOfCylinders(4)
                .manufacturer(ford)
                .build());

        cars.add(Car
                .builder()
                .name("Explorer")
                .numberOfCylinders(6)
                .manufacturer(ford)
                .build());
        ford.setCars(cars);

        when(mockManufacturerRepository.findByName("Ford")).thenReturn(Manufacturer
                .builder()
                .name("Ford")
                .countryOfOrigin("USA")
                .cars(cars)
                .build());

        List<CarResponse> actualResponses = subject.carsByManufacturer("Ford");

        List<CarResponse> expectedResponses = new ArrayList<>();
        expectedResponses.add(CarResponse
                .builder()
                .name("Focus")
                .numberOfCylinders(4)
                .manufacturerName("Ford")
                .build());

        expectedResponses.add(CarResponse
                .builder()
                .name("Explorer")
                .numberOfCylinders(6)
                .manufacturerName("Ford")
                .build());

        assertEquals(expectedResponses, actualResponses);
    }

    @Test
    public void addManufacturer_addsCarManufacturer() {
        Manufacturer manufacturer = Manufacturer
                .builder()
                .name("Nissan")
                .countryOfOrigin("Japan")
                .cars(Collections.EMPTY_LIST)
                .build();
        when(mockManufacturerRepository.save(manufacturer)).thenReturn(manufacturer);

        ManufacturerResponse actualResponse =
                subject.addManufacturer(ManufacturerAddRequest
                .builder()
                .name("Nissan")
                .countryOfOrigin("Japan")
                .build());

        ManufacturerResponse expectedResponse = ManufacturerResponse
                .builder()
                .name("Nissan")
                .countryOfOrigin("Japan")
                .build();

        verify(mockManufacturerRepository).save(manufacturer);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void addCar_addsCarForManufacturer() {
        Manufacturer nissan = Manufacturer
                .builder()
                .name("Nissan")
                .countryOfOrigin("Japan")
                .build();

        Car pathfinder = Car
                .builder()
                .name("Pathfinder")
                .numberOfCylinders(6)
                .manufacturer(nissan)
                .build();
        nissan.setCars(Collections.singletonList(pathfinder));

        when(mockManufacturerRepository.findOne("Nissan")).thenReturn(nissan);
        when(mockManufacturerRepository.save(nissan)).thenReturn(nissan);

        CarResponse actualResponse =
                subject.addCar(CarAddRequest
                        .builder()
                        .name("Maxima")
                        .numberOfCylinders(4)
                        .manufacturerName("Nissan")
                        .build());

        CarResponse maximaResponse = CarResponse
                .builder()
                .name("Maxima")
                .numberOfCylinders(4)
                .manufacturerName("Nissan")
                .build();

        verify(mockManufacturerRepository).save(nissan);
        assertEquals(maximaResponse, actualResponse);
    }
}