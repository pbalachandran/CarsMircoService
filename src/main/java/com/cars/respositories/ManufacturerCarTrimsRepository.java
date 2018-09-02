package com.cars.respositories;

import entities.ManufacturerCarTrim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerCarTrimsRepository extends JpaRepository<ManufacturerCarTrim, String> {

    @Query("SELECT m FROM ManufacturerCarTrim m " +
            "JOIN FETCH m.cars c " +
            "JOIN FETCH c.trims " +
            "WHERE m.name = ?1 " +
            "AND c.name = ?2")
    ManufacturerCarTrim findTrims(String manufacturerName, String carName);
}
