package com.fedorov.beerpj.validators;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.services.BeerService;
import com.fedorov.beerpj.services.ProducerService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class BeerValidator implements Validator {

    private final BeerService beerService;


    public BeerValidator(BeerService beerService) {
        this.beerService = beerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Beer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Beer beer = (Beer) target;

        if (beerService.findByName(beer.getName()).isPresent()) {
            errors.rejectValue("name", "already exists", "Пиво уже есть в базе");
        }

        if (beer.getProducer() == null) {
            errors.rejectValue("producerName", "not found", "Производитель не найден");
        }

    }
}
