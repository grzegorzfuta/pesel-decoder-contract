package dev.futa.tutorial.pesel.v05;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

public class AssertOptional {
  public static <T> void assertEmpty(Optional<T> optional, String message) {
    if (optional.isPresent()) {
      fail("Expected optional should be empty: " + message);
    }
  }

  public static <T> void assertPresent(Optional<T> optional, String message) {
    if (optional.isEmpty()) {
      fail("Expected optional should be present: " + message);
    }
  }
}
