package es.weso.snoicd.indexer.types;

import es.weso.snoicd.core.Concept;

import java.util.Map;
import java.util.stream.Stream;

/** The interface Index. */
public interface Index {

  /**
   * Gets concept indexed at.
   *
   * @param code the code
   * @return the concept indexed at
   */
  Stream<Concept> getConceptIndexedAt(String code);

  /**
   * Index concept.
   *
   * @param concept the concept
   */
  void indexConcept(Concept concept);

  /**
   * Gets all concepts.
   *
   * @return the all concepts
   */
  Map<String, Stream<Concept>> getAllConcepts();
}
