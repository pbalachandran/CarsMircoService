package com.cars.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
@EqualsAndHashCode(exclude = {"manufacturer", "trims"})
@ToString(exclude = {"name", "numberOfCylinders", "manufacturer", "trims"})
public class CarTrim implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "numberofcylinders")
    private Integer numberOfCylinders;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturername", nullable = false)
    private ManufacturerCarTrim manufacturer;

    @JsonBackReference
    @OneToMany(mappedBy = "car",
            cascade = CascadeType.ALL)
    private Set<Trim> trims;
}
