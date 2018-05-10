package hw1;

public class Processor
{
    // The current 'time' of the simulation
    public int time;

    // This queue is not used by the scheduler.  This holds the list of all the
    // future jobs.
    private Queue allJobQueue;

    // Queue holding new jobs that are being added to the simulation this cycle:
    public Queue newJobQueue;

    // Queue holding the one job that is being processed by the CPU
    public Queue currentJob;

    // Queue holding jobs that are ready to run
    public Queue readyQueue;

    // Queue holding jobs that are blocked for IO
    public Queue blockedQueue;

    // Queue holding jobs that are no longer blocked
    public Queue unblockedQueue;

    // Queue holding jobs that are done
    public Queue doneQueue;

    // The scheduler to be used to move jobs through the processor
    public Scheduler scheduler;
    public String _algName;

    public Processor()
    {
        time = 0;
        allJobQueue = new Queue();
        newJobQueue = new Queue();
        currentJob = new Queue();
        readyQueue = new Queue();
        blockedQueue = new Queue();
        unblockedQueue = new Queue();
        doneQueue = new Queue();
        scheduler = null;
    }

    // After initializing the processor, when the scheduler is created, it
    // will call this, linking the scheduler to the processor.
    public void setScheduler(Scheduler theScheduler)
    {
        scheduler = theScheduler;
        _algName = scheduler.algName();
    }

    // This function is also implemented in the sub-class
    public String algName() { return _algName; }

    // Add a new job to the all-job queue.  These jobs should come in order,
    // so they are automatically added to the end of the allJobsQueue.
    public Job addJob(int id, int startTime, int length, int numBlocks, int priority, int answerEndTime)
    {
        Job job = new Job(id, startTime, length, numBlocks, priority, answerEndTime);
        job.enqueueEnd(allJobQueue);
        return job;
    }

    public void addJob(int id, int startTime, int length, String blocks, int priority, int answerEndTime)
    {
        Job job = new Job(id, startTime, length, blocks, priority, answerEndTime);
        job.enqueueEnd(allJobQueue);
    }

    // This function is called once the Processor and Scheduler are set up, and
    // all the jobs are added to allJobQueue.  It runs the simulation.
    public void runSim()
    {
        for (time = 0; time < 100000; time++)
        {
            Job job;

            // If every queue (except doneQueue) is empty, we are done running the simulation
            if (allJobQueue.empty() && newJobQueue.empty() && currentJob.empty() && readyQueue.empty() && blockedQueue.empty() && unblockedQueue.empty())
            {
                break;
            }

            // See if any tasks should be moved from the allJobQueue to the newJobQueue
            while (true)
            {
                job = allJobQueue.first();
                if (job == null)
                {
                    break;
                }
                if (job.startTime() > time)
                {
                    // Not ready to schedule this (or anyone else!) yet
                    break;
                }
                job.dequeue();
                job.enqueueEnd(newJobQueue);
            }

            // If there is a job in the CPU, 'run it' for one cycle.
            job = currentJob.first();
            if (job != null)
            {
                job.computeOneCycle(time);
            }

            // TBD -- If there are multiple jobs in the CPU, complain

            // Run through the blocked queue, decrementing their times.
            // Any job no longer blocked goes to the unblocked queue.
            Job next;
            for (job = blockedQueue.first(); job != null; job = next)
            {
                next = job.next();
                job.decrementBlock();
                if (!job.blocked())
                {
                    job.dequeue();
                    job.enqueueEnd(unblockedQueue);
                }
            }

            // Run the scheduler for one cycle
            scheduler.tick();

            // If there is any job in the done queue that does not have its
            // end-time set, set it now.
            for (job = doneQueue.first(); job != null; job = next)
            {
                next = job.next();
                if (job.endTime() < 0)
                {
                    job.setEndTime(time);
                }
            }
        }

        // Break the link to the scheduler
        scheduler = null;
    }

    ////////////////////////////////////////////////////////////////////
    // Generate a report from this run
    public void generateReport()
    {
        int turnaround = 0;
        int count = 0;
        for (Job job = doneQueue.first() ; job != null; job = job.next())
        {
            count++;
            turnaround += job.elapsedTime();
            System.out.println("Job " + job.id() + ": priority " + job.priority() + ", estimated length " + job.estimatedLength() +
                    ", start time " + job.startTime() + ", end time " + job.endTime() + ", compute time " + job.computeTime() +
                    ", turnaround " + job.elapsedTime());
        }
        if (count > 0)
        {
            System.out.println("Average turnaround: " + (float) turnaround / (float) count);
        }
    }

    ////////////////////////////////////////////////////////////////////
    // Generate a report from this run
    public void checkAnswer()
    {
        int turnaround = 0;
        int count = 0;
        for (Job job = doneQueue.first() ; job != null; job = job.next())
        {
            count++;
            turnaround += job.elapsedTime();
            System.out.print("Job " + job.id() + ": priority " + job.priority() + ", estimated length " + job.estimatedLength() +
                    ", start time " + job.startTime() + ", end time " + job.endTime() + ", compute time " + job.computeTime() +
                    ", turnaround " + job.elapsedTime());
            if (job.endTime() == job.answerEndTime())
            {
                System.out.println("  ** matches my answer");
            }
            else
            {
                System.out.println("  ** my answer had endTime = " + job.answerEndTime());
            }
        }
        if (count > 0)
        {
            System.out.println("Average turnaround: " + (float) turnaround / (float) count);
        }
    }

    ////////////////////////////////////////////////////////////////////
    // Generate a homework report from this run
    public void checkHomework()
    {
        int turnaround = 0;
        int count = 0;
        for (Job job = doneQueue.first() ; job != null; job = job.next())
        {
            count++;
            turnaround += job.elapsedTime();
        }
        if (count > 0)
        {
            System.out.println(_algName + ": Average turnaround: " + (float) turnaround / (float) count);
        }
    }

    ////////////////////////////////////////////////////////////////////
    // Build the 'answer' for this run
    public void generateAnswer()
    {
        for (Job job = doneQueue.first() ; job != null; job = job.next())
        {
            job.printAnswer();
        }
    }
}
