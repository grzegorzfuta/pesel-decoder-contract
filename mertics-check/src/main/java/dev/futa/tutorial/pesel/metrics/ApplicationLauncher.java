package dev.futa.tutorial.pesel.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkArgument;

public class ApplicationLauncher {

  public static Logger logger = LoggerFactory.getLogger(ApplicationLauncher.class);

  public static void main(String[] args) {

    final Arguments arguments = extractArguments(args);
    final PeselSupplier peselSupplier = PeselSupplier.createSupplier(arguments.getFileName());

    final MetricsRunner metricsRunner =
        new MetricsRunner(peselSupplier, createConsumer(arguments.getDecoderVersion()));

    metricsRunner.runMetricsTest();
  }

  private static Arguments extractArguments(String[] args) {
    checkArgument(
        args.length > 1,
        "You should pass two parameters\n\t(1) filename with pesels\n\t(2) decoder version, e.g. V02");

    final String peselsFilename = args[0];
    final String decodeMethod = args[1];

    checkArgument(
        Files.isReadable(Paths.get(peselsFilename)), "Can not read from file " + peselsFilename);

    return new Arguments(peselsFilename, decodeMethod);
  }

  private static Consumer<String> createConsumer(String version) {
    switch (version.toLowerCase()) {
      case "v01":
        return new ObjectOrNullConsumer(new dev.futa.tutorial.pesel.v01.PeselService());
      case "v02":
        return new ObjectOrExceptionConsumer(new dev.futa.tutorial.pesel.v02.PeselService());
      default:
        throw new IllegalStateException("Unknown ");
    }
  }
}
