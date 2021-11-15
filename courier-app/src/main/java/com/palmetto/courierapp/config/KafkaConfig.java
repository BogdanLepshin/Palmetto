package com.palmetto.courierapp.config;

import com.palmetto.courierapp.data.event.OrderEvent;
import com.palmetto.courierapp.model.enums.OrderStatus;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
@RequiredArgsConstructor
@Slf4j
public class KafkaConfig {

  private final KafkaProperties properties;

  @Bean
  public ConsumerFactory<Long, OrderEvent> consumerFactory() {
    Map<String, Object> consumerConfig = properties.buildConsumerProperties();
    consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderEvent.class);
    consumerConfig.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, Boolean.FALSE);
    return new DefaultKafkaConsumerFactory<>(consumerConfig);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<Long, OrderEvent> containerFactory(
      ConsumerFactory<Long, OrderEvent> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<Long, OrderEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.getContainerProperties().setMissingTopicsFatal(false);
    factory.setRecordFilterStrategy(skipEventWithStatusDeliveredFilterStrategy());
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
    factory.setConcurrency(3);
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }

  @Bean
  public RecordFilterStrategy<Long, OrderEvent> skipEventWithStatusDeliveredFilterStrategy() {
    return consumerRecord -> {
      if (consumerRecord.value().getStatus() == OrderStatus.DELIVERED) {
        log.info("Event has been skipped. Event status={}", consumerRecord.value().getStatus());
        return true;
      }
      return false;
    };
  }
}
