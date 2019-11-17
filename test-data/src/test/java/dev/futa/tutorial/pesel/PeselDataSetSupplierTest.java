package dev.futa.tutorial.pesel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class PeselDataSetSupplierTest {

  @Test
  @DisplayName("Should return non empty set of PESEL numbers and decoded information")
  void shouldReturnSetOfPESELData() {

    // given
    final PeselDataSetSupplier peselDataSetSupplier = new PeselDataSetSupplier();

    // when
    final var validPeselSet = peselDataSetSupplier.getValidPeselSet();

    // then
    assertFalse(validPeselSet.isEmpty(), "Returned set can not be empty");
  }
}
