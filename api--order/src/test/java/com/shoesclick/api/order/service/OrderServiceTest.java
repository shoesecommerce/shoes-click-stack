package com.shoesclick.api.order.service;

import com.shoesclick.api.order.domain.PaymentDomain;
import com.shoesclick.api.order.entity.*;
import com.shoesclick.api.order.exception.ElementNotFoundException;
import com.shoesclick.api.order.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrderServiceTest extends AbstractServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private NotificationService notificationService;


    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderService = setupServiceTest(orderService);
    }


    @Test
    void shouldSaveOrderSuccess() {

        when(customerRepository.findById(anyLong()))
                .thenReturn(
                        new Customer()
                                .setId(1L)
                                .setName("NAME"));

        when(productRepository
                .findById(anyLong()))
                .thenReturn(new Product()
                        .setId(1L)
                        .setName("PRODUTO 1")
                        .setPrice(new BigDecimal(10.00)))
                .thenReturn(new Product()
                        .setId(2L)
                        .setName("PRODUTO 2")
                        .setPrice(new BigDecimal(10.00)))
                .thenReturn(new Product()
                        .setId(3L)
                        .setName("PRODUTO 3")
                        .setPrice(new BigDecimal(10.00)));

        var order = new Order();
        var orderDomain = new PaymentDomain();
        order.setIdCustomer(1L);
        order.setOrderItems(Set.of(new OrderItem().setIdProduct(1L)));
        orderService.save(order, orderDomain);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(paymentService, times(1)).save(any(PaymentDomain.class), any(Order.class));
        verify(notificationService, times(1)).sendNotification(any(Order.class));

    }

    @Test
    void shouldListOrderByCustomer() {
        when(orderRepository.findByIdCustomer(anyLong())).thenReturn(Arrays.asList(new Order().setId(1L).setIdCustomer(1L).setCreatedAt(LocalDateTime.now())));
        var list = orderService.listOrderByCustomer(1L);
        assertNotNull(list);
    }

    @Test
    void shouldFindOrderByIdSuccess() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(new Order()));
        var order = orderService.findById(1L);
        assertNotNull(order);
    }

    @Test
    void shouldThrow_ElementNotFoundException_OrderByIdNull() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ElementNotFoundException.class, () -> orderService.findById(1L));
    }

}
