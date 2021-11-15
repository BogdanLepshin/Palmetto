package com.palmetto.clientapp.model;

import com.palmetto.clientapp.model.enums.OrderStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;

  @Column private String pizzaName;
  @Column private OrderStatus status;

  public OrderResponse toDto() {
    return OrderResponse.builder().pizzaName(pizzaName).orderId(orderId).build();
  }
}
