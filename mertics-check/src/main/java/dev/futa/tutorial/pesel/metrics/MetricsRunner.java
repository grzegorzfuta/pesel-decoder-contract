package dev.futa.tutorial.pesel.metrics;

import dev.futa.tutorial.pesel.v02.PeselDecodingException;
import dev.futa.tutorial.pesel.v02.PeselInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class MetricsRunner {

  public static Logger logger = LoggerFactory.getLogger(MetricsRunner.class);

  private final PeselSupplier peselSupplier;
  private final Runtime runtime = Runtime.getRuntime();

  MetricsRunner(PeselSupplier peselSupplier) {
    this.peselSupplier = peselSupplier;
  }

  void runMetricsTest() {

    long[][] memUsages = new long[100][2716472 / 1000];
    long[] times = new long[100];

    System.out.println("" + usedMem());

    for (int r = 0; r < 100; r++) {
      long t0 = System.currentTimeMillis();
      for (int i = 0; i < peselSupplier.getTotalCount(); i++) {
        try {
          final PeselInfo peselInfo = peselService.decodePesel(peselSupplier.getNextPesel(r, i));
        } catch (PeselDecodingException decodingException) {
          logger.info(decodingException.getMessage());
        }
      }

      times[r] = System.currentTimeMillis() - t0;
    }
  }

  private long usedMem() {
    return (runtime.maxMemory() - runtime.freeMemory()) / (1024);
  }
}
