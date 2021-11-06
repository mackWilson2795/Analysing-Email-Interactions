package cpen221.mp2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Interaction {
    private TreeSet<TimeNode> set;
    private HashMap<Integer, Integer> frequencyCount;
    private int emailCount;
    //TODO: internal variable for senders or receivers?
    public Interaction(){
        set = new TreeSet<TimeNode>();
        frequencyCount = new HashMap<Integer, Integer>();
        emailCount = 0;
    }

    public Interaction(int time){
        frequencyCount.put(time, 1);
        set.add(new TimeNode(time, 1));
        emailCount = 1;
    }

    public void add(int time){
        if(frequencyCount.containsKey(time)) {
            TimeNode node = new TimeNode(time, frequencyCount.get(time));
            set.remove(node);
            node.add();
            set.add(node);
        }
        else {
            frequencyCount.put(time,1);
            set.add(new TimeNode(time, 1));
        }
    }

    public int timeRangeInteractions (int lowerBound, int upperBound){
        int sum = 0;
        for (TimeNode node: set.subSet(new TimeNode(lowerBound), new TimeNode(upperBound))) {
            sum += node.getNumEmails();
        }
        return sum;
    }
}
