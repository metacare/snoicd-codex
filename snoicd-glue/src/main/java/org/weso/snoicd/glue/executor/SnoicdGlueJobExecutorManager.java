package main.java.org.weso.snoicd.glue.executor;

import main.java.org.weso.snoicd.glue.jobs.SnoicdGlueJob;
import main.java.org.weso.snoicd.glue.jobs.SnoicdGlueJobState;
import main.java.org.weso.snoicd.glue.jobs.SnoicdGlueJobStrategy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The type Snoicd job executor manager.
 */
public class SnoicdGlueJobExecutorManager implements Runnable {

    private static final int TOTAL_NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static int NUMBER_OF_FREE_CORES = TOTAL_NUMBER_OF_CORES;
    private static Thread EXECUTOR_MANAGER_THREAD;

    private ConcurrentMap<String, SnoicdGlueJob> jobsPool;
    private boolean stop = false;

    /**
     * Instantiates a new Snoicd job executor manager.
     */
    public SnoicdGlueJobExecutorManager() {
        jobsPool = new ConcurrentHashMap<>();
    }

    /**
     * Run all jobs.
     */
    public void runAllJobs() {
        jobsPool.forEach((identifier, snoicdGlueJob) -> runJob(identifier));
    }

    /**
     * Run job.
     *
     * @param identifier the identifier
     */
    public boolean runJob(String identifier) {

        SnoicdGlueJob job = jobsPool.get(identifier);

        if(job.getNumberOfDesiredCores() > TOTAL_NUMBER_OF_CORES && job.getJobStrategy() == SnoicdGlueJobStrategy.WAIT_FOR_FREE_CORES) {
            removeJob(identifier);
            throw new IllegalStateException("The process requires more cores than the available in the system");
        }

        if(job.isJobRunning()) {
            throw new IllegalStateException("The job is already running");
        }

        if(job.getNumberOfCores() <= NUMBER_OF_FREE_CORES) {
            job.run();
            NUMBER_OF_FREE_CORES = NUMBER_OF_FREE_CORES - job.getNumberOfCores();
            return true;
        } else {
            if(job.getJobStrategy() == SnoicdGlueJobStrategy.ANY_CORES) {
                job.setNumberOfCores(NUMBER_OF_FREE_CORES);
                return this.runJob(job.getJobIdentifier());
            } else {
                updateJobsPool();
                return false;
            }
        }
    }

    private void updateJobsPool() {
        jobsPool.forEach((identifier, snoicdGlueJob) -> {
            if(snoicdGlueJob.hasFinished()) {
                removeJob(identifier);
                NUMBER_OF_FREE_CORES = NUMBER_OF_FREE_CORES + snoicdGlueJob.getNumberOfCores();
            }
        });
    }

    /**
     * Add job.
     *
     * @param jobIdentifier the job identifier
     * @param jobToAdd      the job to add
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void addJob(String jobIdentifier, SnoicdGlueJob jobToAdd) throws IllegalArgumentException{
        if(!jobsPool.containsKey(jobIdentifier)) {
            jobsPool.put(jobIdentifier, jobToAdd);
        } else {
            throw new IllegalArgumentException("The job submitted already exists in the pool of jobs");
        }
    }

    /**
     * Remove job.
     *
     * @param jobIdentifier the job identifier
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void removeJob(String jobIdentifier) throws IllegalArgumentException {
        if(jobsPool.containsKey(jobIdentifier)) {
            jobsPool.remove(jobIdentifier);
        } else {
            throw new IllegalArgumentException("The identifier submmited does not exists in the jobs pool");
        }
    }

    /**
     * Wait for job to finish.
     *
     * @param identifier the identifier
     */
    public synchronized void waitForJobToFinish(String identifier) {
        if(jobsPool.containsKey(identifier)) {
            while(!jobsPool.get(identifier).hasFinished()) {
                try {
                    wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Wait for all jobs to finish.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void waitForAllJobsToFinish() {
        jobsPool.forEach((identifier, thread) -> waitForJobToFinish(identifier));
    }

    /**
     * Gets job.
     *
     * @param identifier the identifier
     * @return the job
     */
    public SnoicdGlueJob getJob(String identifier) {
        return this.jobsPool.get(identifier);
    }

    /**
     * Abort job.
     *
     * @param identifier the identifier
     */
    public void abortJob(String identifier) {
        if(jobsPool.containsKey(identifier)) {
            jobsPool.get(identifier).getJobState().setState(SnoicdGlueJobState.State.STOPPING);
            jobsPool.get(identifier).abortJob();
            jobsPool.get(identifier).getJobState().setState(SnoicdGlueJobState.State.READY);
        }
    }

    /**
     * Abort all jobs.
     */
    public void abortAllJobs() {
        jobsPool.forEach((identifier, snoicdGlueJob) -> snoicdGlueJob.abortJob());
    }

    @Override
    public void run() {
        EXECUTOR_MANAGER_THREAD = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!stop && !jobsPool.isEmpty()) {
                    jobsPool.forEach((jobIdentifier, snoicdGlueJob) -> {
                        while(runJob(jobIdentifier) == false) {
                            runJob(jobIdentifier);
                        }
                    });
                }
            }
        });
    }
}
