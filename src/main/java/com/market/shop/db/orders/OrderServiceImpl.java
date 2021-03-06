package com.market.shop.db.orders;

import com.market.shop.db.OrderStatus;
import com.market.shop.db.goods.CakeRepository;
import com.market.shop.db.payment.PaymentEntity;
import com.market.shop.db.purchase.PurchaseEntity;
import com.market.shop.db.purchase.PurchaseRepository;
import com.market.shop.db.users.UserEntity;
import com.market.shop.db.users.UserRepository;
import com.market.shop.exeption.OrderNotFoundException;
import com.market.shop.rest.dto.order.Order;
import com.market.shop.rest.dto.order.Orders;
import com.market.shop.rest.dto.order.Payment;
import com.market.shop.rest.dto.order.Purchase;
import com.market.shop.rest.dto.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CakeRepository cakeRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CakeRepository cakeRepository, PurchaseRepository purchaseRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cakeRepository = cakeRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void addOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setDelivery(order.getDelivery());
        orderEntity.setDeliveryAddress(order.getDeliveryAddress());
        orderEntity.setDeliveryTime(order.getDeliveryTime());
        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setAmount(0.0);
        paymentEntity.setStatus(order.getPayment().getStatus());
        paymentEntity.setOrder(orderEntity);
        orderEntity.setPayment(paymentEntity);

        orderEntity.setOrderStatus(OrderStatus.NEW);
        List<PurchaseEntity> l= new ArrayList<>();
        orderEntity.setPurchases(l);
        orderEntity.setUser(userRepository.findUserEntityByNumber(order.getUser().getNumber()));
        orderRepository.saveAndFlush(orderEntity);
    }


    @Override
    public Orders getOrders() {
        List<OrderEntity> orderEntityList = orderRepository.findAll();
        List<Order> orderList = orderEntityList.stream().map(c -> {
            Order order = new Order();
            order.setId(c.getId());

            UserEntity userEntity = c.getUser();
            User user = new User();
            user.setName(userEntity.getName());
            user.setNumber(userEntity.getNumber());
            order.setUser(user);

            order.setDelivery(c.getDelivery());
            order.setDeliveryAddress(c.getDeliveryAddress());
            order.setDeliveryTime(c.getDeliveryTime());
            order.setOrderStatus(c.getOrderStatus());

            order.setPurchases(c.getPurchases().stream()
                    .map(purchaseEntity -> {
                        Purchase purchase = new Purchase();
                        purchase.setCount(purchaseEntity.getCount());
                        purchase.setCakeId(purchaseEntity.getCake().getId());
                        return purchase;
                    }).collect(Collectors.toList()));

            double amount=0.0;
            for (Purchase el: order.getPurchases()){
                amount+=el.getCount()*cakeRepository.findCakeEntityById(el.getCakeId()).getPrice();
            }

            PaymentEntity paymentEntity=c.getPayment();
            Payment payment = new Payment();
            payment.setId(paymentEntity.getId());
            payment.setStatus(paymentEntity.getStatus());
            payment.setAmount(amount);
            order.setPayment(payment);

            return order;
        }).collect(Collectors.toList());
        Orders orders = new Orders();
        orders.setOrderList(orderList);
        return orders;
    }
    @Override
    public void changeOrderStatus(Long id, OrderStatus orderStatus) throws OrderNotFoundException {
        if(!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("There is no order with"+id);
        }
        else  {
            OrderEntity orderEntity = orderRepository.getById(id);
            orderEntity.setOrderStatus(orderStatus);
            orderRepository.saveAndFlush(orderEntity);
        }
    }

    @Override
    public void addPurchaseInList(Long id, Purchase newpurchase) throws OrderNotFoundException {
        if(!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("There is no order with"+id);
        }
        else  {
            OrderEntity orderEntity = orderRepository.getById(id);
            orderEntity.getPayment().setAmount(orderEntity.getPayment().getAmount()+
                    cakeRepository.findCakeEntityById(newpurchase.getCakeId()).getPrice()*newpurchase.getCount());
            boolean success = false;
            for (PurchaseEntity el: orderEntity.getPurchases()){
                if (el.getCake().getId().equals(newpurchase.getCakeId())) {
                    el.setCount(newpurchase.getCount()+el.getCount());
                    purchaseRepository.saveAndFlush(el);
                    success = true;
                }
            }

            if(!success) {
                PurchaseEntity purchaseEntity = new PurchaseEntity();
                purchaseEntity.setCount(newpurchase.getCount());
                purchaseEntity.setOrder(orderEntity);
                purchaseEntity.setCake(cakeRepository.findById(newpurchase.getCakeId()).orElseThrow(RuntimeException::new));

                orderEntity.getPurchases().add(purchaseEntity);
                orderRepository.saveAndFlush(orderEntity);
            }
        }
    }

    @Override
    public void deleteOrder(Long id) throws OrderNotFoundException {
        if(!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("There is no order with"+id);
        }
        else orderRepository.deleteById(id);
    }
}
