package hw1;

public class Job
{
    private Job _next;
    private Job _prev;
    private Queue _queue;

    // Jobs have ID numbers for debugging
    private int _id;

    // The estimated time to complete this job
    private int _estimatedLength;

    // How much 'compute time' has gone toward this job
    private int _computeTime;

    /**
     * The time at which this job left the cpu currentJob queue
     */
    private int _timeWhenCpuWasLeft;

    /**
     * Time at which _queue was assigned to some value
     */
    private int _enteredQueueTime = 100000;

    // The priority of this job.  For multiple queue scheduling, this will
    // change over time, so the _curPriority is the current, changing priority.
    private int _priority;
    private int _curPriority;

    // This job may have a list of times where it becomes blocked for I/O.
    // This is an array of integers: when the computeTime matches one
    // of these values, the job becomes blocked.
    private int[] _blockTimes;

    // Is this job done?
    private boolean _done;

    // At what time did this job simulatedAnnealing?
    private int _startTime;

    // At what time did this job complete?
    private int _endTime;

    // If this job is blocked for I/O, how much time is left in the block?
    // If this job is not blocked, the value will be 0.
    private int _block;

    // For the cases where we have the 'answer', at what time did the 'answer'
    // end this job?
    private int _answerEndTime;

    // Create a new Job
    public Job(int ID, int curTime, int length, int numBlocks, int priority, int answerEndTime)
    {
        _id = ID;
        _next = null;
        _prev = null;
        _queue = null;
        _estimatedLength = length;
        _computeTime = 0;
        _timeWhenCpuWasLeft = -1;
        _priority = priority;
        _curPriority = priority;
        if (numBlocks == 0)
        {
            _blockTimes = new int[1];
            _blockTimes[0] = -1;
        }
        else
        {
            _blockTimes = new int[numBlocks];
            int i;
            for (i = 0; i < numBlocks; i++)
            {
                _blockTimes[i] = -1;
            }
        }
        _done = false;
        _startTime = curTime;
        _endTime = -1;
        _answerEndTime = answerEndTime;
        _block = 0;
    }

    public Job(int ID, int curTime, int length, String blocks, int priority, int answerEndTime)
    {
        _id = ID;
        _next = null;
        _prev = null;
        _queue = null;
        _estimatedLength = length;
        _computeTime = 0;
        _timeWhenCpuWasLeft = -1;
        _priority = priority;
        _curPriority = priority;
        _done = false;
        _startTime = curTime;
        _endTime = -1;
        _answerEndTime = answerEndTime;
        _block = 0;
        int numBlocks = 1;
        int len = blocks.length();
        for (int i = 0 ; i < len; i++)
        {
            char ch = blocks.charAt(i);
            if (ch == ',')
            {
                numBlocks++;
            }
        }
        _blockTimes = new int[numBlocks];
        numBlocks = 0;
        int time = 0;
        for (int i = 0; i < len; i++)
        {
            char ch = blocks.charAt(i);
            if (ch == ',')
            {
                _blockTimes[numBlocks++] = time;
                time = 0;
            }
            else if (ch >= '0' && ch <= '9')
            {
                time = 10 * time + ch - '0';
            }
        }
        _blockTimes[numBlocks++] = time;
    }

    // Set one of the block times for this Job.  Find an unset slot (its value is -1) and
    // place this value there.
    public void setBlock(int time)
    {
        int len = _blockTimes.length;
        int i;
        for (i = 0 ; i < len; i++)
        {
            if (_blockTimes[i] < 0)
            {
                _blockTimes[i] = time;
                return;
            }
        }
        _blockTimes[0] = time;
    }

    // Remove the job from its current queue
    public void dequeue()
    {
        if (_prev != null)
        {
            _prev._next = _next;
        }
        else if (_queue != null)
        {
            _queue.setFirst(_next);
        }
        if (_next != null)
        {
            _next._prev = _prev;
        }
        else if (_queue != null)
        {
            _queue.setLast(_prev);
        }
        _prev = null;
        _next = null;
        _queue = null;
    }

    // Add the job to the simulatedAnnealing of a queue
    public void enqueueStart(Queue queue)
    {
        // If already in a queue, remove it
        if (_queue != null)
        {
            dequeue();
        }
        _next = queue.first();
        if (_next != null)
        {
            _next._prev = this;
        }
        else
        {
            queue.setLast(this);
        }
        _prev = null;
        _queue = queue;
        queue.setFirst(this);
    }

    // Add the job to the end of a queue
    public void enqueueEnd(Queue queue)
    {
        // If already in a queue, remove it
        if (_queue != null)
        {
            dequeue();
        }
        _prev = queue.last();
        if (_prev != null)
        {
            _prev._next = this;
        }
        else
        {
            queue.setFirst(this);
        }
        _next = null;
        _queue = queue;
        queue.setLast(this);
    }

    public void onEnterQueue() {

    }

    ///// Getters and Setters /////
    public int id() { return _id; }
    public Job next() { return _next; }
    public Job prev() { return _prev; }
    public int estimatedLength() { return _estimatedLength; }
    public int timeLeft() { return _estimatedLength - _computeTime; }
    public int priority() { return _priority; }
    public int curPriority() { return _curPriority; }
    public void decPriority() { _curPriority--; }
    public boolean done() { return _done; }
    public int startTime() { return _startTime; }
    public int endTime() { return _endTime; }
    public int answerEndTime() { return _answerEndTime; }
    public void setEndTime(int time) { _endTime = time; }
    public int computeTime() { return _computeTime; }
//    public int elapsedComputeTime() { return _computeTime - _startTime; }
    public int elapsedTime() { return _endTime - _startTime; }
    public int answerElapsedTime() { return _answerEndTime - _startTime; }
    public boolean blocked() { return _block > 0; }

    public int timeWhenCpuWasLeft() { return _timeWhenCpuWasLeft; }
    public int get_enteredQueueTime() { return _enteredQueueTime; }
    public void set_enteredQueueTime(int time) { _enteredQueueTime = time; }

    ///// These should only be called by the 'engine' /////

    public void computeOneCycle(int curTime)
    {
        // TBD: Print an error message if we are done -- we should not still be
        // in the processor!

        // TBD: Print an error message if we are blocked -- we should be in the
        // blocked queue!

        _computeTime++;

        // See if we are done
        if (_computeTime >= _estimatedLength)
        {
            _done = true;
            _endTime = curTime;
        }

        // See if we should block
        int len = _blockTimes.length;
        int i;
        for (i = 0 ; i < len; i++)
        {
            if (_blockTimes[i] == _computeTime)
            {
                _block += 5;   // Block for a period of time
                break;
            }
        }
    }

    public void decrementBlock()
    {
        if (_block > 0)
        {
            _block--;
        }
    }

    public void printAnswer()
    {
        System.out.print("cpu.addJob(" + _id + ", " + _startTime + ", " + _estimatedLength + ", \"");
        int len = _blockTimes.length;
        if (len > 0)
        {
            for (int i = 0 ; i < len; i++)
            {
                if (i > 0)
                {
                    System.out.print(",");
                }
                System.out.print("" + _blockTimes[i]);
            }
        }
        System.out.println("\", " + _priority + ", " + _endTime + ");");
    }
}
