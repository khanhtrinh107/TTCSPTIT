package com.example.demo.service.impl;

import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.SaleOrder;
import com.example.demo.entity.dto.Cart;
import com.example.demo.repository.*;
import com.example.demo.service.SaleOrderService;
import com.example.demo.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {
    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    

    @Override
    public List<SaleOrder> findALl() {
        return saleOrderRepository.findAll();
    }

    @Override
    public SaleOrder findById(int id) {
        return saleOrderRepository.findById(id).orElseThrow();
    }

    @Override
    public SaleOrder create() {
        return null;
    }

    @Override
    public boolean addOrder(Map<Integer, Cart> carts, int id  ) {
       try {
           System.out.println("OK");
           SaleOrder saleOrder = new SaleOrder();



           saleOrder.setUser(userRepository.findById(id).orElseThrow());
           saleOrderRepository.save(saleOrder);
           System.out.println("OK1");
           for(Cart cart : carts.values()){
               OrderDetail orderDetail = new OrderDetail();
               orderDetail.setSaleOrder(saleOrder);
               orderDetail.setBook(bookRepository.findById(cart.getBookId()).orElseThrow());
               orderDetail.setInitPrice(cart.getPrice());
               orderDetail.setQuantity(cart.getQuantity());
               orderDetailRepository.save(orderDetail);
           }
           System.out.println("OK2");
           return true;
       }catch (Exception ex){
           System.out.println(ex.getMessage());
           ex.getStackTrace();
       }
        System.out.println("NOT OK");
        return false;
    }
}
