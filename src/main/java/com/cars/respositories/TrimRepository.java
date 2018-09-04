package com.cars.respositories;

import com.cars.entities.Trim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrimRepository extends JpaRepository<Trim, String> {
}
