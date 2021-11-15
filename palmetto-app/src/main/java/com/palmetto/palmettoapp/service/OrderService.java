package com.palmetto.palmettoapp.service;

import com.palmetto.palmettoapp.data.event.OrderEvent;
import com.palmetto.palmettoapp.kafka.producer.OrderKafkaProducer;
import com.palmetto.palmettoapp.model.enums.OrderStatus;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

  private final OrderKafkaProducer producer;

  public void process(OrderEvent orderEvent) {
    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
    orderEvent.setStatus(OrderStatus.PROCESSED);
    producer.sendEvent(orderEvent);
    log.info("Event has been sent to notification-topic.");
  }
}
