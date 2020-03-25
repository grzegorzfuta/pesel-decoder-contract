package dev.futa.tutorial.pesel.v06;

import dev.futa.tutorial.pesel.Gender;
import dev.futa.tutorial.pesel.PeselDataSetSupplier;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.futa.tutorial.pesel.v06.AssertEither.assertEmpty;
import static dev.futa.tutorial.pesel.v06.AssertEither.assertPresent;
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

  static Set<Arguments> invalidPesels() {

    Set<Arguments> arguments =
        peselDataSetSupplier.getInvalidLengthPeselSet().stream()
            .map(pesel -> Arguments.of(pesel, FailureReason.INVALID_LENGTH))
            .collect(Collectors.toSet());

    arguments.addAll(
        peselDataSetSupplier.getInvalidCharactersPeselSet().stream()
            .map(pesel -> Arguments.of(pesel, FailureReason.INVALID_CHARACTERS))
            .collect(Collectors.toList()));

    arguments.addAll(
        peselDataSetSupplier.getInvalidChecksumPeselSet().stream()
            .map(pesel -> Arguments.of(pesel, FailureReason.INVALID_CHECKSUM))
            .collect(Collectors.toList()));

    arguments.addAll(
        peselDataSetSupplier.getInvalidEncodedDatePeselSet().stream()
            .map(pesel -> Arguments.of(pesel, FailureReason.INVALID_DATE))
            .collect(Collectors.toList()));

    return arguments;
  }

  @BeforeEach
  void setUp() {
    peselService = new PeselService();
  }

  @ParameterizedTest
  @MethodSource("validPesels")
  @DisplayName("Should decode valid PESEL numbers")
  void shouldDecodeValidPesel(
      final String givenPesel, final LocalDate expectedBirthDate, final Gender expectedGender) {

    // when
    final Either<FailureReason, PeselInfo> peselInfos = peselService.decodePesel(givenPesel);

    // then
    assertPresent(peselInfos, "PeselInfo from " + givenPesel);

    peselInfos.peek(
        peselInfo ->
            Assertions.assertAll(
                () -> assertEquals(givenPesel, peselInfo.getPesel()),
                () -> assertEquals(expectedGender, peselInfo.getGender()),
                () -> assertEquals(expectedBirthDate, peselInfo.getBirthDate())));
  }

  @ParameterizedTest
  @MethodSource("invalidPesels")
  @DisplayName("Should return NullPeselInfo object for invalid PESEL")
  void shouldReturnLeftInvalidPesel(final String givenPesel, final FailureReason expectedReason) {

    // when
    final Either<FailureReason, PeselInfo> peselInfos = peselService.decodePesel(givenPesel);

    // then
    assertEmpty(peselInfos, "PeselInfo from " + givenPesel);

    assertEquals(expectedReason, peselInfos.getLeft(), "for PESEL: " + givenPesel);
  }
}
