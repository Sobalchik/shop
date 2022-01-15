package com.market.shop.db.goods;

import com.market.shop.exeption.CakeNotFoundException;
import com.market.shop.rest.dto.cake.Cake;
import com.market.shop.rest.dto.cake.Cakes;

public interface CakeService {
    Cakes getCakes();
    void addCake(Cake cake);
    void deleteCake (Long id) throws CakeNotFoundException;
}
