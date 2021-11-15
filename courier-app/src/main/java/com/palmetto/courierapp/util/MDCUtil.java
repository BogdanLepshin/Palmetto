package com.palmetto.courierapp.util;

import java.util.StringJoiner;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

@UtilityClass
public class MDCUtil {

  private static final String CORRELATION_ID = "correlationId";
  private static final String UNDERSCORE_DELIMITER = "_";

  public String generateId() {
    return UUID.randomUUID().toString();
  }

  public void setCorrelationId(String topicName, Long orderId, String pizzaName) {
    setCorrelationId(generateCorrelationId(topicName, orderId, pizzaName));
  }

  public void setCorrelationId(String correlationId) {
    MDC.put(CORRELATION_ID, correlationId);
  }

  private String generateCorrelationId(String topicName, Long orderId, String pizzaName) {
    return new StringJoiner(UNDERSCORE_DELIMITER)
        .add(topicName)
        .add(String.valueOf(orderId))
        .add(pizzaName)
        .toString();
  }

  public String getCorrelationId() {
    return MDC.get(CORRELATION_ID);
  }
}
