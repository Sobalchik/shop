package com.market.shop.db.orders;

import com.market.shop.db.OrderStatus;
import com.market.shop.exeption.OrderNotFoundException;
import com.market.shop.rest.dto.order.Order;
import com.market.shop.rest.dto.order.Orders;
import com.market.shop.rest.dto.order.Purchase;

public interface OrderService {
    void addOrder(Order order);
    void changeOrderStatus (Long id, OrderStatus orderStatus) throws OrderNotFoundException;
    void addPurchaseInList (Long id, Purchase purchase) throws OrderNotFoundException;
 //   void deletePurchaseInList (Long id, Purchase purchase) throws OrderNotFoundException;
    void deleteOrder (Long id) throws OrderNotFoundException;
    Orders getOrders();
}
