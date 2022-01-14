package com.market.shop.db.users;

import com.market.shop.db.OrderStatus;
import com.market.shop.db.orders.OrderEntity;
import com.market.shop.db.orders.OrderRepository;
import com.market.shop.exeption.UserExistException;
import com.market.shop.rest.dto.user.User;
import com.market.shop.rest.dto.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository=userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void addUser(User user) throws UserExistException {
        if(userRepository.existsByNumber(user.getNumber())) {
            throw new UserExistException("User with NUMBER "+user.getNumber()+ " already create");
        }
        else {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(user.getName());
            userEntity.setNumber(user.getNumber());
            userRepository.saveAndFlush(userEntity);
        }
    }

    @Override
    public void deleteUser(String number) throws UserExistException {
        if(!userRepository.existsByNumber(number) || !FinishAllOrders(number)) {
            throw new UserExistException("User with NUMBER "+number+ " can't delete");
        }
        else {
            userRepository.deleteById( userRepository.findUserEntityByNumber(number).getId());
        }
    }

    boolean FinishAllOrders(String number){
        List<OrderEntity> orderList = orderRepository.findOrderEntitiesByUser_Number(number);
        for (OrderEntity el: orderList){
            if (!el.getOrderStatus().equals(OrderStatus.FINISHED)) return false;
        }
        return true;
    }

    @Override
    public Users getUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<User> userList = userEntityList.stream().map(c -> {
            User user=new User();
            user.setNumber(c.getNumber());
            user.setName(c.getName());
            return user;
        }).collect(Collectors.toList());
        Users users = new Users();
        users.setUserList(userList);
        return users;
    }
}
