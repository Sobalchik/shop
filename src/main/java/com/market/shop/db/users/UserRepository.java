package com.market.shop.db.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByNumber(String number);
    UserEntity findUserEntityByNumber(String Number);
}