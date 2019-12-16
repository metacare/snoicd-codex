package es.weso.snoicd.normalizer.engines;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.normalizer.storage.TempStore;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class ICD9Normalizer extends AbstractNormalizer {

    private final static String TERMINOLOGY_NAME = "ICD_9";
    private final static String CODE_KEYWORK = "PROCEDURE CODE";
    private final static String DESCRIPTIONS_KEYWORD = "LONG DESCRIPTION";

    private JSONArray arrayOfNodes;

    /**
     * Instantiates a new Abstract normalizer.
     *
     * @param pathToFileToNormalize the path of the file to normalize.
     */
    public ICD9Normalizer(String pathToFileToNormalize) {
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

            iteratorConcept.getDescriptions().add(node.get(DESCRIPTIONS_KEYWORD).toString());

            TempStore.save(iteratorConcept.getCode(), iteratorConcept);
        }
    }
}
