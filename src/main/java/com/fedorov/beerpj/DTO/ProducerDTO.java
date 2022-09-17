package com.fedorov.beerpj.DTO;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@ToString(exclude = "beerList")
public class ProducerDTO {

    private String factoryName;

    private List<Beer> beerList;
}
