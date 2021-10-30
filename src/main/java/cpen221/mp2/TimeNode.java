package cpen221.mp2;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;

public class TimeNode implements Comparable<TimeNode> {
    private final int time;
    private  int numEmails;
    //TODO : Review safety of adding/ how we will work with this;
    public TimeNode (int time1, int emails){
        time = time1;
        numEmails = emails;
    }
    public TimeNode(int time1){
        time = time1;
        numEmails = 0; // does this break the rep invariant?
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
    public int compareTo(TimeNode otherNode){
        return Integer.compare(getTime(),otherNode.getTime());
    }
    @Override
    public boolean equals(Object other){
        if(!(other instanceof TimeNode)){
            return false;
        }
        TimeNode node = (TimeNode) other;
        return (this.getTime() == node.getTime());
    }
   /* @Override
    public int hashCode(TimeNode node){
        return node.getTime();
    }*/
}
