package dev.futa.tutorial.pesel.v01;

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

    String getPesel() {
        return pesel;
    }

    LocalDate getBirthDate() {
        return birthDate;
    }

    Gender getGender() {
        return gender;
    }
}
