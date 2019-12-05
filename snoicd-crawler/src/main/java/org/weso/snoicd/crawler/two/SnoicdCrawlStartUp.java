package org.weso.snoicd.crawler.two;

import com.google.gson.Gson;
import org.weso.snoicd.crawler.StartUp;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SnoicdCrawlStartUp {

    private static Thread icd9Crawl, icd10Crawl, snomedCrawl, snomedIcd9Link, snomedIcd10Link;

    private final static String ICD_9_JOBNAME = "icd9Job";
    private final static String ICD_10_JOBNAME = "icd10Job";
    private final static String SNOMED_JOBNAME = "snomedJob";

    private final static String ICD_9_INPUT_FILE = "C:\\git\\metacare\\snoicd-codex\\data\\icd9-en.jso";
    private final static String ICD_10_INPUT_FILE = "C:\\git\\metacare\\snoicd-codex\\data\\icd-10-en.json";
    private final static String SNOMED_INPUT_FILE = "C:\\git\\metacare\\snoicd-codex\\data\\concepts.json";

    private final static String OUTPUT_FILE = "snoicd-crawler/concepts-test.json";

    public static void main(String... args) throws IOException {

        // Initiates the crawl process.
        initCrawl();

        // Waits till all crawlers have finished.
        joinCrawl();

        // Links all the terminologies.
        linkTerminologies();

        // Writes the result in the output file.
        writeOutpoutFile();
    }

    private static void initCrawl() {
        // load and start icd9 crawling job
        icd9Crawl = new Snoicd9CrawlJob(
                ICD_9_JOBNAME,
                ICD_9_INPUT_FILE);

        icd9Crawl.start();

        // load and start icd10 crawling job
        icd10Crawl = new Snoicd10CrawlJob(
                ICD_10_JOBNAME,
                ICD_10_INPUT_FILE);

        icd10Crawl.start();

        // load and start snomed crawling job
        snomedCrawl = new SnoicdSnomedCrawlJob(
                SNOMED_JOBNAME,
                SNOMED_INPUT_FILE);

        snomedCrawl.start();
    }

    private static void joinCrawl() {
        // Join all the crawl jobs.
        try {
            icd9Crawl.join();
            icd10Crawl.join();
            snomedCrawl.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void linkTerminologies() {
        // not implemented yet
        throw new NotImplementedException();
    }

    private static void writeOutpoutFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE));
        writer.append("[");
        StartUp._nodes.forEach((k, v) -> {
            try {
                writer.append(new Gson().toJson(v));
                writer.append(",\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.append("]");
        writer.close();
    }
}
