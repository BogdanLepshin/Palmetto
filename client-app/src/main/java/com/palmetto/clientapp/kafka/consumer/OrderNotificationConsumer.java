package com.palmetto.clientapp.kafka.consumer;

import com.palmetto.clientapp.data.event.OrderEvent;
import com.palmetto.clientapp.service.OrderStatusService;
import com.palmetto.clientapp.util.MDCUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderNotificationConsumer {

  private final OrderStatusService orderStatusService;

  @KafkaListener(
      topics = "notification-topic",
      groupId = "client-app-group",
      containerFactory = "containerFactory")
  public void orderNotificationHandler(ConsumerRecord<Long, OrderEvent> consumerRecord) {
    try {
      OrderEvent orderEvent = consumerRecord.value();
      MDCUtil.setCorrelationId(orderEvent.getCorrelationId());
      log.info("Event received. ConsumerRecord={}", consumerRecord);
      orderStatusService.updateStatus(orderEvent.getOrderId(), orderEvent.getStatus());
    } catch (RuntimeException e) {
      log.error("Error while updating order status.", e);
    } finally {
      MDC.clear();
    }
  }
}
