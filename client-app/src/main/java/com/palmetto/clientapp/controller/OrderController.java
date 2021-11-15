package com.palmetto.clientapp.controller;

import com.palmetto.clientapp.model.Order;
import com.palmetto.clientapp.model.OrderRequest;
import com.palmetto.clientapp.model.OrderResponse;
import com.palmetto.clientapp.repository.OrdersRepository;
import com.palmetto.clientapp.service.OrderService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final OrdersRepository ordersRepository;

  @PostMapping("/order")
  public OrderResponse getOrder(@Valid @RequestBody OrderRequest orderRequest) {
    return orderService.process(orderRequest);
  }

  @GetMapping("/order/{id}/status")
  public String getStatus(@PathVariable("id") Long orderId) {
    Optional<Order> order = ordersRepository.findStatusByOrderId(orderId);
    if (order.isEmpty()) {
      return "There's no such order with order_id=" + orderId;
    }
    return "Order status: " + order.get().getStatus().name();
  }

  @GetMapping("/orders")
  public List<Order> getAllOrders() {
    return ordersRepository.findAll();
  }
}
