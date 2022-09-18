package com.fedorov.beerpj.validators;

import com.fedorov.beerpj.utils.BeerException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class Validation {
    public static void checkErrors(BindingResult bindingResult){
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
    }
}
