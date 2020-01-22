package dev.futa.tutorial.pesel.v06;

import com.google.common.collect.ImmutableMap;
import dev.futa.tutorial.pesel.Gender;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static dev.futa.tutorial.pesel.Gender.FEMALE;
import static dev.futa.tutorial.pesel.Gender.MALE;
import static java.lang.Character.getNumericValue;
import static java.lang.Integer.parseInt;
import static java.util.Objects.nonNull;

/**
 * Service class that demonstrates decoding PESEL value and return decoded data in a functional
 * style (vavr Either).
 */
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
   * Method decodes date of birth and gender from PESEL number.
   *
   * <p>Takes PESEL number as a String and return Either with left value of {@link FailureReason}
   * and right {@link PeselInfo}.
   *
   * @param pesel PESEL number to decode data from
   * @return Either&lt;FailureReason, PeselInfo&gt; object that will contain {@link FailureReason}
   *     in its <i>left value</i> or {@link PeselInfo} in case of proper
   * @see FailureReason
   * @see PeselInfo
   */
  public Either<FailureReason, PeselInfo> decodePesel(final String pesel) {

    checkArgument(nonNull(pesel), "PESEL number can not be null");

    return getValidLengthPesel(pesel)
        .flatMap(this::getValidCharactersPesel)
        .flatMap(this::getValidChecksumPesel)
        .flatMap(this::extractPeselData);
  }

  private Either<FailureReason, PeselInfo> extractPeselData(String checkedPesel) {
    return extractBirthDate(checkedPesel)
        .toEither()
        .map(birthDate -> new PeselInfo(checkedPesel, birthDate, extractGender(checkedPesel)))
        .mapLeft(throwable -> FailureReason.INVALID_DATE);
  }

  private Either<FailureReason, String> getValidChecksumPesel(String pesel) {
    return isChecksumValid(pesel)
        ? Either.right(pesel)
        : Either.left(FailureReason.INVALID_CHECKSUM);
  }

  private Either<FailureReason, String> getValidCharactersPesel(String pesel) {
    return PESEL_PATTERN.matcher(pesel).matches()
        ? Either.right(pesel)
        : Either.left(FailureReason.INVALID_CHARACTERS);
  }

  private Either<FailureReason, String> getValidLengthPesel(String pesel) {
    return pesel.length() == 11 ? Either.right(pesel) : Either.left(FailureReason.INVALID_LENGTH);
  }

  private boolean isChecksumValid(String pesel) {
    final int[] digitsOfPesel = peselToDigitsArray(pesel);
    return calculateWeightedSum(digitsOfPesel) % 10 == 0;
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

  private Try<LocalDate> extractBirthDate(String pesel) {

    int year = parseInt(pesel.substring(0, 2));
    int monthWithCenturyCode = parseInt(pesel.substring(2, 4));
    int month = monthWithCenturyCode % 20;
    int dayOfMonth = parseInt(pesel.substring(4, 6));

    final int fullYear = extractCenturyFirstYear(month, monthWithCenturyCode) + year;

    return Try.of(() -> LocalDate.of(fullYear, month, dayOfMonth));
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
