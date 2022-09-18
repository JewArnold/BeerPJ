package com.fedorov.beerpj.controllers;

import com.fedorov.beerpj.DTO.BeerDTO;
import com.fedorov.beerpj.DTO.ProducerDTO;
import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import com.fedorov.beerpj.services.BeerService;
import com.fedorov.beerpj.services.ProducerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    private final ProducerService producerService;
    private final ModelMapper mapper;

    @Autowired
    public ProducerController(ProducerService producerService, ModelMapper mapper) {
        this.producerService = producerService;
        this.mapper = mapper;
    }

    @GetMapping()
    public List<ProducerDTO> getAllProducers(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        return producerService.findAll(page, pageSize).stream().map(this::convertToProducerDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public List<BeerDTO> getBeerList(@PathVariable("id") int id) {
        return producerService.getBeerList(id).stream()
                .map(this::convertToBeerDto)
                .collect(Collectors.toList());
    }


    private ProducerDTO convertToProducerDto(Producer producer) {
        return mapper.map(producer, ProducerDTO.class);
    }

    private BeerDTO convertToBeerDto(Beer beer) {
        BeerDTO dto = mapper.map(beer, BeerDTO.class);
        dto.setProducerDTO(mapper.map(beer.getProducer(), ProducerDTO.class));
        return dto;
    }
}
