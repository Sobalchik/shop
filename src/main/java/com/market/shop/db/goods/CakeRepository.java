package com.market.shop.db.goods;

import com.market.shop.db.goods.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepository extends JpaRepository<CakeEntity,Long> {
   // boolean existsByName(String name);
    CakeEntity findCakeEntityById(Long id);
}
