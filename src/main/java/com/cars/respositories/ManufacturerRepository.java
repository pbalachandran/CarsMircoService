package com.cars.respositories;

import entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, String> {

    @Query("SELECT m FROM Manufacturer m JOIN FETCH m.cars WHERE m.name = ?1")
    Manufacturer findByName(String name);
}
