package dev.futa.tutorial.pesel.v04;

import dev.futa.tutorial.pesel.Gender;

import java.time.LocalDate;

public class NullPeselInfo implements PeselInfo {

  private final String pesel;

  private NullPeselInfo(final String pesel) {
    this.pesel = pesel;
  }

  public static PeselInfo of(String pesel) {
    return new NullPeselInfo(pesel);
  }

  @Override
  public boolean isValid() {
    return false;
  }

  @Override
  public String getPesel() {
    return pesel;
  }

  @Override
  public LocalDate getBirthDate() {
    return null;
  }

  @Override
  public Gender getGender() {
    return null;
  }
}
