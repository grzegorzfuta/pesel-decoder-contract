package dev.futa.tutorial.pesel.metrics;

import dev.futa.tutorial.pesel.v04.PeselInfo;
import dev.futa.tutorial.pesel.v04.PeselService;

import java.util.function.Consumer;

public class NullObjectPatternConsumer implements Consumer<String> {

    private final PeselService peselService;

    NullObjectPatternConsumer(PeselService peselService) {
        this.peselService = peselService;
    }

    @Override
    public void accept(String pesel) {
        final PeselInfo peselInfo = peselService.decodePesel(pesel);
        if (peselInfo.isValid()) {

        }
    }
}
