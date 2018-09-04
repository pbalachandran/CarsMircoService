package com.cars.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
@ToString(exclude = {"name", "numberOfCylinders", "manufacturer"})
public class Car implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "numberofcylinders")
    private Integer numberOfCylinders;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturername", nullable = false)
    private Manufacturer manufacturer;
}
