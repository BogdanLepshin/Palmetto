package com.palmetto.clientapp.repository;

import com.palmetto.clientapp.model.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
  Optional<Order> findByOrderId(Long orderId);
  Optional<Order> findStatusByOrderId(Long orderId);
}
