package main.java.org.weso.snoicd.glue.jobs;

/**
 * The enum Snoicd glue job strategy.
 */
public enum SnoicdGlueJobStrategy {
    /**
     * Any cores snoicd glue job strategy.
     */
    ANY_CORES,
    /**
     * Wait for free cores snoicd glue job strategy.
     */
    WAIT_FOR_FREE_CORES
}
