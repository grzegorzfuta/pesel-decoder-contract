package dev.futa.tutorial.pesel.metrics;

import dev.futa.tutorial.pesel.v05.PeselInfo;
import dev.futa.tutorial.pesel.v05.PeselService;

import java.util.function.Consumer;

public class OptionalConsumer implements Consumer<String> {

    private final PeselService peselService;

    OptionalConsumer(PeselService peselService) {
        this.peselService = peselService;
    }

    @Override
    public void accept(String pesel) {
        final PeselInfo peselInfo = peselService.decodePesel(pesel).get();

    }
}
