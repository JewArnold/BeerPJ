package com.fedorov.beerpj.services;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import com.fedorov.beerpj.repositories.ProducerRepository;
import com.fedorov.beerpj.utils.BeerException;
import com.fedorov.beerpj.utils.ProducerException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    private final BeerService beerService;


    public ProducerService(ProducerRepository producerRepository, @Lazy BeerService beerService) {
        this.producerRepository = producerRepository;
        this.beerService = beerService;
    }


    public List<Producer> findAll(int page, int size) {
        return producerRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Producer findProducerById(int id) {
        try {
            return producerRepository.findById(id).orElseThrow(new Supplier<Throwable>() {
                @Override
                public Throwable get() {
                    return new ProducerException("Производитель не найден");
                }
            });
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    public Producer findProducerByName(String name) {
        try {
            return producerRepository.findProducerByFactoryName(name).orElseThrow(new Supplier<Throwable>() {
                @Override
                public Throwable get() {
                    return new ProducerException("Производитель не найден");
                }
            });
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    public List<Beer> getBeerList(int id, int page, int size) {
        return beerService.findBeerByProducer(id, page, size).getContent();
    }
}