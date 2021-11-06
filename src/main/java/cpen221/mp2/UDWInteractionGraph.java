package cpen221.mp2;

import java.util.*;

import static cpen221.mp2.PreProcessing_ToRename.readerFunction;

public class UDWInteractionGraph {

    private Hashtable<Integer, Hashtable<Integer, Interaction>> graph;


    /* ------- Task 1 ------- */
    /* Building the Constructors */

    /**
     * Creates a new UDWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    //TODO: how do I put the reference for one object in both things
    public UDWInteractionGraph(String fileName) {
        graph = new Hashtable<Integer, Hashtable<Integer, Interaction>>();
        ArrayList<Email> emailDataRaw = PreProcessing_ToRename.readerFunction(fileName);
        for (Email email:emailDataRaw) {
            addEmail(email);
        }
    }

    private void addEmail(Email email){
        int user1 = email.getSender(), user2 = email.getReceiver(), time = email.getTimeStamp();
     addUserInteraction(user1, user2, time);
     addUserInteraction(user2, user1, time);
    }

    private void addUserInteraction(int user1, int user2, int time){
        if (graph.containsKey(user1)){
            Hashtable<Integer, Interaction> user1Table = graph.get(user1);
            if (user1Table.containsKey(user2)){
                user1Table.get(user2).add(time);
            }
            else {
                user1Table.put(user2, new Interaction(time));
            }
        }
        else {
            Hashtable<Integer, Interaction> newUser1Table = new Hashtable<>();// do I need to make a new one?
            newUser1Table.put(user2, new Interaction(time));
            graph.put(user1, newUser1Table);
        }
    }

    /**
     * Creates a new UDWInteractionGraph from a UDWInteractionGraph object
     * and considering a time window filter.
     *
     * @param inputUDWIG a UDWInteractionGraph object
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created UDWInteractionGraph
     *                   should only include those emails in the input
     *                   UDWInteractionGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    public UDWInteractionGraph(UDWInteractionGraph inputUDWIG, int[] timeFilter) {

    }

    /**
     * Creates a new UDWInteractionGraph from a UDWInteractionGraph object
     * and considering a list of User IDs.
     *
     * @param inputUDWIG a UDWInteractionGraph object
     * @param userFilter a List of User IDs. The created UDWInteractionGraph
     *                   should exclude those emails in the input
     *                   UDWInteractionGraph for which neither the sender
     *                   nor the receiver exist in userFilter.
     */
    public UDWInteractionGraph(UDWInteractionGraph inputUDWIG, List<Integer> userFilter) {
        graph = (Hashtable<Integer, Hashtable<Integer, Interaction>>) inputUDWIG.graph.clone();
        for (Integer sender:graph.keySet()) {
            if(userFilter.contains(sender)){
                graph.remove(sender);
            }
            for (Integer receiver: graph.get(sender).keySet()) {
                if(userFilter.contains(receiver)){
                    graph.remove(receiver);
                }
            }
        }
    }

    /**
     * Creates a new UDWInteractionGraph from a DWInteractionGraph object.
     *
     * @param inputDWIG a DWInteractionGraph object
     */
    public UDWInteractionGraph(DWInteractionGraph inputDWIG) {
        // TODO: Implement this constructor
    }

    /**
     * @return a Set of Integers, where every element in the set is a User ID
     * in this DWInteractionGraph.
     */
    public Set<Integer> getUserIDs() {
        HashSet<Integer> userIDs = new HashSet<>();
        userIDs.addAll(graph.keySet());
        for (Integer key: graph.keySet()) {
            userIDs.addAll(graph.get(key).keySet());
        }
        return userIDs;
    }

    /**
     * @param sender the User ID of the sender in the email transaction.
     * @param receiver the User ID of the receiver in the email transaction.
     * @return the number of emails sent from the specified sender to the specified
     * receiver in this DWInteractionGraph.
     */
    // TODO: fix this to match the skeleton code
    public int getEmailCount(int sender, int receiver) {
        // TODO: Implement this getter method
        return 0;
    }

    /* ------- Task 2 ------- */

    /**
     * @param timeWindow is an int array of size 2 [t0, t1]
     *                   where t0<=t1
     * @return an int array of length 2, with the following structure:
     *  [NumberOfUsers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
        // TODO: Implement this method
        return null;
    }

    /**
     * @param userID the User ID of the user for which
     *               the report will be created
     * @return an int array of length 2 with the following structure:
     *  [NumberOfEmails, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in this instance of a graph,
     * returns [0, 0].
     */
    public int[] ReportOnUser(int userID) {
        // TODO: Implement this method
        return null;
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @return the User ID for the Nth most active user
     */
    public int NthMostActiveUser(int N) {
        // TODO: Implement this method
        return -1;
    }

    /* ------- Task 3 ------- */

    /**
     * @return the number of completely disjoint graph
     *    components in the UDWInteractionGraph object.
     */
    public int NumberOfComponents() {
        // TODO: Implement this method
        return 0;
    }

    /**
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return whether a path exists between the two users
     */
    public boolean PathExists(int userID1, int userID2) {
        // TODO: Implement this method
        return false;
    }

}
