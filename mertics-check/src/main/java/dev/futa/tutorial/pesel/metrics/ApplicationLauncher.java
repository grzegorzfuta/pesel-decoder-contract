package dev.futa.tutorial.pesel.metrics;

import dev.futa.tutorial.pesel.v02.PeselService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.google.common.base.Preconditions.checkArgument;

public class ApplicationLauncher {

  public static Logger logger = LoggerFactory.getLogger(ApplicationLauncher.class);

  private FileReader fileReader;
  private PeselService peselService;

  ApplicationLauncher(FileReader fileReader, PeselService peselService) {
    this.fileReader = fileReader;
    this.peselService = peselService;
  }

  public static void main(String[] args) {

    final Arguments arguments = extractArguments(args);

    final FileReader fileReader = new FileReader(arguments.getFileName());

    final MetricsRunner metricsRunner = new MetricsRunner(new PeselSupplier(fileReader));
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
}
