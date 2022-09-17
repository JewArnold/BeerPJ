package com.fedorov.beerpj.services;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import com.fedorov.beerpj.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BeerService {

    private final ProducerService producerService;
    private final BeerRepository beerRepository;

    @Autowired
    public BeerService(ProducerService producerService, BeerRepository beerRepository) {
        this.producerService = producerService;
        this.beerRepository = beerRepository;
    }


    public List<Beer> findAll() {
        return beerRepository.findAll();
    }


    public Optional<Beer> findById(int id) {
        return beerRepository.findById(id);
    }

    @Transactional
    public void save(Beer beer) {

        Producer producer = producerService.findProducerByName(beer.getProducer().getFactoryName()).orElse(null);
        beer.setProducer(producer);
        beerRepository.save(beer);

    }

    public Optional<Beer> findByName(String name) {
        return beerRepository.findBeerByName(name);
    }

    @Transactional
    public void delete(Beer beer) {
        beerRepository.delete(beer);
    }


}
