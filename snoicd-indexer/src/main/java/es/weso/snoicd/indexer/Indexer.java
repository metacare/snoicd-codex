package es.weso.snoicd.indexer;

import es.weso.snoicd.indexer.types.Index;

import java.util.List;

/** The interface Indexer. */
public interface Indexer {

  /**
   * Gets indexes.
   *
   * @return the indexes
   */
  List<Index> getIndexes();
}
