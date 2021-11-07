package cpen221.mp2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Interaction {
    private TreeSet<TimeNode> timeOrdered;
    private HashMap<Integer, Integer> frequencyCount;
    private int emailCount;
    private ArrayList<Integer> times;
//Todo: Change name to Interaction_Between2Users
    /*Rep Invariant : emailCount should always be
    equal to the sum of values stored in the frequencyCount
    map. emailCount should also always be equal to the sum of
    TimeNode.getNumEmails() for each TimeNode in timeOrdered.
     */
    /*
    Abstraction Function:
    timeOrdered = representation of every email sent
                  between 2 users, in the time domain.
    frequencyCount = maps a time value to a number representing
                     the number of emails between the two users at that time
    emailCount = number of emails between the two users
     */
    //Todo: copy constructor
    public boolean checkRep(){
        int totalFrequencyCount = 0, totalTimeOrdered = 0;
        for (TimeNode time:timeOrdered) {
            totalTimeOrdered += time.getNumEmails();
        }
        for (Integer time: frequencyCount.keySet()) {
            totalFrequencyCount += frequencyCount.get(time);
        }
        return (emailCount == totalFrequencyCount && emailCount == totalTimeOrdered);
    }
    //TODO: internal variable for senders or receivers?
    public Interaction(){
        timeOrdered = new TreeSet<TimeNode>();
        frequencyCount = new HashMap<Integer, Integer>();
        emailCount = 0;
    }

    public Interaction(int time){
        timeOrdered = new TreeSet<TimeNode>();
        frequencyCount = new HashMap<Integer, Integer>();
        frequencyCount.put(time, 1);
        timeOrdered.add(new TimeNode(time, 1));
        emailCount = 1;
    }

    public Interaction(Interaction interaction, int lowerBound, int upperBound){
        if (lowerBound < interaction.getMinTime()){
            lowerBound = interaction.getMinTime();
        }
        if (upperBound > interaction.getMaxTime()){
            upperBound = interaction.getMaxTime();
        }
        emailCount = interaction.emailCount;
        times = new ArrayList<>();
        frequencyCount = new HashMap<Integer, Integer>();
        timeOrdered = (TreeSet<TimeNode>) interaction.timeOrdered.subSet(new TimeNode(lowerBound),
                        true, new TimeNode(upperBound), true);// does creating these nodes add anything to the nodes at this existing time
        //eg. another email count or something? test case to look at
        for (TimeNode node: timeOrdered) {
            frequencyCount.put(node.getTime(), node.getNumEmails());
            for(int nthEmail = 0; nthEmail < node.getNumEmails(); nthEmail++){
                times.add(node.getTime());
            }
        }
    }

    public void add(int time){
        if(frequencyCount.containsKey(time)) {
            TimeNode node = new TimeNode(time, frequencyCount.get(time));
            timeOrdered.remove(node);
            node.add();
            timeOrdered.add(node);
        }
        else {
            frequencyCount.put(time,1);
            timeOrdered.add(new TimeNode(time, 1));
        }
    }

    public int timeRangeInteractions (int lowerBound, int upperBound){
        int sum = 0;
        for (TimeNode node: timeOrdered.subSet(new TimeNode(lowerBound), new TimeNode(upperBound))) {
            sum += node.getNumEmails();
        }
        return sum;
    }

    public int getMinTime(){
        return timeOrdered.first().getTime();
    }

    public int getMaxTime() {
        return timeOrdered.last().getTime();
    }


}
