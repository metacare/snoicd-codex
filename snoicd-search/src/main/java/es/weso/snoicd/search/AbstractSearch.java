package es.weso.snoicd.search;

import es.weso.snoicd.search.persistence.Persistence;

/** The type Abstract search. */
public abstract class AbstractSearch implements Search {

    protected final Persistence persistence;

  /**
   * Instantiates a new Abstract search.
   *
   * @param persistence the persistence
   */
  public AbstractSearch(Persistence persistence) {
        this.persistence = persistence;

        // Loading the indexes.
        //this.persistence.loadIndexes();
    }
}
