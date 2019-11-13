package dev.futa.tutorial.pesel;

import java.time.LocalDate;

public class DecodedPeselData {
  private final String pesel;
  private final LocalDate birthDate;
  private final Gender gender;

  DecodedPeselData(String pesel, LocalDate birthDate, Gender gender) {
    this.pesel = pesel;
    this.birthDate = birthDate;
    this.gender = gender;
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
}
