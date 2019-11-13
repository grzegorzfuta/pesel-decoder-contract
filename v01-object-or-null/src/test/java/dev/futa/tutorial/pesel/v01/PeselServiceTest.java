package dev.futa.tutorial.pesel.v01;

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
    return Stream.of(
        Arguments.of(""),
        Arguments.of("12345"),
        Arguments.of("012345678901234"),
        Arguments.of("abcd-abcd-1"),
        Arguments.of("20223003433"),
        Arguments.of("19222903431"),
        Arguments.of("75123203434"),
        Arguments.of("25273203437"),
        Arguments.of("25370203433"),
        Arguments.of("13443101230"),
        Arguments.of("14551204561"),
        Arguments.of("15613201234"),
        Arguments.of("15771001231"),
        Arguments.of("15874201235"),
        Arguments.of("15951001235"),
        Arguments.of("85023003436"));
  }
}
