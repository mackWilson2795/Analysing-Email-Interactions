package cpen221.mp2;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import java.io.File;
import java.util.Hashtable;
import java.util.ArrayList;

public class DWInteractionGraph {

//Notes:
    //1. added another hashtable,hashtable structure in the dwinteraction class but this time for receiver to sender

    /* ------- Task 1 ------- */
    /* Building the Constructors */
    private Hashtable<Integer, Hashtable<Integer, Interaction>> interactionGraph;
    private Hashtable<Integer, Hashtable<Integer, Interaction>> interactionGraphReceiver;
    private Set<Integer> userIDs;


    /**
     * Creates a new DWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    public DWInteractionGraph(String fileName) {

        userIDs = new HashSet<Integer>();
        interactionGraph = new Hashtable<Integer, Hashtable<Integer, Interaction>>();
        interactionGraphReceiver = new Hashtable<Integer, Hashtable<Integer, Interaction>>();

        try {

            File file = new File(fileName);
            Scanner input = new Scanner(file);

            int count = 0;
            int sender = -1;
            int receiver = -1;
            while (input.hasNextInt()) {
                int number = input.nextInt();
                count++;


                if (count == 1) {
                    sender = number;
                    if (!interactionGraph.containsKey(sender)) {
                        interactionGraph.put(sender, new Hashtable<Integer, Interaction>());
                    }
                    userIDs.add(sender);
                }
                if (count == 2) {
                    receiver = number;
                    userIDs.add(receiver);
                   if (!interactionGraphReceiver.containsKey(receiver)) {
                        interactionGraphReceiver.put(receiver, new Hashtable<Integer, Interaction>());
                    }
                }
                if (count == 3) {
                    if (interactionGraph.get(sender) == null) {
                        interactionGraph.get(sender).put(receiver, new Interaction(number));
                    }
                    else if (!interactionGraph.get(sender).containsKey(receiver)) {
                        interactionGraph.get(sender).put(receiver, new Interaction(number));
                    }
                    else {
                        interactionGraph.get(sender).get(receiver).addInteraction(number);
                    }

                    if(interactionGraphReceiver.get(receiver) == null) {
                        interactionGraphReceiver.get(receiver).put(sender, new Interaction(number));
                    }
                    else if(!interactionGraphReceiver.get(receiver).containsKey(sender)) {
                        interactionGraphReceiver.get(receiver).put(sender, new Interaction(number));
                    }
                    else {
                        interactionGraphReceiver.get(receiver).get(sender).addInteraction(number);
                    }
                    count = 0;
                }
            }
        }
        catch(FileNotFoundException e){

        }
    }

    /**
     * Creates a new DWInteractionGraph from a DWInteractionGraph object
     * and considering a time window filter.
     *
     * @param inputDWIG a DWInteractionGraph object
     * @param timeFilter an integer array of length 2: [t0, t1]
     *                   where t0 <= t1. The created DWInteractionGraph
     *                   should only include those emails in the input
     *                   DWInteractionGraph with send time t in the
     *                   t0 <= t <= t1 range.
     */
    public DWInteractionGraph(DWInteractionGraph inputDWIG, int[] timeFilter) {

        Set<Integer> users = new HashSet<Integer>();
        users = inputDWIG.getUserIDs();
        userIDs = inputDWIG.getUserIDs();

        for(Integer sender : users) {
            for (Integer receiver : users) {
                if (inputDWIG.isSender(sender)) {
                    if (inputDWIG.isInteractive(sender, receiver)) {
                        Interaction temp = inputDWIG.getUserInteraction(sender, receiver);
                        List<Integer> times = temp.getTimes();
                        Interaction placeHolder = new Interaction();

                        int count = 0;

                        for (Integer time : times) {
                            if (time >= timeFilter[0] && time <= timeFilter[1]) {
                                placeHolder.addInteraction(time);
                                count++;
                            }
                        }

                        if(interactionGraph == null){
                            interactionGraph = new Hashtable<Integer, Hashtable<Integer, Interaction>>();
                        }
                       if(count > 0){
                        interactionGraph.put(sender, new Hashtable<Integer, Interaction>());
                        interactionGraph.get(sender).put(receiver, placeHolder);
                        }
                    }
                }
            }
        }

        for(Integer receive1:users){
            for(Integer send1:users){
                if(inputDWIG.isReceiver(receive1)){
                    if(inputDWIG.isInteractiveReceiver(receive1,send1)){
                        Interaction temp = inputDWIG.getUserInteractionReceiver(receive1,send1);
                        List<Integer> times = temp.getTimes();
                        Interaction placeHolder = new Interaction();

                        int count = 0;

                        for(Integer time: times){
                            if(time >= timeFilter[0] && time <= timeFilter[1]){
                                placeHolder.addInteraction(time);
                                count++;
                            }
                        }

                        if(interactionGraphReceiver == null){
                            interactionGraphReceiver = new Hashtable<Integer, Hashtable<Integer, Interaction>>();
                        }
                       if(count > 0) {
                            interactionGraphReceiver.put(receive1, new Hashtable<Integer, Interaction>());
                            interactionGraphReceiver.get(receive1).put(send1, placeHolder);
                        }
                    }
                }
            }
        }

        for(Integer sender:users) { //needs to be revised
            if (interactionGraph.get(sender) == null){
                    userIDs.remove(sender);
            }
        }
        HashSet<Integer> temporary = new HashSet<Integer>();
        for(Integer sender:userIDs) {
            Set<Integer> Temp = new HashSet<Integer>();
            Temp = interactionGraph.get(sender).keySet();
            for(Integer receiver: Temp){
                temporary.add(receiver);
            }
        }
        for(Integer receiver: temporary){
            userIDs.add(receiver);
        }
    }



    public Interaction getUserInteraction(int sender, int receiver){

        int interactions = interactionGraph.get(sender).get(receiver).getInteractionCount();
        ArrayList<Integer> copy = new ArrayList<Integer>();
        for(Integer num: interactionGraph.get(sender).get(receiver).getTimes()){
            copy.add(num);
        }

        Interaction copyinteraction = new Interaction(interactions,copy);

        return copyinteraction;
    }

    public Interaction getUserInteractionReceiver(int receiver, int sender){

        int interactions = interactionGraphReceiver.get(receiver).get(sender).getInteractionCount();
        ArrayList<Integer> copy = new ArrayList<Integer>();
        for(Integer num: interactionGraphReceiver.get(receiver).get(sender).getTimes()){
            copy.add(num);
        }

        Interaction copyinteraction = new Interaction(interactions,copy);

        return copyinteraction;
    }

    public Boolean isSender(int sender){
        return interactionGraph.containsKey(sender);
    }

    public Boolean isInteractive(int sender, int receiver){
        if(interactionGraph.containsKey(sender)){
            if(interactionGraph.get(sender).containsKey(receiver)){
                return true;
            }
        }
        return false;
    }


    public Boolean isReceiver(int receiver){
        return interactionGraphReceiver.containsKey(receiver);
    }

    public Boolean isInteractiveReceiver(int receiver, int sender){
        if(interactionGraphReceiver.containsKey(receiver)){
            if(interactionGraphReceiver.get(receiver).containsKey(sender)){
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new DWInteractionGraph from a DWInteractionGraph object
     * and considering a list of User IDs.
     *
     * @param inputDWIG a DWInteractionGraph object
     * @param userFilter a List of User IDs. The created DWInteractionGraph
     *                   should exclude those emails in the input
     *                   DWInteractionGraph for which neither the sender
     *                   nor the receiver exist in userFilter.
     */
    public DWInteractionGraph(DWInteractionGraph inputDWIG, List<Integer> userFilter) {

        Set<Integer> users = new HashSet<Integer>();
        users = inputDWIG.getUserIDs();
        userIDs = inputDWIG.getUserIDs();
        Set<Integer> filterUsers = new HashSet<Integer>(userFilter);

        for(Integer sender :users) {
            for (Integer receiver : users) {
                if (inputDWIG.isSender(sender)) {
                    if (inputDWIG.isInteractive(sender, receiver)) {
                        if(filterUsers.contains(sender)||filterUsers.contains(receiver)) {
                            Interaction temp = inputDWIG.getUserInteraction(sender, receiver);
                            List<Integer> times = temp.getTimes();
                            Interaction placeHolder = new Interaction();

                            for(Integer time: times){
                                    placeHolder.addInteraction(time);
                            }

                            if (interactionGraph == null) {
                                interactionGraph = new Hashtable<Integer, Hashtable<Integer, Interaction>>();
                            }
                            interactionGraph.put(sender, new Hashtable<Integer, Interaction>());
                            interactionGraph.get(sender).put(receiver, placeHolder);
                        }
                    }
                }
            }
        }

        for(Integer receive1:users){
            for(Integer send1:users){
                if(inputDWIG.isReceiver(receive1)){
                    if(inputDWIG.isInteractiveReceiver(receive1,send1)){
                        if(userFilter.contains(send1)||userFilter.contains(receive1)) {
                            Interaction temp = inputDWIG.getUserInteractionReceiver(receive1, send1);
                            List<Integer> times = temp.getTimes();
                            Interaction placeHolder = new Interaction();

                            for(Integer time: times){
                                placeHolder.addInteraction(time);
                            }

                            if (interactionGraphReceiver == null) {
                                interactionGraphReceiver =
                                    new Hashtable<Integer, Hashtable<Integer, Interaction>>();
                            }
                            interactionGraphReceiver.put(receive1, new Hashtable<Integer, Interaction>());
                            interactionGraphReceiver.get(receive1).put(send1, placeHolder);
                        }
                    }
                }
            }
        }

        for(Integer sender:users) {
            if (interactionGraph.get(sender) == null) {
                userIDs.remove(sender);
            }
        }

        HashSet<Integer> temporary = new HashSet<Integer>();


        for(Integer sender:userIDs) {
            Set<Integer> Temp = new HashSet<Integer>();
            Temp = interactionGraph.get(sender).keySet();
            for(Integer receiver: Temp){
                temporary.add(receiver);
            }
        }

        for(Integer receiver: temporary){
            userIDs.add(receiver);
        }


    }

    /**
     * @return a Set of Integers, where every element in the set is a User ID
     * in this DWInteractionGraph.
     */
    public Set<Integer> getUserIDs() {
        Set<Integer> copy = new HashSet<Integer>();
        for(Integer userID: userIDs){
            copy.add(userID);
        }
        return copy;
    }

    /**
     * @param sender the User ID of the sender in the email transaction.
     * @param receiver the User ID of the receiver in the email transaction.
     * @return the number of emails sent from the specified sender to the specified
     * receiver in this DWInteractionGraph.
     */
    public int getEmailCount(int sender, int receiver) {
        if (interactionGraph == null) {
            return 0;
        }
        if (!interactionGraph.containsKey(sender)){
            return 0;
        }
        if (!interactionGraph.get(sender).containsKey(receiver)){

            return 0;

        }
        return interactionGraph.get(sender).get(receiver).getInteractionCount();

    }

    /* ------- Task 2 ------- */

    /**
     * Given an int array, [t0, t1], reports email transaction details.
     * Suppose an email in this graph is sent at time t, then all emails
     * sent where t0 <= t <= t1 are included in this report.
     * @param timeWindow is an int array of size 2 [t0, t1] where t0<=t1.
     * @return an int array of length 3, with the following structure:
     * [NumberOfSenders, NumberOfReceivers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
        // TODO: Implement this method
        return null;
    }

    /**
     * Given a User ID, reports the specified User's email transaction history.
     * @param userID the User ID of the user for which the report will be
     *               created.
     * @return an int array of length 3 with the following structure:
     * [NumberOfEmailsSent, NumberOfEmailsReceived, UniqueUsersInteractedWith]
     * If the specified User ID does not exist in this instance of a graph,
     * returns [0, 0, 0].
     */
    public int[] ReportOnUser(int userID) {
        // TODO: Implement this method
        return null;
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @param interactionType Represent the type of interaction to calculate the rank for
     *                        Can be SendOrReceive.Send or SendOrReceive.RECEIVE
     * @return the User ID for the Nth most active user in specified interaction type.
     * Sorts User IDs by their number of sent or received emails first. In the case of a
     * tie, secondarily sorts the tied User IDs in ascending order.
     */
    public int NthMostActiveUser(int N, SendOrReceive interactionType) {
        // TODO: Implement this method
        return -1;
    }

    /* ------- Task 3 ------- */

    /**
     * performs breadth first search on the DWInteractionGraph object
     * to check path between user with userID1 and user with userID2.
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, should return null.
     */
    public List<Integer> BFS(int userID1, int userID2) {
        List<Integer> path = new ArrayList<Integer>();
        List<Integer> nextPaths = new ArrayList<Integer>();
        Set<Integer> adjacentUsers = new HashSet<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        int lastUser;

        nextPaths.add(userID1);

        while(nextPaths.size() > 0){

            path.add(nextPaths.get(0));
            nextPaths.remove(0);

            lastUser = path.get(path.size()-1);
            visited.add(lastUser);

            if (lastUser == userID2){
                return path;
            }

            if(interactionGraph.containsKey(lastUser)) {
                adjacentUsers = interactionGraph.get(lastUser).keySet();
            }
            if(!interactionGraph.containsKey(lastUser)) {
                adjacentUsers = new HashSet<Integer>();
            }

            List<Integer> nextUsers = new ArrayList<Integer>();

            for(Integer user:adjacentUsers){
                if(!path.contains(user) && !nextPaths.contains(user)){
                    nextUsers.add(user);
                }
            }
            Collections.sort(nextUsers);

            for(Integer adjUser: nextUsers){
                nextPaths.add(adjUser);
            }
        }

        return null;
    }
    /**
     * performs depth first search on the DWInteractionGraph object
     * to check path between user with userID1 and user with userID2.
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return if a path exists, returns aa list of user IDs
     * in the order encountered in the search.
     * if no path exists, should return null.
     */
    public List<Integer> DFS(int userID1, int userID2) {
        // TODO: Implement this method
        return null;
    }

    /* ------- Task 4 ------- */

    /**
     * Read the MP README file carefully to understand
     * what is required from this method.
     * @param hours
     * @return the maximum number of users that can be polluted in N hours
     */
    public int MaxBreachedUserCount(int hours) {
        // TODO: Implement this method
        return 0;
    }

}
