package com.fedorov.beerpj.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class BeerDTO implements Serializable {

    @NotEmpty
    @Size(min = 2, max = 30, message = "Название должно быть от 2 до 30 символов")
    private String name;

    private ProducerDTO producerDTO;



}
