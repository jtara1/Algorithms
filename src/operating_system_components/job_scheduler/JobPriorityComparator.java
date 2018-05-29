package hw1;

import java.util.Comparator;

public class JobPriorityComparator implements Comparator<Job>
{
    @Override
    public int compare(Job x, Job y) {
        return y.priority() - x.priority();
    }
}