package dev.futa.tutorial.pesel.v02;

import com.google.common.collect.ImmutableMap;
import dev.futa.tutorial.pesel.Gender;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Pattern;

import static dev.futa.tutorial.pesel.Gender.FEMALE;
import static dev.futa.tutorial.pesel.Gender.MALE;
import static java.lang.Character.getNumericValue;
import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;

public class PeselService {

  private static final byte PESEL_LENGTH = 11;
  private static final int[] PESEL_WEIGHTS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1};
  private static final Pattern PESEL_PATTERN = Pattern.compile("[0-9]{11}");

  private static final Map<Integer, Integer> YEAR_CODES =
      ImmutableMap.of(
          0, 1900,
          20, 2000,
          40, 2100,
          60, 2200,
          80, 1800);

  /**
   * Method decodes date of birth and gender from PESEL number. Will return <code>PeselDecodingException</code>
   * in case of invalid PESEL number.
   *
   * @param pesel PESEL number to decode data from
   * @return object contains decoded data and PESEL itself
   * @throws PeselDecodingException if given PESEL can not be decoded
   */
  public PeselInfo decodePesel(final String pesel) {

    if (isNull(pesel)) {
      throw new IllegalArgumentException("PESEL number can not be null");
    }

    if (isNotValidPattern(pesel)) {
      throw new PeselDecodingException("Invalid PESEL pattern for " + pesel);
    }

    if (isChecksumValid(pesel)) {
      final LocalDate birthDate = extractBirthDate(pesel);
      final Gender gender = extractGender(pesel);

      return new PeselInfo(pesel, birthDate, gender);
    }

    throw new PeselDecodingException("Can not decode information from PESEL " + pesel);
  }

  private boolean isChecksumValid(String pesel) {
    final int[] digitsOfPesel = peselToDigitsArray(pesel);
    if (calculateWeightedSum(digitsOfPesel) % 10 != 0) {
      throw new PeselDecodingException("Checksum is not valid for PESEL " + pesel);
    }
    return true;
  }

  private int[] peselToDigitsArray(String pesel) {
    int[] digits = new int[PESEL_LENGTH];
    final char[] peselCharacters = pesel.toCharArray();

    for (int i = 0; i < PESEL_LENGTH; i++) {
      digits[i] = getNumericValue(peselCharacters[i]);
    }
    return digits;
  }

  private int calculateWeightedSum(int[] digitsOfPesel) {
    int sum = 0;
    for (byte i = 0; i < PESEL_LENGTH; i++) {
      sum += digitsOfPesel[i] * PESEL_WEIGHTS[i];
    }
    return sum;
  }

  private boolean isNotValidPattern(String pesel) {
    return !PESEL_PATTERN.matcher(pesel).matches();
  }

  private LocalDate extractBirthDate(String pesel) {
    int year = parseInt(pesel.substring(0, 2));
    int monthWithCenturyCode = parseInt(pesel.substring(2, 4));
    int month = monthWithCenturyCode % 20;
    int dayOfMonth = parseInt(pesel.substring(4, 6));

    year = extractCenturyFirstYear(month, monthWithCenturyCode) + year;

    try {
      return LocalDate.of(year, month, dayOfMonth);
    } catch (DateTimeException e) {
      throw new PeselDecodingException("Encoded date seems not to be valid for PESEL " + pesel);
    }
  }

  private Gender extractGender(String pesel) {
    final int tenthDigitInPesel = getNumericValue(pesel.charAt(9));
    return tenthDigitInPesel % 2 == 0 ? FEMALE : MALE;
  }

  private int extractCenturyFirstYear(int month, int monthWithCenturyCode) {
    final int yearCode = monthWithCenturyCode - month;
    return YEAR_CODES.get(yearCode);
  }
}
