package com.fedorov.beerpj.mappers;

import com.fedorov.beerpj.DTO.BeerDTO;
import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.services.ProducerService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class BeerMapper {/*
    private final ModelMapper mapper;
    private final ProducerService producerService;


    @Autowired
    public BeerMapper(ModelMapper modelMapper, ProducerService producerService) {
        this.mapper = modelMapper;
        this.producerService = producerService;
    }


    public BeerDTO convertToBeerDto(Beer beer) {

//        BeerDTO beerDTO = new BeerDTO();
//        beerDTO.setName(beer.getName());
//        beerDTO.setProducerName(beer.getProducer().getFactoryName());
//
//        return beerDTO;

        *//* return modelMapper.map(beer, BeerDTO.class);*//*

        return Objects.isNull(beer) ? null : mapper.map(beer, BeerDTO.class);
    }

    public Beer convertToBeer(BeerDTO beerDTO) {

//        Beer beer = new Beer();
//
//        beer.setProducer(producerService.findProducerByName(beerDTO.getProducerName()).orElse(null));
//        beer.setName(beerDTO.getName());
//
//        return beer;
        *//*   return modelMapper.map(beerDTO, Beer.class);*//*

        return Objects.isNull(beerDTO) ? null : mapper.map(beerDTO, Beer.class);

    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Beer.class, BeerDTO.class)
                .addMappings(m -> m.skip(BeerDTO::setProducer)).setPostConverter(toBeerDtoConverter());
        mapper.createTypeMap(BeerDTO.class, Beer.class)
                .addMappings(m -> m.skip(Beer::setProducer)).setPostConverter(toBeerConverter());
    }


    public Converter<BeerDTO, Beer> toBeerConverter() {
        return context -> {
            BeerDTO source = context.getSource();
            Beer destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public Converter<Beer, BeerDTO> toBeerDtoConverter() {
        return context -> {
            Beer source = context.getSource();
            BeerDTO destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }


    public void mapSpecificFields(Beer source, BeerDTO destination) {
        destination.setProducer(Objects.isNull(source) || Objects.isNull(source.getName()) ? null : source.getProducer().getFactoryName());
    }

    public void mapSpecificFields(BeerDTO source, Beer destination) {
        destination.setProducer(producerService.findProducerByName(source.getProducer()).orElse(null));
    }
*/
}
