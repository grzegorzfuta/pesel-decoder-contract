package dev.futa.tutorial.pesel.v04;

import dev.futa.tutorial.pesel.Gender;

import java.time.LocalDate;

public interface PeselInfo {
  boolean isValid();

  String getPesel();

  LocalDate getBirthDate();

  Gender getGender();
}
