package com.cars.responses;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerResponse {
    String name;
    String countryOfOrigin;
}
