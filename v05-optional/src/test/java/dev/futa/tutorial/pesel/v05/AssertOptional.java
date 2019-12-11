package dev.futa.tutorial.pesel.v05;

import org.junit.jupiter.api.Assertions;

import java.util.Optional;

public class AssertOptional {
  public static <T> void assertEmpty(Optional<T> optional, String message) {
    if (optional.isPresent()) {
      Assertions.fail("Expected optional should be empty: " + message);
    }
  }

  public static <T> void assertPresent(Optional<T> optional, String message) {
    if (optional.isEmpty()) {
      Assertions.fail("Expected optional should be present: " + message);
    }
  }
}
