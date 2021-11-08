package cpen221.mp2.InternalFrameworks;

public class TimeNode implements Comparable<TimeNode> {
    private final int time;
    private  int numEmails;
    //TODO : Review safety of adding/ how we will work with this;
    // TODO: specs

    /* Abstraction Function:
        numEmails = the number of emails sent
        time = the time at which the emails are sent
     */
    /*
       Rep Invariant : time and numEmails must be non-negative
     */
    public boolean checkRep(){
        boolean success = true;
        if(time < 0 || numEmails < 0){
          success = false;
        }
        return success;
    }
    /**
     * Create a new timeNode at a specific time with a given
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
     * Create a new timeNode object at a specific time
     * @param time1 specified time
     */
    public TimeNode(int time1){
        time = time1;
        numEmails = 0;
    }

    /**
     * Adds an instance of an email interaction at a timeNode
     */
    public void add(){
        numEmails ++;
    }

    /**
     * Finds the time at which a given timeNode occurs
     * @return time
     */
    public int getTime(){
        return time;
    }

    /**
     * Finds the number of emails at a given timeNode
     * @return number of emails
     */
    public int getNumEmails(){
        return numEmails;
    }

    /**
     * Created a comparison method for timeNodes, based on if each timeNode occurs at the same time.
     */
    @Override
    public int compareTo(TimeNode otherNode){
        return Integer.compare(getTime(),otherNode.getTime());
    }

    /**
     * Defines equality for a timeNode. Two timeNodes are equal if they occur at the same time.
     * @param other timeNode
     * @return true is the two timeNodes are equal and false if they are not
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof TimeNode)){
            return false;
        }
        TimeNode node = (TimeNode) other;
        return (this.getTime() == node.getTime());
    }

    /**
     * Defines the hashcode for a timeNode
      * @return the hashCode
     */
    @Override
    public int hashCode(){
        return this.time;
    }
}
