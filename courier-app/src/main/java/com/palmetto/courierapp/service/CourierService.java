package com.palmetto.courierapp.service;

import com.palmetto.courierapp.data.event.OrderEvent;
import com.palmetto.courierapp.kafka.producer.OrderKafkaProducer;
import com.palmetto.courierapp.model.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierService {

  private final OrderKafkaProducer producer;

  public void process(OrderEvent orderEvent) {
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
    orderEvent.setStatus(OrderStatus.DELIVERED);
    producer.sendEvent(orderEvent);
    log.info(
        "Event with orderId={} has been sent to notification-topic with status={}",
        orderEvent.getOrderId(),
        orderEvent.getStatus());
  }
}
