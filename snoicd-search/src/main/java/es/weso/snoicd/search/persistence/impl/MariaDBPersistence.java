package es.weso.snoicd.search.persistence.impl;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.indexer.types.Index;
import es.weso.snoicd.search.persistence.Persistence;

import java.util.stream.Stream;

public class MariaDBPersistence implements Persistence {

    @Override
    public void loadIndexes(Index conceptsIndexedByCode, Index conceptsIndexedByDescription) {

    }

    @Override
    public Stream<Concept> findByConceptCode(String conceptCode) {
        return null;
    }

    @Override
    public Stream<Concept> findByConceptDescription(String[] words) {
        return null;
    }
}
