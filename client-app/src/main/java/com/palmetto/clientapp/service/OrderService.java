package com.palmetto.clientapp.service;

import com.palmetto.clientapp.data.event.OrderEvent;
import com.palmetto.clientapp.kafka.producer.OrderKafkaProducer;
import com.palmetto.clientapp.model.Order;
import com.palmetto.clientapp.model.OrderRequest;
import com.palmetto.clientapp.model.OrderResponse;
import com.palmetto.clientapp.model.enums.OrderStatus;
import com.palmetto.clientapp.repository.OrdersRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderKafkaProducer producer;
  private final OrdersRepository ordersRepository;

  @Transactional
  public OrderResponse process(OrderRequest orderRequest) {
    Order order = ordersRepository.save(buildOrder(orderRequest));
    producer.sendEvent(buildOrderEvent(order));
    return order.toDto();
  }

  private OrderEvent buildOrderEvent(Order order) {
    return OrderEvent.builder()
        .orderId(order.getOrderId())
        .pizzaName(order.getPizzaName())
        .status(order.getStatus())
        .build();
  }

  private Order buildOrder(OrderRequest orderRequest) {
    return Order.builder()
        .pizzaName(orderRequest.getPizzaName())
        .status(OrderStatus.RECEIVED)
        .build();
  }
}
