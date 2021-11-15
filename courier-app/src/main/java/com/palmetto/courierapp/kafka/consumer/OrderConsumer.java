package com.palmetto.courierapp.kafka.consumer;

import com.palmetto.courierapp.data.event.OrderEvent;
import com.palmetto.courierapp.service.CourierService;
import com.palmetto.courierapp.util.MDCUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

  private final CourierService courierService;

  @KafkaListener(
      topics = "${app.kafka.notification.topic}",
      groupId = "${app.kafka.notification.group}",
      containerFactory = "containerFactory")
  public void orderHandler(ConsumerRecord<Long, OrderEvent> consumerRecord) {
    try {
      OrderEvent orderEvent = consumerRecord.value();
      MDCUtil.setCorrelationId(orderEvent.getCorrelationId());
      log.info("Event received. RECORD={}", consumerRecord);

      courierService.process(orderEvent);
      log.info("Order with orderId={} has been delivered", orderEvent.getOrderId());
    } catch (Exception e) {
      log.error("Error while order delivery processing.", e);
    } finally {
      MDC.clear();
    }
  }
}
