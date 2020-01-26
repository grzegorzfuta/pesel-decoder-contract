package dev.futa.tutorial.pesel.metrics;

import dev.futa.tutorial.pesel.v02.PeselDecodingException;
import dev.futa.tutorial.pesel.v02.PeselInfo;
import dev.futa.tutorial.pesel.v02.PeselService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class ObjectOrExceptionConsumer implements Consumer<String> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectOrExceptionConsumer.class);
    private final PeselService peselService;

    ObjectOrExceptionConsumer(PeselService peselService) {
        this.peselService = peselService;
    }

    @Override
    public void accept(String pesel) {
        try {
            final PeselInfo peselInfo = peselService.decodePesel(pesel);

        } catch (PeselDecodingException e) {
            logger.info("Caught PESEL decoding exception for: " + pesel, e);
        }
    }
}
