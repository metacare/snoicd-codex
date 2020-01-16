package es.weso.snoicd.search.persistence;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.indexer.types.Index;

import java.util.stream.Stream;

/** The interface Persistence. */
public interface Persistence {


  void loadIndexes(Index conceptsIndexedByCode, Index conceptsIndexedByDescription);

  /**
   * Find by concept code stream.
   *
   * @param conceptCode the concept code
   * @return the stream
   */
  Stream<Concept> findByConceptCode(String conceptCode);

  /**
   * Find by concept description stream.
   *
   * @param words the words
   * @return the stream
   */
  Stream<Concept> findByConceptDescription(String[] words);
}
