package dev.futa.tutorial.pesel.metrics;

import dev.futa.tutorial.pesel.v03.PeselInfo;
import dev.futa.tutorial.pesel.v03.PeselService;

import java.util.function.Consumer;

public class ObjectWithSuccessConsumer implements Consumer<String> {

    private final PeselService peselService;

    ObjectWithSuccessConsumer(PeselService peselService) {
        this.peselService = peselService;
    }

    @Override
    public void accept(String pesel) {
        final PeselInfo peselInfo = peselService.decodePesel(pesel);
        if (peselInfo.isValid()) {

        }
    }
}
