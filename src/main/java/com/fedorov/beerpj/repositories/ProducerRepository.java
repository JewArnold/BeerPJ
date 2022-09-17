package com.fedorov.beerpj.repositories;

import com.fedorov.beerpj.entities.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Integer> {

    Optional<Producer> findProducerByFactoryName(String name);
}
