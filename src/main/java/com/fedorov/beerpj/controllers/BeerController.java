package com.fedorov.beerpj.controllers;


import com.fedorov.beerpj.DTO.BeerDTO;
import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.services.BeerService;
import com.fedorov.beerpj.services.ProducerService;
import com.fedorov.beerpj.utils.BeerErrorResponse;
import com.fedorov.beerpj.utils.BeerException;
import com.fedorov.beerpj.validators.BeerValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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


    //учесть пагинацию и сортировку по типу, имени, дате добавления, количеству
    @GetMapping()
    public List<BeerDTO> getBeerList() {
        return beerService.findAll().stream().map(this::convertToBeerDto).collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<HttpStatus> getBeerById(@PathVariable int id) {
        /*
        Реализовать
        */
        return ResponseEntity.ok(HttpStatus.OK);
    }


    //Если пиво есть в БД - обновляем количество
    // если нет - добавляем пиво в бд
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addBeer(@RequestBody @Valid BeerDTO beerDTO,
                                              BindingResult bindingResult) {


        Beer beer = convertToBeer(beerDTO);

        beerValidator.validate(beer, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error :
                    errors) {

                errorMessage
                        .append(error.getField())
                        .append("-")
                        .append(error.getDefaultMessage())
                        .append(";  ");
            }
            throw new BeerException(errorMessage.toString());


        }

        beerService.save(beer);

        return ResponseEntity.ok(HttpStatus.OK);
    }


    //Если количество в параметре больше - то зануляем
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> send(@PathVariable String id) {
        /*
        Реализовать
        */
        return ResponseEntity.ok(HttpStatus.OK);
    }


    private BeerDTO convertToBeerDto(Beer beer) {

        BeerDTO beerDTO = new BeerDTO();
        beerDTO.setName(beer.getName());
        beerDTO.setProducerName(beer.getProducer().getFactoryName());

        return beerDTO;

//        return mapper.map(beer, BeerDTO.class);
    }

    private Beer convertToBeer(BeerDTO beerDTO) {

        Beer beer = new Beer();

        beer.setProducer(producerService.findProducerByName(beerDTO.getProducerName()).orElse(null));
        beer.setName(beerDTO.getName());

        return beer;
//        return mapper.map(beerDTO, Beer.class);

    }

    @ExceptionHandler
    public ResponseEntity<BeerErrorResponse> exceptionHandler(BeerException exception) {

        BeerErrorResponse response = new BeerErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}