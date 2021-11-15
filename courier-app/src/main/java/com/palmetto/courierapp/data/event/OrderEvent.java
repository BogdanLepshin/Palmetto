package com.palmetto.courierapp.data.event;

import com.palmetto.courierapp.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderEvent {
  private Long orderId;
  private String correlationId;
  private String pizzaName;
  private OrderStatus status;
}
