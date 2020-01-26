package dev.futa.tutorial.pesel.v06;

import dev.futa.tutorial.pesel.Gender;

import java.time.LocalDate;

public class PeselInfo {
  private final String pesel;
  private final LocalDate birthDate;
  private final Gender gender;

  PeselInfo(String pesel, LocalDate birthDate, Gender gender) {
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
