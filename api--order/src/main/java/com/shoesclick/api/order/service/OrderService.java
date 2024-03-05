package com.shoesclick.api.order.service;

import com.shoesclick.api.order.aspect.ObjectReturnType;
import com.shoesclick.api.order.aspect.ReturnNullObject;
import com.shoesclick.api.order.domain.PaymentDomain;
import com.shoesclick.api.order.entity.Order;
import com.shoesclick.api.order.entity.Status;
import com.shoesclick.api.order.repository.CustomerRepository;
import com.shoesclick.api.order.repository.OrderRepository;
import com.shoesclick.api.order.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final PaymentService paymentService;

    private final NotificationService notificationService;

    public OrderService(CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository, PaymentService paymentService, NotificationService notificationService) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }


    @Transactional
    public Status save(Order order, PaymentDomain paymentDomain){

        var customer = customerRepository.findById(order.getIdCustomer());
        order.getOrderItems().stream().forEach(item ->{
            var product = productRepository.findById(item.getIdProduct());
            item.setNameProduct(product.getName());
            item.setPrice(product.getPrice());
        });
        orderRepository.save(order);
        paymentService.save(paymentDomain,order);

        notificationService.sendNotification(order);
        return new Status(0,"SUCESSO");
    }

    public List<Order> listOrderByCustomer(Long idCustomer){
       return orderRepository.findByIdCustomer(idCustomer);
    }

    @ReturnNullObject(ObjectReturnType.OBJECT)
    public Order findById(Long id){
        return orderRepository.findById(id).orElse(null);
    }
}
