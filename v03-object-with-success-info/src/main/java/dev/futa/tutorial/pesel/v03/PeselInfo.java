package dev.futa.tutorial.pesel.v03;

import dev.futa.tutorial.pesel.Gender;

import java.time.LocalDate;

public class PeselInfo {
  private String pesel;
  private LocalDate birthDate;
  private Gender gender;
  private boolean valid = true;

  private PeselInfo() {}

  static PeselInfo ofValid(String pesel, LocalDate birthDate, Gender gender) {

    final PeselInfo peselInfo = new PeselInfo();

    peselInfo.pesel = pesel;
    peselInfo.birthDate = birthDate;
    peselInfo.gender = gender;

    return peselInfo;
  }

  static PeselInfo ofInvalid(String pesel) {

    final PeselInfo failedDecodingPeselInfo = new PeselInfo();

    failedDecodingPeselInfo.pesel = pesel;
    failedDecodingPeselInfo.valid = false;

    return failedDecodingPeselInfo;
  }

  public String getPesel() {
    return pesel;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public Gender getGender() {
    return gender;
  }

  public boolean isValid() {
    return valid;
  }
}
