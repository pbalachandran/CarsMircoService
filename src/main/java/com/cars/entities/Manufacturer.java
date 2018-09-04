package com.cars.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"name", "countryOfOrigin", "cars"})
@Table(name = "manufacturer")
public class Manufacturer implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "countryoforigin")
    private String countryOfOrigin;

    @JsonBackReference
    @OneToMany(mappedBy = "manufacturer",
            cascade = CascadeType.ALL)
    private List<Car> cars;
}
