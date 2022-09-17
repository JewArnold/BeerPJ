package com.fedorov.beerpj.services;

import com.fedorov.beerpj.entities.Producer;
import com.fedorov.beerpj.repositories.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public Optional<Producer> findProducerByName(String name) {
        return producerRepository.findProducerByFactoryName(name);
    }
}
