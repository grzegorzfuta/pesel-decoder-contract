package dev.futa.tutorial.pesel.metrics;

import dev.futa.tutorial.pesel.v01.PeselInfo;
import dev.futa.tutorial.pesel.v01.PeselService;

import java.util.function.Consumer;

public class ObjectOrNullConsumer implements Consumer<String> {

    private final PeselService peselService;

    ObjectOrNullConsumer(PeselService peselService) {
        this.peselService = peselService;
    }

    @Override
    public void accept(String pesel) {
        final PeselInfo peselInfo = peselService.decodePesel(pesel);
        if (peselInfo != null) {

        }
    }
}
