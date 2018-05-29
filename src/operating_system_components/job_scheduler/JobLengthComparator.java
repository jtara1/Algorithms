package hw1;

import java.util.Comparator;

public class JobLengthComparator implements Comparator<Job>
{
    @Override
    public int compare(Job x, Job y) {
        return x.timeLeft() - y.timeLeft();
//        return x.estimatedLength() - y.estimatedLength();
    }
}