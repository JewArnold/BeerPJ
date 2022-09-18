package com.fedorov.beerpj.DTO;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProducerDTO {

    @Size(min = 2, max = 30, message = "Имя производителя должно быть от 2 до 30 символов")
    private String factoryName;

}
