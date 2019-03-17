package hw1;

public class MultipleQueueScheduler extends Scheduler
{
    private int _quanta;

    public MultipleQueueScheduler(Processor theCPU)
    {
        super(theCPU);
    }

    public String algName() { return "Multiple Queue Scheduler"; }

    private void setQuanta(int priority)
    {
        _quanta = 1 << (5 - priority);
    }

    // This function does the scheduling work!
    public void tick()
    {
        Job job, best;
        int curPriority, bestPriority;

        // If there were any jobs that used to be blocked, but no longer are,
        // move them from the unblockedQueue to the end of the readyQueue, unless its
        // estimated remaining time is less than the time of the job in the cpu.
        for (job = cpu.unblockedQueue.first(); job != null; job = cpu.unblockedQueue.first())
        {
            job.enqueueEnd(cpu.readyQueue);
        }

        // If there are any new jobs being added to the processor, move them
        // from the newJobQueue to the end of the readyQueue, unless the remaining
        // time is less than the current job
        for (job = cpu.newJobQueue.first(); job != null; job = cpu.newJobQueue.first())
        {
            job.enqueueEnd(cpu.readyQueue);
        }

        // Check the job in the cpu (if any).  If it is done, move it to the done queue,
        // and if it is blocked, move it to the blocked queue.
        job = cpu.currentJob.first();
        if (job != null)
        {
            if (job.done())
            {
                job.enqueueEnd(cpu.doneQueue);
            }
            else if (job.blocked())
            {
                job.enqueueEnd(cpu.blockedQueue);
            }
            else
            {
                _quanta--;
                if (_quanta <= 0)
                {
                    job.decPriority();
                    job.enqueueEnd(cpu.readyQueue);
                }
            }
        }

        // If the CPU is ready for a new job, simulatedAnnealing that job
        if (cpu.currentJob.empty())
        {
            // Find the highest priority job in the ready queue
            best = null;
            bestPriority = -999;
            for (job = cpu.readyQueue.first(); job != null; job = job.next())
            {
                curPriority = job.curPriority();
                if (best == null || curPriority > bestPriority)
                {
                    best = job;
                    bestPriority = curPriority;
                }
            }
            if (best != null)
            {
                best.enqueueStart(cpu.currentJob);
                setQuanta(best.curPriority());
            }
        }
    }

    public void testCase1(Processor cpu)
    {
        cpu.addJob(1, 1, 50, "10,20,30,40", 1, 128);
        cpu.addJob(2, 1, 30, "7,15,22", 3, 103);
        cpu.addJob(3, 1, 20, "6,14", 2, 74);
        cpu.addJob(4, 1, 20, "6,14", 3, 85);
    }

    public void testCase2(Processor cpu)
    {
        cpu.addJob(1, 0, 30, "9,18,27", 4, 210);
        cpu.addJob(2, 1, 20, "3,6,9,12,15", 5, 104);
        cpu.addJob(3, 2, 30, "5,10,15,20,25", 2, 120);
        cpu.addJob(4, 21, 21, "20", 4, 179);
        cpu.addJob(5, 42, 29, "28", 3, 198);
        cpu.addJob(6, 71, 18, "5,10,15", 3, 135);
        cpu.addJob(7, 89, 13, "4,8,12", 4, 136);
        cpu.addJob(8, 102, 19, "4,8,12,16", 2, 158);
        cpu.addJob(9, 121, 19, "9,18", 1, 217);
        cpu.addJob(10, 140, 26, "6,12,18,24", 1, 234);
    }
}