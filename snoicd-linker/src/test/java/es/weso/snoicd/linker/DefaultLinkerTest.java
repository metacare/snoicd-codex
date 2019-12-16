package es.weso.snoicd.linker;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

public class DefaultLinkerTest {

    @Test
    public void shitTest() throws IOException, ParseException {
        new DefaultLinker(
                "../data/snomed.json",
                "../data/icd9-en.json",
                "../data/icd-10-en.json",
                "../data/snomed-icd9-map.json",
                "../data/snomed-icd10-map.json");
    }
}
