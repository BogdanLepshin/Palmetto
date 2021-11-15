package com.palmetto.courierapp.kafka.producer;

import com.palmetto.courierapp.data.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderKafkaProducer {
  private final KafkaTemplate<Long, OrderEvent> kafkaTemplate;

  @Value("${app.kafka.notification.topic}")
  private String topic;

  public void sendEvent(OrderEvent orderEvent) {
    kafkaTemplate.send(buildRecord(orderEvent));
  }

  private ProducerRecord<Long, OrderEvent> buildRecord(OrderEvent event) {
    return new ProducerRecord<>(topic, event.getOrderId(), event);
  }
}
