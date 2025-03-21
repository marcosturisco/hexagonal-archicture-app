package com.turisco.learning.adapter.outbound.persistence.repository;

import com.turisco.learning.adapter.outbound.persistence.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
