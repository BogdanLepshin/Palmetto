package com.palmetto.clientapp.kafka.producer;

import com.palmetto.clientapp.data.event.OrderEvent;
import com.palmetto.clientapp.util.MDCUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderKafkaProducer {
  private final KafkaTemplate<Long, OrderEvent> kafkaTemplate;

  private static final String TOPIC = "order-topic";

  public void sendEvent(OrderEvent orderEvent) {
    setMDCCorrelationId(orderEvent);
    kafkaTemplate.send(buildRecord(orderEvent));
    log.info("Event has been sent. ORDER_ID={}", orderEvent.getOrderId());
  }

  private void setMDCCorrelationId(OrderEvent orderEvent) {
    MDCUtil.setCorrelationId(TOPIC, orderEvent.getOrderId(), orderEvent.getPizzaName());
    orderEvent.setCorrelationId(MDCUtil.getCorrelationId());
  }

  private ProducerRecord<Long, OrderEvent> buildRecord(OrderEvent event) {
    return new ProducerRecord<>(TOPIC, event.getOrderId(), event);
  }
}
