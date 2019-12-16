package es.weso.snoicd.normalizer.engines;

/** The type Abstract normalizer. */
public abstract class AbstractNormalizer implements Normalizable {

    protected final String pathToFileToNormalize;

  /**
   * Instantiates a new Abstract normalizer.
   *
   * @param pathToFileToNormalize the path of the file to normalize.
   */
  public AbstractNormalizer(final String pathToFileToNormalize) {
        this.pathToFileToNormalize = pathToFileToNormalize;
    }
}
