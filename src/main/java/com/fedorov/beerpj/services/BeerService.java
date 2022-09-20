package com.fedorov.beerpj.services;

import com.fedorov.beerpj.entities.Beer;
import com.fedorov.beerpj.entities.Producer;
import com.fedorov.beerpj.repositories.BeerRepository;
import com.fedorov.beerpj.utils.BeerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class BeerService {

    private final ProducerService producerService;

    private final BeerRepository beerRepository;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BeerService(ProducerService producerService, BeerRepository beerRepository, JdbcTemplate jdbcTemplate) {
        this.producerService = producerService;
        this.beerRepository = beerRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Beer> findAll(int page, int size) {
        return beerRepository.findAll(PageRequest.of(page, size)).getContent();
    }


    public Beer findById(int id) {
        Beer beer = null;
        try {
            beer = beerRepository.findById(id).orElseThrow(new Supplier<Throwable>() {
                @Override
                public Throwable get() {
                    return new BeerException("Пиво не найдено");
                }
            });
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }


        return beer;
    }

    @Transactional
    public void save(Beer beer) {

        //Достаем референс из базы данных. Сравниваем по нему имя производителя и проверяем, представлено ли ранее
        Optional<Beer> dataBaseBeer = findByName(beer.getName());

        Producer producer = producerService.findProducerByName(beer.getProducer().getFactoryName());
        beer.setProducer(producer);

        //Если пиво уже представлено в БД, суммируем объем поступившего пива с объемом пива в наличии
        if (dataBaseBeer.isPresent()) {

            if (!dataBaseBeer.get().getProducer().getFactoryName() //проверяем, равны ли имена производителя
                    .equals(beer.getProducer().getFactoryName())) {
                throw new BeerException("Данное пиво представлено у другого производителя");
            }

            if (dataBaseBeer.get().getAmount() != null) { //если пиво уже представлено и у него заявлено количество
                beer.setAmount(dataBaseBeer.get().getAmount() + beer.getAmount());
                jdbcTemplate.update("UPDATE beer SET amount = ? WHERE name = ?", beer.getAmount(), beer.getName());
                return;
            }
        }


        beerRepository.save(beer);
    }

    @Transactional
    public void remove(int id, int forRemove) {

        Beer checkBeer = findById(id); // здесь заложена проверка на наличие пива с таким айдишником

        checkBeer.setAmount(Math.max((checkBeer.getAmount() - forRemove), 0));

        jdbcTemplate.update("UPDATE beer SET amount = ? WHERE name = ?", checkBeer.getAmount(), checkBeer.getName());

    }

    public Optional<Beer> findByName(String name) {
        return beerRepository.findBeerByName(name);
    }


    public /*List<Beer>*/ Page<Beer> findBeerByProducer(int producerId, int page, int size) {

        Producer producer = producerService.findProducerById(producerId);

        return beerRepository.findBeerByProducer(producer, PageRequest.of(page, size));

    }

}
