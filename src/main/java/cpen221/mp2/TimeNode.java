package cpen221.mp2;

import java.util.ArrayList;
import java.util.Comparator;

public class TimeNode implements Comparable<TimeNode> {
    private final int time;
    private  int numEmails;
    public TimeNode (int time1){
        time = time1;
        numEmails = 1;
    }
    public void add(){
        numEmails ++;
    }
    public int getTime(){
        return time;
    }
    public int getNumEmails(){
        return numEmails;
    }
    @Override
    public int compareTo( TimeNode otherNode){
        return Integer.compare(getTime(),otherNode.getTime());
    }

}
