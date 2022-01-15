package com.market.shop.rest.controller;

import com.market.shop.db.goods.CakeService;
import com.market.shop.db.orders.OrderService;
import com.market.shop.db.payment.PaymentService;
import com.market.shop.db.users.UserService;
import com.market.shop.exeption.CakeNotFoundException;
import com.market.shop.exeption.OrderNotFoundException;
import com.market.shop.exeption.PaymentNotFoundException;
import com.market.shop.exeption.UserExistException;

import com.market.shop.rest.dto.cake.Cake;
import com.market.shop.rest.dto.order.Order;
import com.market.shop.rest.dto.order.Payment;
import com.market.shop.rest.dto.order.Purchase;
import com.market.shop.rest.dto.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final OrderService orderService;
    private final CakeService cakeService;
    private final UserService userService;
    private final PaymentService paymentService;

    @Autowired
    public AdminController(OrderService orderService, CakeService cakeService, UserService userService, PaymentService paymentService) {
        this.orderService = orderService;
        this.cakeService = cakeService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @GetMapping(value="home")
    public String home(){
        return "home";
    }

    @GetMapping(value="cakes")
    public String cakes(org.springframework.ui.Model model){
        model.addAttribute("cakes",  cakeService.getCakes().getCakeList());
        model.addAttribute("cake", new Cake());
        return "cakes";
    }

    @GetMapping(value="orders")
    public String orders(Model model){
        model.addAttribute("orders", orderService.getOrders().getOrderList());
        model.addAttribute("users",  userService.getUsers().getUserList());
        model.addAttribute("order", new Order());
        model.addAttribute("neworder", new Order());
        model.addAttribute("status", new Order());
        model.addAttribute("user", new User());
        model.addAttribute("purchase", new Purchase());
        model.addAttribute("payment", new Payment());
        return "orders";
    }

    @PostMapping(path = "addOrder")
    public String createOrder(Order newOrder){
        try {
            userService.addUser(newOrder.getUser());
        }
        catch (UserExistException ignored){
        }
        orderService.addOrder(newOrder);
        return "redirect:/admin/orders";
    }

    @PostMapping(value = "addCake")
    public String createCake(Cake newCake){
        cakeService.addCake(newCake);
        return "redirect:/admin/cakes";
    }

    @PostMapping(value="deleteCake")
    public String deleteCake(Cake cake){
        try {
            cakeService.deleteCake(cake.getId());
        }
        catch (CakeNotFoundException ignored) {
        }
        return "redirect:/admin/cakes";
    }

    @PostMapping(value = "deleteUser/{number}")
    public String deleteUser(@PathVariable String number){
        try {
            userService.deleteUser(number);
        }
        catch (UserExistException ignored){
        }
        return "redirect:/admin/orders";
    }

    @PostMapping(value = "changeOrderStatus/{id}")
    public String changeOrderStatus(@PathVariable Long id, Order order){
        try {
            orderService.changeOrderStatus(id, order.getOrderStatus());
        }
        catch (OrderNotFoundException ignored) {
        }
        return "redirect:/admin/orders";
    }

    @PostMapping(value = "changePaymentStatus/{id}")
    public String changePaymentStatus(@PathVariable Long id, Payment payment){
        try {
            paymentService.changePaymentStatus(id, payment.getStatus());
        }
        catch (PaymentNotFoundException ignored){
        }
        return "redirect:/admin/orders";
    }

    @PostMapping(value = "addPurchaseInList/{id}")
    public String addPurchaseInList(@PathVariable Long id, Purchase purchase){
        try {
            orderService.addPurchaseInList(id, purchase);
        }
        catch(OrderNotFoundException ignored){
        }
        return "redirect:/admin/orders";
    }

    @PostMapping(value = "deletePurchaseInList/{id}")
    public String deletePurchaseInList(@PathVariable Long id, Purchase purchase){
        try {
            orderService.deletePurchaseInList(id, purchase);
        }
        catch (OrderNotFoundException ignored){
        }
        return "redirect:/admin/orders";
    }

    @PostMapping(value = "deleteOrder/{id}")
    public String deleteOrder(@PathVariable Long id){
        try {
            orderService.deleteOrder(id);
        }
        catch(OrderNotFoundException ignored){
        }
        return "redirect:/admin/orders";
    }
}