package dev.futa.tutorial.pesel.v01;

import com.google.common.collect.ImmutableSet;
import dev.futa.tutorial.pesel.Gender;
import dev.futa.tutorial.pesel.PeselDataSetSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PeselServiceTest {

  private PeselService peselService;
  private static PeselDataSetSupplier peselDataSetSupplier;

  @BeforeAll
  static void beforeAll() {
    peselDataSetSupplier = new PeselDataSetSupplier();
  }

  @BeforeEach
  void setUp() {
    peselService = new PeselService();
  }

  @ParameterizedTest
  @MethodSource("validPesels")
  @DisplayName("Should decode valid PESEL numbers")
  void shouldDecodeValidPesel(
      String givenPesel, LocalDate expectedBirthDate, Gender expectedGender) {

    // when
    final PeselInfo peselInfo = peselService.decodePesel(givenPesel);

    // then
    assertEquals(givenPesel, peselInfo.getPesel());
    assertEquals(expectedGender, peselInfo.getGender());
    assertEquals(expectedBirthDate, peselInfo.getBirthDate());
  }

  @ParameterizedTest
  @MethodSource("invalidPesels")
  @DisplayName("Should return null if invalid PESEL was passed")
  void shouldReturnNullOnInvalidPeselNumber(String givenPesel) {

    // when
    final PeselInfo peselInfo = peselService.decodePesel(givenPesel);

    // then
    assertNull(peselInfo, "PeselInfo object should be null for " + givenPesel);
  }

  static Stream<Arguments> validPesels() {
    return peselDataSetSupplier.getValidPeselSet().stream()
        .map(x -> Arguments.of(x.getPesel(), x.getBirthDate(), x.getGender()));
  }

  static Stream<Arguments> invalidPesels() {

    return ImmutableSet.builder()
        .addAll(peselDataSetSupplier.getInvalidLengthPeselSet())
        .addAll(peselDataSetSupplier.getInvalidCharactersPeselSet())
        .addAll(peselDataSetSupplier.getInvalidChecksumPeselSet())
        .addAll(peselDataSetSupplier.getInvalidEncodedDatePeselSet()).build().stream()
        .map(Arguments::of);
  }
}
