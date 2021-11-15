package com.palmetto.clientapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
  private Long orderId;
  private String pizzaName;
}
