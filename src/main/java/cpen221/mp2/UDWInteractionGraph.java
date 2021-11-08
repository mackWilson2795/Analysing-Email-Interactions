package cpen221.mp2;

import cpen221.mp2.Users.UndirectedUser;
import java.util.*;

import cpen221.mp2.Users.UserBuildingHelpers;
//todo: ENUM comparator for jaden, hashmap user integration in UDW and DW finish UDW

public class UDWInteractionGraph {

    private Hashtable<Integer, Hashtable<Integer, Interaction>> graph;
    private HashMap<Integer, UndirectedUser> users;
    /* ------- Task 1 ------- */
    /* Building the Constructors */
    /*
    Rep Invariant: For every User in the set users, User.getUserID()
                   is in graph.keySet()
                   For every userID in graph.keySet(), there exists
                   a User in users whose ID is userID
                   TODO: user1, user2 is the same as user2, user1
     */

    public boolean checkRep(){
        HashSet<Integer> userIDs = new HashSet<>(users.keySet());
        return (userIDs.equals(graph.keySet()));
    }

    /**
     * Creates a new UDWInteractionGraph using an email interaction file.
     * The email interaction file will be in the resources directory.
     *
     * @param fileName the name of the file in the resources
     *                 directory containing email interactions
     */
    //TODO: how do I put the reference for one object in both things
    public UDWInteractionGraph(String fileName) {
        users = new HashMap<>();
        graph = new Hashtable<>();
        ArrayList<Email> emailDataRaw = ReadingMethods.readerFunction(fileName);
        for (Email email:emailDataRaw) {
            addEmail(email);
        }
        users = UserBuildingHelpers.createUserMapUDW(graph);
    }

    private void addEmail(Email email){
        int user1 = email.getSender(), user2 = email.getReceiver(), time = email.getTimeStamp();
        addInteractionTime(user1, user2, time);
        addInteractionTime(user2, user1, time);
    }

    private void addInteractionTime(int user1, int user2, int time){
        if (graph.containsKey(user1)){
            Hashtable<Integer, Interaction> user1Table = graph.get(user1);
            if (user1Table.containsKey(user2)){
                user1Table.get(user2).addInteraction(time);
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
    //Todo: seen maybe?
    public UDWInteractionGraph(UDWInteractionGraph inputUDWIG, int[] timeFilter) {
        int lowerBound = timeFilter[0], upperbound = timeFilter[1];
        graph = new Hashtable<>();
        users = new HashMap<>();
        for (Integer user1 : inputUDWIG.graph.keySet()) {
            for (Integer user2: inputUDWIG.graph.get(user1).keySet()) {

                Interaction unfilteredInteraction = inputUDWIG.graph.get(user1).get(user2);
                if(!(unfilteredInteraction.getMinTime() > upperbound ||
                        unfilteredInteraction.getMaxTime() < lowerBound)) {
                    Interaction filteredInteraction =
                            new Interaction(unfilteredInteraction, lowerBound, upperbound);
                    addInteraction(user1, user2, filteredInteraction);
                    addInteraction(user2, user1, filteredInteraction);


                    // TODO: init user list + map

                }
            }
        }
    }

    private void addInteraction(Integer user1, Integer user2, Interaction interaction){
        if(graph.containsKey(user1)){
            if(!(graph.get(user1).containsKey(user2))){
                graph.get(user1).put(user2, interaction);
            }
        }
        else {
            Hashtable<Integer, Interaction> user1Table = new Hashtable<>();
            user1Table.put(user2,interaction);
            graph.put(user1, user1Table);
        }
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
        HashSet<Integer> filterSet = new HashSet<>(userFilter);
        HashSet<Integer> usersInFinalMap = new HashSet<>();
        for (Integer userID: filterSet) {
            usersInFinalMap.addAll(inputUDWIG.users.get(userID).getSetOfInteractedUsers());
        }
        for (Integer user :inputUDWIG.graph.keySet()) {
             if(!(usersInFinalMap.contains(user))){
             graph.remove(user);
            }
        }
        for (Integer user : graph.keySet()) {
            for (Integer userInteractedWith : graph.get(user).keySet()) {
                if(!(usersInFinalMap.contains(userInteractedWith))){
                    graph.get(user).remove(userInteractedWith);
                }
            }
        }
        //TODO: here run the map creator
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
     * in this UDWInteractionGraph.
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
     * @param user1 the User ID of the first user.
     * @param user2 the User ID of the second user.
     * @return the number of email interactions (send/receive) between user1 and user2
     */
    public int getEmailCount(int user1, int user2) {
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
        // TODO: Implement this
        /*
        SearchAlgorithms.pathExists(users.get(userID1), users.get(userID2), )
         */
        return false;
    }

}
