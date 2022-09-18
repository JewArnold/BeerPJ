package com.fedorov.beerpj.validators;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.services.BeerService;
import com.fedorov.beerpj.services.ProducerService;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class BeerValidator extends Validation implements Validator {

    private final BeerService beerService;
    private final ProducerService producerService;


    public BeerValidator(BeerService beerService, ProducerService producerService) {
        this.beerService = beerService;
        this.producerService = producerService;
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

        checkErrors((BindingResult) errors);

    }
}
