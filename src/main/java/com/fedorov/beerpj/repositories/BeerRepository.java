package com.fedorov.beerpj.repositories;

import com.fedorov.beerpj.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer> {


    Optional<Beer> findBeerByName(String name);
}
