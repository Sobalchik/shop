package com.market.shop.rest.controller;

import com.market.shop.db.goods.CakeService;
import com.market.shop.db.orders.OrderService;
import com.market.shop.db.users.UserService;
import com.market.shop.exeption.UserExistException;
import com.market.shop.rest.dto.cake.Cake;
import com.market.shop.rest.dto.cake.Cakes;
import com.market.shop.rest.dto.order.Order;
import com.market.shop.rest.dto.order.Orders;
import com.market.shop.rest.dto.user.User;
import com.market.shop.rest.dto.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class UserController {
    private final CakeService cakesService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserController(CakeService cakeService, UserService userService, OrderService orderService) {
        this.cakesService =cakeService;
        this.userService=userService;
        this.orderService=orderService;
    }

    @GetMapping(value="cakes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cakes cakes(){
        return cakesService.getCakes();
    }

    @GetMapping(value="users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Users users(){
        return userService.getUsers();
    }

    @GetMapping(value="order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Orders orders(@PathVariable String number){
        return orderService.getOrder(number);
    }

    @GetMapping(value="cake/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cake cake(@PathVariable Long id){
        return cakesService.getCake(id);
    }

    @ResponseStatus(code=HttpStatus.CREATED)
    @PostMapping(path = "addOrder", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody @Valid Order newOrder){
        try {
            userService.addUser(newOrder.getUser());
        }
        catch (UserExistException ignored){
        }
        orderService.addOrder(newOrder);
    }

    @ResponseStatus(code= HttpStatus.CREATED)
    @PostMapping(path = "addUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody @Valid User newUser){
        try {
            userService.addUser(newUser);
        }
        catch (UserExistException ignored){
        }
    }
}
