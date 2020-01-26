package dev.futa.tutorial.pesel.metrics;

import dev.futa.tutorial.pesel.v06.PeselInfo;
import dev.futa.tutorial.pesel.v06.PeselService;

import java.util.function.Consumer;

public class EitherConsumer implements Consumer<String> {

  private final PeselService peselService;

  EitherConsumer(PeselService peselService) {
    this.peselService = peselService;
  }

  @Override
  public void accept(String pesel) {
    final PeselInfo peselInfo = peselService.decodePesel(pesel).get();
  }
}
