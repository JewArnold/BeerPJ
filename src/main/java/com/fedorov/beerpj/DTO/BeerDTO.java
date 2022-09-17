package com.fedorov.beerpj.DTO;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ToString(exclude = "producer")
public class BeerDTO implements Serializable {

    @NotEmpty
    @Size(min = 2, max = 30, message = "Название должно быть от 2 до 30 символов")
    private String name;

//    @NotEmpty
//    @Size(min = 2, max = 30, message = "Имя производителя должно быть от 2 до 30 символов")
//    private String producerName;

    private String producerName;

}
