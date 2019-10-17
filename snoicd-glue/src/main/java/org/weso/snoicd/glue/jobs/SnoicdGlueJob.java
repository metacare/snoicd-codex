package main.java.org.weso.snoicd.glue.jobs;

/**
 * The interface Snoicd glue job.
 */
public interface SnoicdGlueJob extends Runnable {

    /**
     * Checks whether a job is running or not.
     *
     * @return true if the job is running. False otherwise.
     */
    boolean isJobRunning();

    /**
     * Has finished boolean.
     *
     * @return the boolean
     */
    boolean hasFinished();

    /**
     * Checks wheter a job has been started. Notice that a
     * job might not been running but been started previously.
     *
     * @return true if the job has been started. False otherwise.
     */
    boolean hasJobBeenStarted();

    /**
     * Gets the current state of the job.
     *
     * @return the state
     */
    SnoicdGlueJobState getJobState();

    /**
     * Gets job identifier.
     *
     * @return the job identifier
     */
    String getJobIdentifier();

    /**
     * Gets job strategy.
     *
     * @return the job strategy
     */
    SnoicdGlueJobStrategy getJobStrategy();

    /**
     * Gets number of desired cores.
     *
     * @return the number of desired cores
     */
    int getNumberOfDesiredCores();

    /**
     * Gets number of cores.
     *
     * @return the number of cores
     */
    int getNumberOfCores();

    /**
     * Sets number of cores.
     *
     * @param numberOfCores the number of cores
     */
    void setNumberOfCores(int numberOfCores);

    /**
     * Abort job.
     */
    void abortJob();
}
