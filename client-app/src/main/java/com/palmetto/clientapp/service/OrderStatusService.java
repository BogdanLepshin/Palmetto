package com.palmetto.clientapp.service;

import com.palmetto.clientapp.error.NoSuchOrderException;
import com.palmetto.clientapp.model.Order;
import com.palmetto.clientapp.model.enums.OrderStatus;
import com.palmetto.clientapp.repository.OrdersRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
  private final OrdersRepository repository;

  @Transactional
  public void updateStatus(Long orderId, OrderStatus orderStatus) {
    Optional<Order> ordersOptional = repository.findByOrderId(orderId);
    if (ordersOptional.isEmpty()) {
      throw new NoSuchOrderException(String.format("There's no order with such id=%d", orderId));
    }
    Order order = ordersOptional.get();
    order.setStatus(orderStatus);

    repository.save(order);
  }
}
