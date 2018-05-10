package hw1;

import java.util.Random;
import java.util.Scanner;

public class Main
{
    static public String studentName() { return "James Taracevicz"; }

    public static void main(String[] args)
    {
        Scanner reader = new Scanner(System.in);

        System.out.println("ScheduleSim!");
        while (true)
        {
            System.out.println("0: quit");
            System.out.println("1: run a test case");
            System.out.println("2: run homework");
            System.out.print("Yes? ");
            int op = reader.nextInt();
            if (op == 0)
            {
                break;
            }
            if (op < 0 || op > 2)
            {
                continue;
            }
            if (op == 2)
            {
                runHomework();
                continue;
            }

            // We are running a test case!
            System.out.println("0: First Come First Served");
            System.out.println("1: Shortest Job First");
            System.out.println("2: Round Robin");
            System.out.println("3: Shortest Remaining Time Next");
            System.out.println("4: Priority");
            System.out.println("5: Multiple Queue");
            System.out.print("Algorithm? ");
            int alg = reader.nextInt();

            Processor cpu = new Processor();
            Scheduler sched;
            switch (alg)
            {
            case 0:
                sched = new FirstComeFirstServedScheduler(cpu);
                break;
            case 1:
                sched = new ShortestJobFirstScheduler(cpu);
                break;
            case 2:
                sched = new RoundRobinScheduler(cpu);
                break;
            case 3:
                sched = new ShortestRemainingTimeNextScheduler(cpu);
                break;
            case 4:
                sched = new PriorityScheduler(cpu);
                break;
            case 5:
                sched = new MultipleQueueScheduler(cpu);
                break;
            default:
                continue;
            }

            System.out.println("1: Test case 1");
            System.out.println("2: Test case 2");
            System.out.print("Test case? ");
            int test = reader.nextInt();
            if (test < 1 || test > 2)
            {
                continue;
            }

            System.out.println("Results for " + studentName());
            if (test == 1)
            {
                sched.testCase1(cpu);
            }
            else
            {
                sched.testCase2(cpu);
            }
            cpu.runSim();
            System.out.println(cpu.algName() + ":");
            cpu.checkAnswer();
        }
    }

    static private void runHomework()
    {
        System.out.println("Results for " + studentName());

        Processor cpu = new Processor();
        Scheduler sched = new FirstComeFirstServedScheduler(cpu);
        testCase3(cpu);
        cpu.runSim();
        cpu.checkHomework();

        cpu = new Processor();
        sched = new ShortestJobFirstScheduler(cpu);
        testCase3(cpu);
        cpu.runSim();
        cpu.checkHomework();

        cpu = new Processor();
        sched = new RoundRobinScheduler(cpu);
        testCase3(cpu);
        cpu.runSim();
        cpu.checkHomework();

        cpu = new Processor();
        sched = new ShortestRemainingTimeNextScheduler(cpu);
        testCase3(cpu);
        cpu.runSim();
        cpu.checkHomework();

        cpu = new Processor();
        sched = new PriorityScheduler(cpu);
        testCase3(cpu);
        cpu.runSim();
        cpu.checkHomework();

        cpu = new Processor();
        sched = new MultipleQueueScheduler(cpu);
        testCase3(cpu);
        cpu.runSim();
        cpu.checkHomework();
    }

    static public void testCase3(Processor cpu)
    {
        cpu.addJob(1, 1, 19, "9,18", 3, -1);
        cpu.addJob(2, 2, 19, "4,8,12,16", 3, -1);
        cpu.addJob(3, 3, 20, "6,12,18", 2, -1);
        cpu.addJob(4, 4, 17, "5,10,15", 1, -1);
        cpu.addJob(5, 5, 10, "1,2,3,4,5", 5, -1);
        cpu.addJob(6, 6, 29, "5,10,15,20,25", 2, -1);
        cpu.addJob(7, 7, 20, "3,6,9,12,15", 1, -1);
        cpu.addJob(8, 16, 28, "5,10,15,20,25", 3, -1);
        cpu.addJob(9, 44, 12, "2,4,6,8", 2, -1);
        cpu.addJob(10, 56, 19, "18", 5, -1);
        cpu.addJob(11, 75, 25, "12,24", 4, -1);
        cpu.addJob(12, 100, 14, "6,12", 5, -1);
        cpu.addJob(13, 114, 10, "3,6,9", 4, -1);
        cpu.addJob(14, 124, 29, "14,28", 3, -1);
        cpu.addJob(15, 153, 12, "11", 5, -1);
        cpu.addJob(16, 165, 19, "3,6,9,12,15", 1, -1);
        cpu.addJob(17, 184, 16, "15", 4, -1);
        cpu.addJob(18, 200, 30, "9,18,27", 3, -1);
        cpu.addJob(19, 230, 16, "7,14", 5, -1);
        cpu.addJob(20, 246, 23, "5,10,15,20", 5, -1);
        cpu.addJob(21, 269, 14, "4,8,12", 1, -1);
        cpu.addJob(22, 283, 25, "24", 2, -1);
        cpu.addJob(23, 308, 28, "13,26", 2, -1);
        cpu.addJob(24, 336, 25, "8,16,24", 3, -1);
        cpu.addJob(25, 361, 26, "6,12,18,24", 4, -1);
        cpu.addJob(26, 387, 20, "3,6,9,12,15", 2, -1);
        cpu.addJob(27, 407, 29, "5,10,15,20,25", 4, -1);
        cpu.addJob(28, 436, 25, "12,24", 5, -1);
        cpu.addJob(29, 461, 29, "7,14,21,28", 5, -1);
        cpu.addJob(30, 490, 29, "9,18,27", 5, -1);
    }

    static public void createTestCase()
    {
        Random rand = new Random();

        // Ask for number of jobs, min and max length, min and max number of blocks, and min and max priority
        int numJobs = 30;
        int minJobLength = 10;
        int maxJobLength = 30;
        int avgJobLength = (minJobLength + maxJobLength) / 2;
        int minNumBlocks = 1;
        int maxNumBlocks = 5;
        int minPriority = 1;
        int maxPriority = 5;

        // Running count of the number of quanta left in all the jobs still to be done by the CPU
        int numQuanta = 1;
        int time = 0;
        int id = 0;
        while (true)
        {
            time++;
            numQuanta--;
            // Estimate of the number of jobs in the cpu
            int estNum = numQuanta / avgJobLength;

            // If we have < 6 jobs in the queue, add one
            if (estNum < 6 && numJobs > 0)
            {
                numJobs--;

                // Get a length, numBlocks, and priority for this job
                int jobLen = rand.nextInt(maxJobLength - minJobLength + 1) + minJobLength;
                int numBlocks = rand.nextInt(maxNumBlocks - minNumBlocks + 1) + minNumBlocks;
                int priority = rand.nextInt(maxPriority - minPriority + 1) + minPriority;

                // Get the 'blocks' string for this job
                String blocks = computeBlocks(jobLen, numBlocks);

                id++;
                System.out.println("cpu.addJob(" + id + ", " + time + ", " + jobLen + ", \"" + blocks + "\", " + priority + ", -1);");
                numQuanta += jobLen;
            }
        }
    }

    static public String computeBlocks(int jobLen, int numBlocks)
    {
        int avg = (jobLen - 1) / numBlocks;
        String result = "" + avg;
        int loc = avg;
        for (int i = 1; i < numBlocks; i++)
        {
            loc += avg;
            result = result + "," + loc;
        }
        return result;
    }
}
