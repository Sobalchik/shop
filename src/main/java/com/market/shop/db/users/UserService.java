package com.market.shop.db.users;

import com.market.shop.exeption.UserExistException;
import com.market.shop.rest.dto.user.User;
import com.market.shop.rest.dto.user.Users;

public interface UserService {
    void addUser(User user) throws UserExistException;
    void deleteUser(String number) throws UserExistException;
    Users getUsers();
}
