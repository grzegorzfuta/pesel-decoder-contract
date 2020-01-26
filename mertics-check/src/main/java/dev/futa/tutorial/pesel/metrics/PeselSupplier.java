package dev.futa.tutorial.pesel.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class PeselSupplier {

  private static final Logger logger = LoggerFactory.getLogger(PeselSupplier.class);

  private final String filename;
  private List<String> pesels;

  private PeselSupplier(String filename) {
    this.filename = filename;
  }

  static PeselSupplier createSupplier(String filename) {
    final PeselSupplier peselSupplier = new PeselSupplier(filename);
    peselSupplier.loadPesels();
    return peselSupplier;
  }

  private void loadPesels() {
    Path path = Paths.get(filename);
    try {
      pesels = Files.readAllLines(path);
    } catch (IOException e) {
      logger.error("File " + filename + " is not found", e);
      throw new IllegalStateException("File " + filename + " is not found", e);
    }
  }

  String getNextPesel(int repeat, int index) {
    return pesels.get(index);
  }

  int getTotalCount() {
    return pesels.size();
  }
}
