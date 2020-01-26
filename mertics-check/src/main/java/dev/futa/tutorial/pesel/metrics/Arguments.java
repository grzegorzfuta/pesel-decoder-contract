package dev.futa.tutorial.pesel.metrics;

class Arguments {

  private final String fileName;
  private final String decoderVersion;

  Arguments(String fileName, String decoderVersion) {
    this.fileName = fileName;
    this.decoderVersion = decoderVersion;
  }

  String getFileName() {
    return fileName;
  }

  String getDecoderVersion() {
    return decoderVersion;
  }
}
