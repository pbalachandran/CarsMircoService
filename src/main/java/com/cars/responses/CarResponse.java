package com.cars.responses;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    String name;
    int numberOfCylinders;
    String manufacturerName;
}
