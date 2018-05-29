package hw1;

public class Scheduler
{
    // The processor used by this Scheduler:
    Processor cpu;

    public Scheduler(Processor theCPU)
    {
        cpu = theCPU;
        cpu.setScheduler(this);
    }

    // This is the function that should be implemented by the sub-class to
    // perform the scheduling logic.
    public void tick()
    {

    }

    // This function is also implemented in the sub-class
    public String algName()
    {
        return "Unknown";
    }

    // You guessed it!  This is also implemented in the sub-class
    public void testCase1(Processor cpu)
    {

    }

    // Yet another method implemented in the sub-class
    public void testCase2(Processor cpu)
    {

    }
}
