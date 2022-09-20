package com.fedorov.beerpj.repositories;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer> {


    Optional<Beer> findBeerByName(String name);

//    List<Beer> findBeerByProducer(Producer producer);

    Page<Beer> findBeerByProducer(Producer producer, Pageable pageable);
}
