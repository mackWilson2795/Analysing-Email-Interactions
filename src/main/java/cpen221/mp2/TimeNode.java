package cpen221.mp2;

import java.util.ArrayList;
import java.util.Comparator;

public class TimeNode implements Comparable<TimeNode> {
    private final int time;
    private  int numEmails;
    private ArrayList<Integer> receivers;
    public TimeNode (Email email){
        time = email.getTime;
        numEmails = 1;
        receivers = new ArrayList<Integer>(email.getReceiver);
    }
    public void add(Email email){
        if (email.getTime == time) {
        numEmails ++;
        receivers.add(email.getReceiver);
        }
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
