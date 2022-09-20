package com.fedorov.beerpj.controllers;


import com.fedorov.beerpj.DTO.BeerDTO;
import com.fedorov.beerpj.DTO.ProducerDTO;
import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import com.fedorov.beerpj.services.BeerService;
import com.fedorov.beerpj.services.ProducerService;
import com.fedorov.beerpj.utils.ErrorResponse;
import com.fedorov.beerpj.validators.BeerValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/beer")
public class BeerController {


    private final BeerService beerService;
    private final BeerValidator beerValidator;
    private final ModelMapper mapper;
    private final ProducerService producerService;


    @Autowired
    public BeerController(BeerService beerService, BeerValidator beerValidator, ModelMapper mapper, ProducerService producerService) {
        this.beerService = beerService;
        this.beerValidator = beerValidator;
        this.mapper = mapper;
        this.producerService = producerService;
    }


    //учесть сортировку по типу, имени, дате добавления, количеству
    @GetMapping()
    public List<BeerDTO> getBeerList(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {


        return beerService.findAll(page, pageSize).stream().map(this::convertToBeerDto).collect(Collectors.toList());

    }


    @GetMapping("/{id}")
    public BeerDTO getBeerById(@PathVariable int id) {

        Beer beer = beerService.findById(id);

        return convertToBeerDto(beer);
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addBeer(@RequestBody @Valid BeerDTO beerDTO,
                                              BindingResult bindingResult) {

        Beer beer = convertToBeer(beerDTO);

        beerService.save(beer);

        return ResponseEntity.ok(HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public int remove(@PathVariable int id,
                      @RequestParam (value = "amount", required = false, defaultValue = "1") int forRemove) {

        beerService.remove(id, forRemove);

        return id;
    }


    private BeerDTO convertToBeerDto(Beer beer) {

        BeerDTO dto = mapper.map(beer, BeerDTO.class);

        dto.setProducerDTO(mapper.map(beer.getProducer(), ProducerDTO.class));

        return dto;
    }

    private Beer convertToBeer(BeerDTO beerDTO) {

        Producer producer = producerService.findProducerByName(beerDTO.getProducerDTO().getFactoryName());
        Beer beer = mapper.map(beerDTO, Beer.class);
        beer.setProducer(producer);

        return beer;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException exception) {

        ErrorResponse response = new ErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
