package com.fedorov.beerpj.services;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import com.fedorov.beerpj.repositories.BeerRepository;
import com.fedorov.beerpj.utils.BeerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class BeerService {

    private final ProducerService producerService;

    private final BeerRepository beerRepository;

    @Autowired
    public BeerService(ProducerService producerService, BeerRepository beerRepository) {
        this.producerService = producerService;
        this.beerRepository = beerRepository;
    }


    public List<Beer> findAll(int page, int size) {
        return beerRepository.findAll(PageRequest.of(page, size)).getContent();
    }


    public Beer findById(int id) {
        Beer beer = null;
        try {
            beer = beerRepository.findById(id).orElseThrow(new Supplier<Throwable>() {
                @Override
                public Throwable get() {
                    return new BeerException("Пиво не найдено");
                }
            });
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }


        return beer;
    }

    @Transactional
    public void save(Beer beer) {
        Producer producer = producerService.findProducerByName(beer.getProducer().getFactoryName());
        beer.setProducer(producer);
        beerRepository.save(beer);
    }

    @Transactional
    public void delete(int id) {

        Beer checkBeer = findById(id); // здесь заложена проверка на наличие пива с таким айдишником

        beerRepository.deleteById(id);
    }

    public Optional<Beer> findByName(String name) {
        return beerRepository.findBeerByName(name);
    }


    public List<Beer> findBeerByProducer(int producerId) {

        Producer producer = producerService.findProducerById(producerId);

        return beerRepository.findBeerByProducer(producer);

    }

}
