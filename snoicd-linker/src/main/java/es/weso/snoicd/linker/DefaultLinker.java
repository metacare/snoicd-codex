package es.weso.snoicd.linker;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.core.SimpleConcept;
import es.weso.snoicd.normalizer.DefaultNormalizer;
import es.weso.snoicd.normalizer.Normalizer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultLinker implements Linker {

    private static final String SNOMED_CODE = "SNOMED_CID";
    private static final String ICD9_CODE = "ICD_CODE";

    private static final String SNOMED2_CODE = "conceptId";
    private static final String ICD10_CODE = "mapTarget";

    private final String snomedIcd9LinkerFile;
    private final String snomedIcd10LinkerFile;
    private Map<String, Concept> concepts;

    private Normalizer normalizer;
    private JSONArray arrayOfICD9ConceptsToLink;
    private JSONArray arrayOfICD10ConceptsToLink;

    public DefaultLinker(
            final String snomedFile,
            final String icd9File,
            final String icd10File,
            final String snomedIcd9LinkerFile,
            final String snomedIcd10LinkerFile
    ) throws IOException, ParseException {
        this.snomedIcd9LinkerFile = snomedIcd9LinkerFile;
        this.snomedIcd10LinkerFile = snomedIcd10LinkerFile;
        concepts = new HashMap<>();

        System.out.println("NORMALIZING FILES");

        // Init the normalizer instance
        normalizer = new DefaultNormalizer(snomedFile, icd9File, icd10File, null);

        // Add the normalized concepts to a map to start the link process.
        /*normalizer.getNormalizedConceptsStream()
                .forEach(
                        concept -> {
                            concepts.put(concept.getCode(), concept);
                            //System.out.println("Processing stream -> " + concepts.size());
                        }
                );*/

        concepts = normalizer.getNormalizedConceptsStream()
                .collect(
                        Collectors.toMap(Concept::getCode, Function.identity())
                );



        System.out.println("FILES NORMALIZED");

        // Initialization of the arrays
        System.out.println("LINKING ICD9");
        linkConcepts(SNOMED_CODE, ICD9_CODE, new FileReader(this.snomedIcd9LinkerFile));

        System.out.println("LINKING ICD10");
        //linkConcepts(SNOMED2_CODE, ICD10_CODE, new FileReader(this.snomedIcd10LinkerFile));
    }

    @Override
    public Stream<Concept> getLinkedConceptsStream() {
        return concepts.values().parallelStream();
    }

    private void linkConcepts(String snomedCodeKeyword, String icdCodeKeyword, FileReader file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(file);

        arrayOfICD9ConceptsToLink = (JSONArray) obj;

        Iterator nodesIterator = arrayOfICD9ConceptsToLink.iterator();

        while(nodesIterator.hasNext()) {
            JSONObject node = (JSONObject) nodesIterator.next();

            Object snomedCodeObj = node.get(snomedCodeKeyword);
            Object icdCodeObj = node.get(icdCodeKeyword);

            if(Objects.isNull(snomedCodeObj) || Objects.isNull(icdCodeObj))
                continue;

            String snomedCode = snomedCodeObj.toString();
            String icdCode = icdCodeObj.toString();

            Concept snomedConcept = concepts.get(snomedCode);
            Concept icdConcept = concepts.get(icdCode);

            //System.out.println(snomedCode + " --> " + snomedConcept);
            //System.out.println(icdCode + " --> " + icdConcept);

            int relation = 0;

            if(Objects.nonNull(snomedCode)) {
                try{
                    concepts.get(snomedCode).getRelatedCodes().add(
                            new SimpleConcept(
                                    icdConcept.getCode(),
                                    icdConcept.getDescriptions(),
                                    icdConcept.getTerminologyName()
                            )
                    );
                    relation++;
                } catch (NullPointerException ex) {
                    // Log the error
                }
            }

            if(Objects.nonNull(icdConcept)){
                try {
                    concepts.get(icdCode).getRelatedCodes().add(
                            new SimpleConcept(
                                    snomedConcept.getCode(),
                                    snomedConcept.getDescriptions(),
                                    snomedConcept.getTerminologyName()
                            )
                    );
                    relation++;
                } catch (NullPointerException ex) {
                    //log error
                }
            }

            if(relation == 2) {
                System.err.println("Relation added...");
            }
        }
    }
}
