package com.cars.services;

import com.cars.requests.CarTrimAddRequest;
import com.cars.responses.CarTrimResponse;
import com.cars.responses.ManufacturerTrimsResponse;
import com.cars.responses.TrimResponse;
import com.cars.respositories.ManufacturerCarTrimsRepository;
import com.google.common.collect.Sets;
import com.cars.entities.CarTrim;
import com.cars.entities.ManufacturerCarTrim;
import com.cars.entities.Trim;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarTrimsServiceTest {

    private CarTrimsService subject;

    @Mock
    private ManufacturerCarTrimsRepository mockManufacturerCarTrimsRepository;

    @Before
    public void setUp() throws Exception {
        subject = new CarTrimsService(mockManufacturerCarTrimsRepository);
    }

    @Test
    public void carTrimsByManufacturer_returnsCarsAndTrims() {

        ManufacturerCarTrim honda = ManufacturerCarTrim
                .builder()
                .name("Honda")
                .countryOfOrigin("Japan")
                .build();

        CarTrim civic = CarTrim
                .builder()
                .name("Civic")
                .numberOfCylinders(4)
                .manufacturer(honda)
                .build();

        Set<CarTrim> hondaCarTrims = Sets.newHashSet();
        hondaCarTrims.add(civic);
        honda.setCars(hondaCarTrims);

        Trim lx = Trim
                .builder()
                .name("EX")
                .car(civic)
                .build();

        Trim ex = Trim
                .builder()
                .name("LX")
                .car(civic)
                .build();

        Set<Trim> civicTrims = new HashSet<>();
        civicTrims.add(lx);
        civicTrims.add(ex);
        civic.setTrims(civicTrims);

        when(mockManufacturerCarTrimsRepository.findTrims("Honda", "Civic")).thenReturn(honda);

        ManufacturerTrimsResponse actualResponse = subject.trims("Honda", "Civic");

        List<TrimResponse> trimResponses = new ArrayList<>();
        TrimResponse trimResponse1 = TrimResponse
                .builder()
                .trimName("EX")
                .carName("Civic")
                .build();

        TrimResponse trimResponse2 = TrimResponse
                .builder()
                .trimName("LX")
                .carName("Civic")
                .build();

        trimResponses.add(trimResponse1);
        trimResponses.add(trimResponse2);

        ManufacturerTrimsResponse expectedResponse = ManufacturerTrimsResponse
                .builder()
                .manufacturerName("Honda")
                .countryOfOrigin("Japan")
                .car(CarTrimResponse
                        .builder()
                        .carName("Civic")
                        .numberOfCylinders(4)
                        .trims(trimResponses)
                        .build())
                .build();

        assertEquals(expectedResponse.getManufacturerName(), actualResponse.getManufacturerName());
        assertEquals(expectedResponse.getCountryOfOrigin(), actualResponse.getCountryOfOrigin());

        assertEquals(expectedResponse.getCar().getCarName(), actualResponse.getCar().getCarName());
        assertEquals(expectedResponse.getCar().getNumberOfCylinders(), actualResponse.getCar().getNumberOfCylinders());

        assertTrue(trimResponses.contains(actualResponse.getCar().getTrims().get(0)));
        assertTrue(trimResponses.contains(actualResponse.getCar().getTrims().get(1)));
    }

    @Test
    public void trim_AddsTrimToManufacturerCarCombination() throws  Exception {

        ManufacturerCarTrim honda = ManufacturerCarTrim
                .builder()
                .name("Honda")
                .countryOfOrigin("Japan")
                .build();

        CarTrim accord = CarTrim
                .builder()
                .name("Accord")
                .numberOfCylinders(4)
                .manufacturer(honda)
                .build();

        CarTrim pilot = CarTrim
                .builder()
                .name("Pilot")
                .numberOfCylinders(6)
                .manufacturer(honda)
                .build();

        Set<CarTrim> hondaCars = new HashSet<>();
        hondaCars.add(accord);
        hondaCars.add(pilot);
        honda.setCars(hondaCars);

        Trim accordTrim1 = Trim
                .builder()
                .name("Accord-Trim1")
                .car(accord)
                .build();

        Trim accordTrim2 = Trim
                .builder()
                .name("Accord-Trim2")
                .car(accord)
                .build();

        Trim accordLimitedEditionTrim = Trim
                .builder()
                .name("LimitedEdition")
                .car(accord)
                .build();

        Set<Trim> accordTrims = new HashSet<>();
        accordTrims.add(accordTrim1);
        accordTrims.add(accordTrim2);
        accord.setTrims(accordTrims);

        Trim pilotTrim1 = Trim
                .builder()
                .name("Pilot-Trim1")
                .car(pilot)
                .build();

        Trim pilotTrim2 = Trim
                .builder()
                .name("Pilot-Trim2")
                .car(pilot)
                .build();

        Set<Trim> pilotTrims = new HashSet<>();
        pilotTrims.add(pilotTrim1);
        pilotTrims.add(pilotTrim2);
        pilot.setTrims(pilotTrims);

        when(mockManufacturerCarTrimsRepository.findOne("Honda")).thenReturn(honda);

        accord.getTrims().add(accordLimitedEditionTrim);
        when(mockManufacturerCarTrimsRepository.save(honda)).thenReturn(honda);

        CarTrimAddRequest carTrimAddRequest = CarTrimAddRequest
                .builder()
                .carName("Accord")
                .manufacturerName("Honda")
                .trimName("LimitedEdition")
                .build();

        TrimResponse actualResponse = subject.trim(carTrimAddRequest);

        TrimResponse expectedResponse = TrimResponse
                .builder()
                .trimName("LimitedEdition")
                .carName("Accord")
                .build();

        verify(mockManufacturerCarTrimsRepository).save(honda);
        assertEquals(expectedResponse, actualResponse);
    }
}