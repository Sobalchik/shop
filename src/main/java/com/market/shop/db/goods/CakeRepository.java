package com.market.shop.db.goods;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepository extends JpaRepository<CakeEntity,Long> {
    CakeEntity findCakeEntityById(Long id);
}
