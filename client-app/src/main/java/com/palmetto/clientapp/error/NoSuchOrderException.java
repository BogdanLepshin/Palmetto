package com.palmetto.clientapp.error;

public class NoSuchOrderException extends RuntimeException {

  public NoSuchOrderException(String message) {
    super(message);
  }
}
