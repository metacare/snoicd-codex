package org.weso.snoicd.examples.suggestions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.weso.snoicd.search.SnoicdSearch;
import org.weso.snoicd.search.SnoicdSearchDefaultImpl;

@SpringBootApplication
public class StartUp {

    public final static SnoicdSearch SEARCH_ENGINE = new SnoicdSearchDefaultImpl(
            "C:\\git\\metacare\\snoicd-codex\\snoicd-search\\conceptIDIndex.json",
            "C:\\git\\metacare\\snoicd-codex\\snoicd-search\\descriptionsIndex.json"
    );

    public static void main(String... args) {
        SpringApplication.run(StartUp.class);
        SEARCH_ENGINE.loadConceptsInMemory();
    }
}
