package es.weso.snoicd.search.persistence.impl;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.indexer.types.Index;
import es.weso.snoicd.search.persistence.Persistence;
import io.thewilly.bigtable.BigTable;
import io.thewilly.bigtable.BigTableProducer;
import io.thewilly.bigtable.index.IndexEngine;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static es.weso.snoicd.core.utils.StringUtilsKt.normalize;

public class InMemoryPersistence implements Persistence {

    private BigTable<String, Concept> conceptsIndexedByCode;
    private BigTable<String, Concept> conceptsIndexedByDescription;

    public InMemoryPersistence() {

        // Initialization of the concept index.
        this.conceptsIndexedByCode = new BigTableProducer<String, Concept>().withIndexEngine(IndexEngine.DEFAULT_ENGINE).build();

        // Initialization of the description index.
        this.conceptsIndexedByDescription = new BigTableProducer<String, Concept>().withIndexEngine(IndexEngine.DEFAULT_ENGINE).build();
    }

    @Override
    public void loadIndexes(Index conceptsIndexedByCode, Index conceptsIndexedByDescription) {

        // Loading concepts indexed by code.
        conceptsIndexedByCode.getAllConcepts().forEach(
                (key, value) ->
                        this.conceptsIndexedByCode
                                .getMemoryMap()
                                .put(key, value.collect(Collectors.toSet()))
        );

        // Loading concepts indexed by description.
        conceptsIndexedByDescription.getAllConcepts().forEach(
                (key, value) ->
                        this.conceptsIndexedByDescription
                                .getMemoryMap()
                                .put(key, value.collect(Collectors.toSet()))
        );
    }

    @Override
    public Stream<Concept> findByConceptCode(String conceptCode) {
        return this.conceptsIndexedByCode.find(conceptCode).parallelStream();
    }

    @Override
    public Stream<Concept> findByConceptDescription(String[] words) {
        for (int i = 0; i < words.length; i++) words[i] = normalize(words[i]);

        return this.conceptsIndexedByDescription.findIntersection(words).parallelStream();
    }
}
