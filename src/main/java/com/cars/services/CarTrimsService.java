package com.cars.services;

import com.cars.requests.CarTrimAddRequest;
import com.cars.responses.CarTrimResponse;
import com.cars.responses.ManufacturerTrimsResponse;
import com.cars.responses.TrimResponse;
import com.cars.respositories.ManufacturerCarTrimsRepository;
import com.cars.entities.CarTrim;
import com.cars.entities.ManufacturerCarTrim;
import com.cars.entities.Trim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarTrimsService {

    private ManufacturerCarTrimsRepository manufacturerCarTrimsRepository;

    @Autowired
    public CarTrimsService(ManufacturerCarTrimsRepository manufacturerCarTrimsRepository) {
        this.manufacturerCarTrimsRepository = manufacturerCarTrimsRepository;
    }

    public ManufacturerTrimsResponse trims(String manufacturer, String car) {
        ManufacturerCarTrim retrievedManufacturer =
                manufacturerCarTrimsRepository.findTrims(manufacturer, car);

        CarTrim carTrim = retrievedManufacturer.getCars().iterator().next();
        return ManufacturerTrimsResponse
                .builder()
                .manufacturerName(retrievedManufacturer.getName())
                .countryOfOrigin(retrievedManufacturer.getCountryOfOrigin())
                .car(CarTrimResponse
                        .builder()
                        .carName(carTrim.getName())
                        .numberOfCylinders(carTrim.getNumberOfCylinders())
                        .trims(carTrim.getTrims()
                                .stream()
                                .map(trim -> TrimResponse
                                        .builder()
                                        .trimName(trim.getName())
                                        .carName(carTrim.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .build();
    }

    public TrimResponse trim(CarTrimAddRequest carTrimAddRequest) {
        ManufacturerCarTrim manufacturerCarTrim =
                manufacturerCarTrimsRepository.findOne(carTrimAddRequest.getManufacturerName());

        Iterator<CarTrim> manufacturerCarTrimIterator = manufacturerCarTrim.getCars().iterator();
        while (manufacturerCarTrimIterator.hasNext()) {
            CarTrim carTrim = manufacturerCarTrimIterator.next();
            if (carTrim.getName().equals(carTrimAddRequest.getCarName())) {
                Set<Trim> trims = carTrim.getTrims();
                trims.add(Trim
                        .builder()
                        .name(carTrimAddRequest.getTrimName())
                        .car(carTrim)
                        .build());
                break;
            }
        }
        ManufacturerCarTrim updatedManufacturerCarTrim =
                manufacturerCarTrimsRepository.save(manufacturerCarTrim);

        TrimResponse trimResponse = null;

        manufacturerCarTrimIterator = updatedManufacturerCarTrim.getCars().iterator();
        while (manufacturerCarTrimIterator.hasNext()) {
            CarTrim carTrim = manufacturerCarTrimIterator.next();
            if (carTrim.getName().equals(carTrimAddRequest.getCarName())) {
                Set<Trim> trims = carTrim.getTrims();
                Iterator<Trim> trimIterator = trims.iterator();
                while (trimIterator.hasNext()) {
                    Trim trim = trimIterator.next();
                    if (trim.getName().equals(carTrimAddRequest.getTrimName())) {
                        trimResponse = TrimResponse
                                .builder()
                                .trimName(trim.getName())
                                .carName(carTrim.getName())
                                .build();
                        break;
                    }
                }
                break;
            }
        }
        return trimResponse;
    }
}
