package es.weso.snoicd.normalizer.engines;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.normalizer.storage.TempStore;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SnomedNormalizer extends AbstractNormalizer {

    private final static String TERMINOLOGY_NAME = "SNOMED";
    private final static String CODE_KEYWORK = "code";
    private final static String DESCRIPTIONS_KEYWORD = "descriptions";

    private JSONArray arrayOfNodes;

    /**
     * Instantiates a new Abstract normalizer.
     *
     * @param pathToFileToNormalize the path of the file to normalize.
     * @param outputPath            the output path.
     */
    public SnomedNormalizer(String pathToFileToNormalize) {
        super(pathToFileToNormalize);
    }

    @Override
    public void run() {
        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            obj = parser.parse(new FileReader(super.pathToFileToNormalize));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        arrayOfNodes = (JSONArray) obj;

        Iterator nodesIterator = arrayOfNodes.iterator();
        Concept iteratorConcept;

        while (nodesIterator.hasNext()) {
            JSONObject node = (JSONObject) nodesIterator.next();

            iteratorConcept = new Concept();

            iteratorConcept.setCode(node.get(CODE_KEYWORK).toString());
            iteratorConcept.setTerminologyName(TERMINOLOGY_NAME);

            List<String> descriptions = (ArrayList) node.get(DESCRIPTIONS_KEYWORD);

            if( Objects.nonNull(descriptions) ) {
                Concept finalIteratorConcept = iteratorConcept;
                descriptions.forEach(
                        description -> finalIteratorConcept.getDescriptions().add(description)
                );
                iteratorConcept = finalIteratorConcept;
            }

            TempStore.save(iteratorConcept.getCode(), iteratorConcept);
        }
    }
}
