package dev.futa.tutorial.pesel.v04;

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

import static org.junit.jupiter.api.Assertions.*;

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
    final PeselInfo peselInfo = peselService.decodePesel(givenPesel);

    // then
    assertTrue(peselInfo.isValid(), "Success status should be true");
    assertEquals(
        "dev.futa.tutorial.pesel.v04.CorrectPeselInfo",
        peselInfo.getClass().getName(),
        "Returned object has to be instance of dev.futa.tutorial.pesel.v04.CorrectPeselInfo");
    assertEquals(givenPesel, peselInfo.getPesel());
    assertEquals(expectedGender, peselInfo.getGender());
    assertEquals(expectedBirthDate, peselInfo.getBirthDate());
  }

  @ParameterizedTest
  @MethodSource("invalidPesels")
  @DisplayName("Should return NullPeselInfo object for invalid PESEL")
  void shouldThrowExceptionForInvalidLengthOrCharacters(String givenPesel) {

    // when
    final PeselInfo peselInfo = peselService.decodePesel(givenPesel);

    // then
    assertFalse(peselInfo.isValid(), "Success status should be false");
    assertEquals(
        "dev.futa.tutorial.pesel.v04.NullPeselInfo",
        peselInfo.getClass().getName(),
        "Returned object has to be instance of dev.futa.tutorial.pesel.v04.NullPeselInfo");
    assertEquals(
        givenPesel,
        peselInfo.getPesel(),
        "PESEL returned in PeselInfo should be equal to delivered to method");
    assertNull(peselInfo.getGender(), "Value of gender should be null");
    assertNull(peselInfo.getBirthDate(), "Value of birth date should be null");
  }
}
