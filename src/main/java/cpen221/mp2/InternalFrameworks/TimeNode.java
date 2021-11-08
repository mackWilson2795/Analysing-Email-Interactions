package cpen221.mp2.InternalFrameworks;

public class TimeNode implements Comparable<TimeNode> {
    private final int time;
    private  int numEmails;
    //TODO : Review safety of adding/ how we will work with this;
    // TODO: specs

    /* Abstraction Function:
        numEmails = the number of emails sent
        time = the exact time //TODO: laura
     */

    /**
     * Create a new TimeNode at a specific time with a given
     * number of initial emails
     *
     * @param time1 the time
     * @param emails the number of emails
     */
    public TimeNode (int time1, int emails){
        time = time1;
        numEmails = emails;
    }

    /**
     * Create a new TimeNode object at a specific time
     * @param time1 //TODO: laura
     */
    public TimeNode(int time1){
        time = time1;
        numEmails = 0;
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
}
