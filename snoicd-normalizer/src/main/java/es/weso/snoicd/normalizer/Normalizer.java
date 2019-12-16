package es.weso.snoicd.normalizer;

import es.weso.snoicd.core.Concept;

import java.util.stream.Stream;

@FunctionalInterface
public interface Normalizer {

    /**
     * Gets the normalized concepts after the normalization of all
     * registered normalizers.
     *
     * @return an stream containing all the normalized concepts.
     */
    Stream<Concept> getNormalizedConceptsStream();
}
