package dev.futa.tutorial.pesel.v06;

import io.vavr.control.Either;

import static org.junit.jupiter.api.Assertions.fail;

class AssertEither {
  static <L, R> void assertEmpty(Either<L, R> either, String objectInfo) {
    if (either.isRight()) {
      fail("Expected EITHER should be empty but contains " + objectInfo);
    }
  }

  static <L, R> void assertPresent(Either<L, R> optional, String objectInfo) {
    if (optional.isLeft()) {
      fail("Expected EITHER should be present but contains " + objectInfo);
    }
  }
}
