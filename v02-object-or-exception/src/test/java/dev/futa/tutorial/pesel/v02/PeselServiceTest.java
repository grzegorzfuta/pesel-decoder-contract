package dev.futa.tutorial.pesel.v02;

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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
  @MethodSource("invalidPatternSet")
  @DisplayName("Should throw exception for invalid characters or length")
  void shouldThrowExceptionForInvalidLengthOrCharacters(String givenPesel) {

    // when
    final PeselDecodingException peselDecodingException =
        assertThrows(PeselDecodingException.class, () -> peselService.decodePesel(givenPesel));

    // then
    final String expectedMessage = "Invalid PESEL pattern for " + givenPesel;
    assertEquals(expectedMessage, peselDecodingException.getMessage(), "asd");
  }

  @ParameterizedTest
  @MethodSource("invalidChecksumSet")
  @DisplayName("Should throw exception for checksum in PESEL number")
  void shouldThrowExceptionForInvalidChecksum(String givenPesel) {

    // when
    final PeselDecodingException peselDecodingException =
        assertThrows(PeselDecodingException.class, () -> peselService.decodePesel(givenPesel));

    // then
    final String expectedMessage = "Checksum is not valid for PESEL " + givenPesel;
    assertEquals(expectedMessage, peselDecodingException.getMessage());
  }

  @ParameterizedTest
  @MethodSource("invalidEncodedDateSet")
  @DisplayName("Should throw exception for invalid encoded date in PESEL")
  void shouldThrowExceptionForInvalidEncodedDate(String givenPesel) {

    // when
    final PeselDecodingException peselDecodingException =
        assertThrows(PeselDecodingException.class, () -> peselService.decodePesel(givenPesel));

    // then
    final String expectedMessage = "Encoded date seems not to be valid for PESEL " + givenPesel;
    assertEquals(expectedMessage, peselDecodingException.getMessage());
  }

  static Stream<Arguments> validPesels() {
    return peselDataSetSupplier.getValidPeselSet().stream()
        .map(x -> Arguments.of(x.getPesel(), x.getBirthDate(), x.getGender()));
  }

  static Stream<Arguments> invalidPatternSet() {
    return ImmutableSet.builder().addAll(peselDataSetSupplier.getInvalidLengthPeselSet())
        .addAll(peselDataSetSupplier.getInvalidCharactersPeselSet()).build().stream()
        .map(Arguments::of);
  }

  static Stream<Arguments> invalidChecksumSet() {
    return ImmutableSet.builder().addAll(peselDataSetSupplier.getInvalidChecksumPeselSet()).build()
        .stream()
        .map(Arguments::of);
  }

  static Stream<Arguments> invalidEncodedDateSet() {
    return ImmutableSet.builder().addAll(peselDataSetSupplier.getInvalidEncodedDatePeselSet())
        .build().stream()
        .map(Arguments::of);
  }
}
