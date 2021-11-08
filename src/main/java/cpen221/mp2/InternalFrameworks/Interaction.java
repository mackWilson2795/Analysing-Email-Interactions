package cpen221.mp2.InternalFrameworks;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class Interaction {
    private TreeSet<TimeNode> timeOrdered;
    private HashMap<Integer, Integer> frequencyCount;
    private int emailCount;
    private ArrayList<Integer> allTimes;
//Todo: Change name to Interaction_Between2Users
    /* Rep Invariant:
        emailCount should always be equal to the sum of values
        stored in the frequencyCount map.
        emailCount should also always be equal to the sum of
        TimeNode.getNumEmails() for each TimeNode in timeOrdered.
     */

    /* Abstraction Function:
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
        timeOrdered = new TreeSet<>();
        frequencyCount = new HashMap<>();
        emailCount = 0;
        allTimes = new ArrayList<>();
    }
    public Interaction(int interactions, ArrayList<Integer> times){
        this();
        for(Integer time: times) {
            this.addInteraction(time);
        }
    }
    public Interaction(Interaction interaction){
        timeOrdered = new TreeSet<>();
        frequencyCount = new HashMap<>();
        allTimes = new ArrayList<>();
        allTimes.addAll(interaction.allTimes);
        emailCount = interaction.emailCount;
        frequencyCount.putAll(interaction.frequencyCount);
        timeOrdered.addAll(interaction.timeOrdered);
    }
    public Interaction(Interaction interaction1, Interaction interaction2){
        this(interaction1);
        for (TimeNode node : interaction2.timeOrdered) {
            for (int i = 0; i < node.getNumEmails(); i++) {
                this.addInteraction(node.getTime());
            }
        }
    }


    public Interaction(int time){
        this();
        frequencyCount.put(time, 1);
        timeOrdered.add(new TimeNode(time, 1));
        emailCount = 1;
        allTimes.add(time);
    }

    public Interaction(Interaction interaction, int lowerBound, int upperBound){
        this();
        if (lowerBound < interaction.getMinTime()){
            lowerBound = interaction.getMinTime();
        }
        if (upperBound > interaction.getMaxTime()){
            upperBound = interaction.getMaxTime();
        }
        emailCount = interaction.timeRangeInteractions(lowerBound, upperBound);
        timeOrdered = (TreeSet<TimeNode>) interaction.timeOrdered.subSet(new TimeNode(lowerBound),
                        true, new TimeNode(upperBound), true);// does creating these nodes add anything to the nodes at this existing time
        //eg. another email count or something? test case to look at
        for (TimeNode node: timeOrdered) {
            frequencyCount.put(node.getTime(), node.getNumEmails());
            for(int nthEmail = 0; nthEmail < node.getNumEmails(); nthEmail++){
                allTimes.add(node.getTime());
            }
        }
    }

    public void addInteraction(int time){
        if(frequencyCount.containsKey(time)) {
            int count = frequencyCount.get(time);
            TimeNode node = new TimeNode(time, count);
            timeOrdered.remove(node);
            node.add();
            timeOrdered.add(node);
            count++;
            frequencyCount.put(time, count);
        }
        else {
            frequencyCount.put(time,1);
            timeOrdered.add(new TimeNode(time, 1));
        }
        emailCount++;
        allTimes.add(time);
    }

    public int timeRangeInteractions (int lowerBound, int upperBound){
        int sum = 0;
        for (TimeNode node: timeOrdered.subSet(new TimeNode(lowerBound), true, new TimeNode(upperBound), true)) {
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

    public int getNextTime(int initialTime){
        int count = 0;
        int nextTime = -1;
        for(TimeNode time: timeOrdered){
            if(time.getTime() >=  initialTime){
                nextTime = time.getTime();
                count++;
            }

            if (count > 0){
                break;
            }
        }

        return nextTime;
    }

    public int getInteractionCount() {
        return emailCount;
    }
    public List<Integer> getTimes() {
        return new ArrayList<>(allTimes);
    }
}

