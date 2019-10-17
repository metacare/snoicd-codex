package main.java.org.weso.snoicd.glue.jobs;

/**
 * The type Snoicd glue abstract job.
 */
public abstract class SnoicdGlueAbstractJob implements SnoicdGlueJob {

    private static final int DEFAULT_NUMBER_OF_DESIRED_CORES = 1;

    private final SnoicdGlueJobState state = new SnoicdGlueJobState();
    private final SnoicdGlueJobStrategy strategy;
    private final String jobIdentifier;
    private final int numberOfDesiredCores;
    private boolean isJobRunning = false;
    private boolean hasJoobBeenStarted = false;
    private boolean hasFinished = false;
    private int numberOfCores;

    /**
     * Instantiates a new Snoicd glue abstract job.
     *
     * @param jobIdentifier the job identifier
     */
    public SnoicdGlueAbstractJob(String jobIdentifier) {
        this(jobIdentifier, DEFAULT_NUMBER_OF_DESIRED_CORES);
    }

    /**
     * Instantiates a new Snoicd glue abstract job.
     *
     * @param jobIdentifier        the job identifier
     * @param numberOfDesiredCores the number of desired cores
     */
    public SnoicdGlueAbstractJob(String jobIdentifier, int numberOfDesiredCores) {
        this(jobIdentifier, numberOfDesiredCores, SnoicdGlueJobStrategy.ANY_CORES);
    }

    /**
     * Instantiates a new Snoicd glue abstract job.
     *
     * @param jobIdentifier        the job identifier
     * @param numberOfDesiredCores the number of desired cores
     * @param jobStrategy          the job strategy
     */
    public SnoicdGlueAbstractJob(String jobIdentifier, int numberOfDesiredCores, SnoicdGlueJobStrategy jobStrategy) {
        this.jobIdentifier = jobIdentifier;
        this.numberOfDesiredCores = numberOfDesiredCores;
        this.strategy = jobStrategy;
        this.numberOfCores = this.numberOfDesiredCores;
    }

    @Override
    public boolean isJobRunning() {
        return this.isJobRunning;
    }

    @Override
    public boolean hasJobBeenStarted() {
        return this.hasJoobBeenStarted;
    }

    @Override
    public boolean hasFinished() {
        return this.hasFinished;
    }

    @Override
    public SnoicdGlueJobState getJobState() {
        return this.state;
    }

    @Override
    public String getJobIdentifier() {
        return this.jobIdentifier;
    }

    @Override
    public int getNumberOfDesiredCores() { return this.numberOfDesiredCores; }

    @Override
    public int getNumberOfCores() { return this.numberOfCores; }

    @Override
    public void setNumberOfCores(int numberOfCores) {

        // Check that the new number of cores is equals or greater to one.
        if(numberOfCores < DEFAULT_NUMBER_OF_DESIRED_CORES)
            throw new IllegalArgumentException("The number of cores cannot be zero or negative");

        // Depending on the strategy of the job the operation will be done or not.
        if(strategy == SnoicdGlueJobStrategy.WAIT_FOR_FREE_CORES) {
            throw new IllegalStateException("Cannot change the number of cores for this job strategy.");
        } else {
            this.numberOfCores = numberOfDesiredCores;
        }

    }

    @Override
    public SnoicdGlueJobStrategy getJobStrategy() {
        return this.strategy;
    }

    @Override
    public void run() {
        this.hasJoobBeenStarted = true;
        this.isJobRunning = true;
        this.state.setState(SnoicdGlueJobState.State.RUNNING);
        this.executeJob();
        this.state.setState(SnoicdGlueJobState.State.STOPPING);
        this.isJobRunning = false;
        this.hasFinished = true;
        this.state.setState(SnoicdGlueJobState.State.READY);
    }

    protected abstract void executeJob();
}
