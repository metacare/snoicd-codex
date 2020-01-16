package es.weso.snoicd.normalizer.engines;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.normalizer.storage.TempStore;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SnomedNormalizer extends AbstractNormalizer {

    private final static String TERMINOLOGY_NAME = "SNOMED";
    private final static String CODE_KEYWORD = "conceptId";
    private final static String DESCRIPTIONS_KEYWORD = "descriptions";

    private JSONArray arrayOfNodes;

    /**
     * Instantiates a new Abstract normalizer.
     *
     * @param pathToFileToNormalize the path of the file to normalize.
     */
    public SnomedNormalizer(String pathToFileToNormalize) {
        super(pathToFileToNormalize);
    }

    //@Override
    public void run2() {
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

            iteratorConcept.setCode(node.get(CODE_KEYWORD).toString());
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

    @Override
    public void run() {
        JsonParser parser = null;
        Concept auxConcept = null;
        try {
            parser = Json.createParser(new FileInputStream(super.pathToFileToNormalize));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(parser.hasNext()) {
            Event event = parser.next();
            //System.out.println("Event -->" + event);

            if(event == Event.START_OBJECT) {
                auxConcept = new Concept();
                JsonObject obj = parser.getObject();
                //System.out.println("Object -->" + obj);
                //System.out.println(obj.get(CODE_KEYWORD));
                auxConcept.setCode(obj.get(CODE_KEYWORD).toString().toLowerCase());
                JsonArray descriptions = (JsonArray) obj.get(DESCRIPTIONS_KEYWORD);
                for(JsonValue description : descriptions) {
                    //System.out.println(description.asJsonObject().get("term"));
                    auxConcept.getDescriptions().add(
                            description
                                    .asJsonObject()
                                    .get("term")
                                    .toString()
                                    .replace("\"", "")
                    );
                }
                auxConcept.setTerminologyName(TERMINOLOGY_NAME);
                //System.out.println(obj.get(DESCRIPTIONS_KEYWORD));
            }

            /*if(event == Event.KEY_NAME) {
                Concept auxConcept = new Concept();
                //System.out.println(parser.getString() + ":" + parser.getValue());
                auxConcept.setTerminologyName(TERMINOLOGY_NAME);
                switch (parser.getString()) {
                    case CODE_KEYWORD:
                        parser.next();
                        //System.out.println("Code -> " + parser.getString());
                        auxConcept.setCode(parser.getString());
                        break;

                    case DESCRIPTIONS_KEYWORD:
                        parser.next();
                        //System.out.println("Descriptions -> " + parser.getArray());
                        for(JsonValue value: parser.getArray()) {
                            //System.out.println(value.asJsonObject().toString());
                            if(value.asJsonObject().get("languageCode").equals("es")) {
                                //System.out.println(value.asJsonObject().get("languageCode"));
                                auxConcept.getDescriptions().add(value.asJsonObject().get("preferredTerm").toString());
                            }
                        }
                        break;
                }
            }*/
            //System.out.println("------------------");
            if(Objects.nonNull(auxConcept)) {
                //System.out.println(auxConcept);
                TempStore.save(auxConcept.getCode(), auxConcept);
            }
        }
    }
}
