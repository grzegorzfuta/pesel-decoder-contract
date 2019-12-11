package dev.futa.tutorial.pesel.v04;

import dev.futa.tutorial.pesel.Gender;

import java.time.LocalDate;

public class CorrectPeselInfo implements PeselInfo {
  private String pesel;
  private LocalDate birthDate;
  private Gender gender;

  private CorrectPeselInfo(String pesel, LocalDate birthDate, Gender gender) {
    this.pesel = pesel;
    this.birthDate = birthDate;
    this.gender = gender;
  }

  public static PeselInfo of(String pesel, LocalDate birthDate, Gender gender) {
    return new CorrectPeselInfo(pesel, birthDate, gender);
  }

  @Override
  public String getPesel() {
    return pesel;
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public LocalDate getBirthDate() {
    return birthDate;
  }

  @Override
  public Gender getGender() {
    return gender;
  }
}
