package dev.futa.tutorial.pesel.v05;

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
import java.util.Optional;
import java.util.stream.Stream;

import static dev.futa.tutorial.pesel.v05.AssertOptional.assertEmpty;
import static dev.futa.tutorial.pesel.v05.AssertOptional.assertPresent;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeselServiceTest {
  private static PeselDataSetSupplier peselDataSetSupplier;
  private PeselService peselService;

  @BeforeAll
  static void beforeAll() {
    peselDataSetSupplier = new PeselDataSetSupplier();
  }

  static Stream<Arguments> validPesels() {
    return peselDataSetSupplier.getValidPeselSet().stream()
        .map(x -> Arguments.of(x.getPesel(), x.getBirthDate(), x.getGender()));
  }

  static Stream<Arguments> invalidPesels() {
    return ImmutableSet.builder().addAll(peselDataSetSupplier.getInvalidLengthPeselSet())
        .addAll(peselDataSetSupplier.getInvalidCharactersPeselSet())
        .addAll(peselDataSetSupplier.getInvalidChecksumPeselSet())
        .addAll(peselDataSetSupplier.getInvalidEncodedDatePeselSet()).build().stream()
        .map(Arguments::of);
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
    final Optional<PeselInfo> peselInfo = peselService.decodePesel(givenPesel);

    // then
    assertPresent(peselInfo, "PeselInfo from " + givenPesel);

    peselInfo.ifPresent(
        flatPeselInfo ->
            assertAll(
                () -> assertEquals(givenPesel, flatPeselInfo.getPesel()),
                () -> assertEquals(expectedGender, flatPeselInfo.getGender()),
                () -> assertEquals(expectedBirthDate, flatPeselInfo.getBirthDate())));
  }

  @ParameterizedTest
  @MethodSource("invalidPesels")
  @DisplayName("Should return NullPeselInfo object for invalid PESEL")
  void shouldThrowExceptionForInvalidLengthOrCharacters(String givenPesel) {

    // when
    final Optional<PeselInfo> peselInfo = peselService.decodePesel(givenPesel);

    // then
    assertEmpty(peselInfo, "PeselInfo from " + givenPesel);
  }
}
