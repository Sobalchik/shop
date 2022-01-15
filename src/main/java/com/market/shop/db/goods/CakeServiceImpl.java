package com.market.shop.db.goods;

import com.market.shop.exeption.CakeNotFoundException;
import com.market.shop.rest.dto.cake.Cake;
import com.market.shop.rest.dto.cake.Cakes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CakeServiceImpl implements CakeService {
    private final CakeRepository cakeRepository;

    @Autowired
    public CakeServiceImpl(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    @Override
    public Cakes getCakes() {
        List<CakeEntity> cakeEntityList = cakeRepository.findAll();
        List<Cake> cakeList = cakeEntityList.stream().map(c -> {
            Cake cake = new Cake();
            cake.setId(c.getId());
            cake.setName(c.getName());
            cake.setImage(c.getImage());
            cake.setPrice(c.getPrice());
            cake.setWeight(c.getWeight());
            cake.setCalories(c.getCalories());
            return cake;
        }).collect(Collectors.toList());
        Cakes cakes = new Cakes();
        cakes.setCakeList(cakeList);
        return cakes;
    }

    @Override
    public void addCake(Cake cake) {
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setName(cake.getName());
        cakeEntity.setImage(cake.getImage());
        cakeEntity.setPrice(cake.getPrice());
        cakeEntity.setWeight(cake.getWeight());
        cakeEntity.setCalories(cake.getCalories());
        cakeRepository.saveAndFlush(cakeEntity);
    }

    @Override
    public void deleteCake(Long id) throws CakeNotFoundException {
        if(!cakeRepository.existsById(id)) {
            throw new CakeNotFoundException("There is no cake with id = "+id);
        }
        else cakeRepository.deleteById(id);
    }
}
