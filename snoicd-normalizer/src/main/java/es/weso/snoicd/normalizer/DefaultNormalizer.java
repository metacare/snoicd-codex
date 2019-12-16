package es.weso.snoicd.normalizer;

import es.weso.snoicd.core.Concept;
import es.weso.snoicd.normalizer.engines.ICD10Normalizer;
import es.weso.snoicd.normalizer.engines.ICD9Normalizer;
import es.weso.snoicd.normalizer.engines.SnomedNormalizer;
import es.weso.snoicd.normalizer.storage.TempStore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class DefaultNormalizer implements Normalizer {

    private final Runnable[] normalizers = new Runnable[3];

    public DefaultNormalizer(final String snomedInpuFile,
                             final String icd9InputFile,
                             final String icd10InputFile,
                             final String outputFile) {
        normalizers[0] = new SnomedNormalizer(snomedInpuFile);
        normalizers[1] = new ICD9Normalizer(icd9InputFile);
        normalizers[2] = new ICD10Normalizer(icd10InputFile);
    }

    @Override
    public Stream<Concept> getNormalizedConceptsStream() {
        normalize();
        return TempStore.stream();
    }

    private void normalize() {
        List<Thread> threads = new ArrayList(3);
        /*for(Runnable normalizer : normalizers) {
            Thread tmpThread = new Thread(normalizer);
            threads.add(tmpThread);
            tmpThread.start();
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        System.out.println("NORMALIZING SNOMED");
        normalizers[0].run();
        System.out.println("NORMALIZING ICD9");
        normalizers[1].run();
        System.out.println("NORMALIZING ICD10");
        normalizers[2].run();
        System.out.println("NORMALIZING FINISHED");
    }
}
