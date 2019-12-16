package es.weso.snoicd.normalizer.storage;

import es.weso.snoicd.core.Concept;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class TempStore {

    private static final Map<String, Concept> CONCEPTS = new HashMap<>();

    public static void save(String code, Concept concept) {
        CONCEPTS.put(code, concept);
    }

    public static Optional<Concept> find(String code) {
        Optional<Concept> result;
        Concept stored = CONCEPTS.get(code);
        if( Objects.nonNull(stored))
            result = Optional.of(stored);
        else
            result = Optional.empty();

        return result;
    }

    public static Stream<Concept> stream() {
        return CONCEPTS.values().parallelStream();
    }
}
