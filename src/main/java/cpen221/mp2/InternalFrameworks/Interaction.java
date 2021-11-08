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

    /* Abstraction Function:
        timeOrdered = representation of every email sent
                      between 2 users, in the time domain.
        frequencyCount = maps a time value to a number representing
                         the number of emails between the two users at that time
        emailCount = number of emails between the two users
     */

    /* Rep Invariant:
        emailCount should always be equal to the sum of values stored
         in the frequencyCount map.
        emailCount should always be equal to the sum of TimeNode.getNumEmails()
         for each TimeNode in timeOrdered.
     */
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

    /**
     * Create a new empty interaction object
     */
    public Interaction(){
        timeOrdered = new TreeSet<>();
        frequencyCount = new HashMap<>();
        emailCount = 0;
        allTimes = new ArrayList<>();
    }

    /**
     * Create a new interaction object from a number of interactions
     * and a List of the times at which they occur
     *
     * @param interactions number of interactions
     * @param times a list of interaction times
     */
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

    /**
     * Create a new interaction object from 2 given interaction objects.
     * The new object contains the exact data found in both given interaction
     * objects.
     * @param interaction1 First given interaction object
     * @param interaction2 Second given interaction object
     */
    public Interaction(Interaction interaction1, Interaction interaction2){
        this(interaction1);
        for (TimeNode node : interaction2.timeOrdered) {
            for (int i = 0; i < node.getNumEmails(); i++) {
                this.addInteraction(node.getTime());
            }
        }
    }

    /**
     * Create a new interaction object with one interaction at a specific
     * time.
     *
     * @param time the time of the interaction
     */
    public Interaction(int time){
        this();
        frequencyCount.put(time, 1);
        timeOrdered.add(new TimeNode(time, 1));
        emailCount = 1;
        allTimes.add(time);
    }

    /**
     * Create a new interaction object from another interaction object
     * where all interactions in the resulting object occur between the
     * lowerbound and upperbound time
     *
     * @param interaction the given interaction object
     * @param lowerBound the lowerbound time
     * @param upperBound the upperbound time
     */
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

    /**
     * Add a single interaction to the object
     *
     * @param time the time of the interaction
     */
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

    /**
     * Determine the number of interactions that have occurred in
     * a given time range, where the time range is:
     * lowerBound <= t <= upperBound
     *
     * @param lowerBound the lower time bound
     * @param upperBound the upper time bound
     * @return the number of interactions
     */
    public int timeRangeInteractions (int lowerBound, int upperBound){
        int sum = 0;
        for (TimeNode node: timeOrdered.subSet(new TimeNode(lowerBound), true, new TimeNode(upperBound), true)) {
            sum += node.getNumEmails();
        }
        return sum;
    }

    /**
     * Determine the time of the first interaction in the object
     *
     * @return the time of the first interaction
     */
    public int getMinTime(){
        return timeOrdered.first().getTime();
    }

    /**
     * Determine the time of the last interaction in the object
     *
     * @return the time of the last interaction
     */
    public int getMaxTime() {
        return timeOrdered.last().getTime();
    }

    /**
     * Determine the time of the next interaction that occurs after
     * a specific time
     *
     * @param initialTime the start time
     * @return the time of the next interaction
     */
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

    /**
     * Obtain the total number of interactions
     *
     * @return the total number of interactions
     */
    public int getInteractionCount() {
        return emailCount;
    }

    /**
     * Obtain a List containing the time of every interaction
     *
     * @return a List containing the time of every interaction
     */
    public List<Integer> getTimes() {
        return new ArrayList<>(allTimes);
    }
}

