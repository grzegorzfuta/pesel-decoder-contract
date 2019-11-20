package dev.futa.tutorial.pesel.v02;

public class PeselDecodingException extends RuntimeException {
  public PeselDecodingException(final String message) {
    super(message);
  }
}
