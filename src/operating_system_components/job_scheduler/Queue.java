package hw1;

public class Queue
{
    private Job _firstJob;
    private Job _lastJob;

    public Queue()
    {
        _firstJob = null;
        _lastJob = null;
    }

    // Getters:
    public Job first() { return _firstJob; }
    public Job last() { return _lastJob; }
    public boolean empty() { return _firstJob == null; }

    // Setters -- these should only be called by the Job
    public void setFirst(Job job) { _firstJob = job; }
    public void setLast(Job job) { _lastJob = job; }
}
