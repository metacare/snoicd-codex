package es.weso.snoicd.linker;

import es.weso.snoicd.core.Concept;

import java.util.stream.Stream;

public interface Linker {

    /**
     * Gets the linked concepts as a parallel stream.
     *
     * @return the linked concepts as a parallel stream.
     */
    Stream<Concept> getLinkedConceptsStream();
}
