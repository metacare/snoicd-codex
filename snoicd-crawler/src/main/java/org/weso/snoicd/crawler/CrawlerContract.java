package org.weso.snoicd.crawler;

public interface CrawlerContract {

    /**
     * STEP ONE.
     * <p>
     * Crawls the snomed master file and extracts the concepts.
     */
    public void crawlSNOMED();

    /**
     * STEP TWO.
     * <p>
     * Crawls the icd9 master file and extracts the concepts.
     */
    public void crawlICD9();

    /**
     * STEP THREE.
     * <p>
     * Crawls the icd10 master file and extracts the concepts.
     */
    public void crawlICD10();

    /**
     * STEP FOUR.
     * <p>
     * Links the snomed concepts with the icd-9 ones
     */
    public void linkSNOMED_ICD9();

    /**
     * STEP FIVE.
     * <p>
     * Links the snomed concepts with the icd-10 ones
     */
    public void linkSNOMED_ICD10();

    /**
     * STEP SIX.
     * <p>
     * Exports the knowledge base to a file.
     */
    public void exportKnowledgeBase();

}
