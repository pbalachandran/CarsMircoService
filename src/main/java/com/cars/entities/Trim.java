package com.cars.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trim")
@EqualsAndHashCode(exclude = {"car"})
@ToString(exclude = {"name", "car"})
public class Trim {

    @Id
    @Column(name = "name")
    private String name;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carname", nullable = false)
    private CarTrim car;
}
