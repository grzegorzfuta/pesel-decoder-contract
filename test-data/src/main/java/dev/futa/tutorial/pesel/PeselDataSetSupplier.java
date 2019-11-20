package dev.futa.tutorial.pesel;

import java.time.LocalDate;
import java.util.Set;

import static dev.futa.tutorial.pesel.Gender.FEMALE;
import static dev.futa.tutorial.pesel.Gender.MALE;

public class PeselDataSetSupplier {

  public Set<DecodedPeselData> getValidPeselSet() {

    return Set.of(
        new DecodedPeselData("75123103437", LocalDate.of(1975, 12, 31), MALE),
        new DecodedPeselData("09273006542", LocalDate.of(2009, 7, 30), FEMALE),
        new DecodedPeselData("92041903790", LocalDate.of(1992, 4, 19), MALE),
        new DecodedPeselData("44051401359", LocalDate.of(1944, 5, 14), MALE),
        new DecodedPeselData("70100619901", LocalDate.of(1970, 10, 6), FEMALE),
        new DecodedPeselData("80082107231", LocalDate.of(1980, 8, 21), MALE),
        new DecodedPeselData("00301202868", LocalDate.of(2000, 10, 12), FEMALE),
        new DecodedPeselData("00271100559", LocalDate.of(2000, 7, 11), MALE),
        new DecodedPeselData("12241301417", LocalDate.of(2012, 4, 13), MALE),
        new DecodedPeselData("12252918020", LocalDate.of(2012, 5, 29), FEMALE),
        new DecodedPeselData("12262911406", LocalDate.of(2012, 6, 29), FEMALE),
        new DecodedPeselData("12441301413", LocalDate.of(2112, 4, 13), MALE),
        new DecodedPeselData("14522918020", LocalDate.of(2114, 12, 29), FEMALE),
        new DecodedPeselData("12641301419", LocalDate.of(2212, 4, 13), MALE),
        new DecodedPeselData("12702918020", LocalDate.of(2212, 10, 29), FEMALE),
        new DecodedPeselData("89841301417", LocalDate.of(1889, 4, 13), MALE),
        new DecodedPeselData("98912918021", LocalDate.of(1898, 11, 29), FEMALE));
  }

  public Set<String> getInvalidLengthPeselSet() {
    return Set.of("", "123", "012345678901234");
  }

  public Set<String> getInvalidCharactersPeselSet() {
    return Set.of("0827140234a", "0a271402343", "a6231202341");
  }

  public Set<String> getInvalidChecksumPeselSet() {
    return Set.of("06231202347", "08271402340");
  }

  public Set<String> getInvalidEncodedDatePeselSet() {
    return Set.of(
        "20223003433",
        "19222903431",
        "75123203434",
        "25273203437",
        "25370203433",
        "13443101230",
        "14551204561",
        "15613201234",
        "15771001231",
        "15874201235",
        "15951001235",
        "85023003436");
  }
}
