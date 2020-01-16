package es.weso.snoicd.search.persistence.impl;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.indexer.types.Index;
import es.weso.snoicd.search.persistence.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class InMemmoryPersistence implements Persistence {

    private Map<String, Concept> concepts = new HashMap<>();

    @Override
    public void loadIndex(Index index) {

    }

    @Override
    public Stream<Concept> findByConceptCode(String conceptCode) {
        return Stream.of(new Concept("1234", "", new ArrayList<>(), new ArrayList<>()));
    }

    @Override
    public Stream<Concept> findByConceptDescription(String[] words) {
        return Stream.of(new Concept("1234", "", new ArrayList<>(), new ArrayList<>()));
    }
}
