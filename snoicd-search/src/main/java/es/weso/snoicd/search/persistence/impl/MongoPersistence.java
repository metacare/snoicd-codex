package es.weso.snoicd.search.persistence.impl;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.indexer.types.Index;
import es.weso.snoicd.search.persistence.Persistence;

import java.util.stream.Stream;

public class MongoPersistence implements Persistence {

    @Override
    public void loadIndex(Index index) {

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
