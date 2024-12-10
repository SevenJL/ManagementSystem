package org.manage.exception;

public class InvalidInputException extends RuntimeException {
  public InvalidInputException(String message) {
    System.out.println("InvalidInputException: " + message);  // Print the message
  }
}
