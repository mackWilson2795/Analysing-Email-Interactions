package cpen221.mp2;

import cpen221.mp2.InternalFrameworks.Email;
import cpen221.mp2.InternalFrameworks.Interaction;
import cpen221.mp2.InternalFrameworks.ReadingMethods;
import cpen221.mp2.Users.UndirectedUser;
import java.util.*;

import cpen221.mp2.Users.UserBuildingHelpers;


public class UDWInteractionGraph {

    private Hashtable<Integer, Hashtable<Integer, Interaction>> graph;
    private HashMap<Integer, UndirectedUser> users;
    private ArrayList<Integer> NthMostActiveUser;

    /*
    Rep Invariant: For every User in the set users, User.getUserID()
                   is in graph.keySet()
                   For every userID in graph.keySet(), there exists
                   a User in users whose ID is userID
                   For every interaction graph.get(user1).get(user2)
                   the same interaction exists under graph.get(user2).get(user1)
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
    public UDWInteractionGraph(String fileName) {
        users = new HashMap<>();
        graph = new Hashtable<>();
        ArrayList<Email> emailDataRaw = ReadingMethods.readerFunction(fileName);
        for (Email email:emailDataRaw) {
            addEmail(email);
        }
        users = UserBuildingHelpers.createUserMapUDW(graph);
        NthMostActiveUser = UserBuildingHelpers.createUsersSortedByActivityUDW(new HashMap<>(users));
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
        int lowerBound = timeFilter[0], upperbound = timeFilter[1];
        graph = new Hashtable<>();
        users = new HashMap<>();
        for (Integer user1 : inputUDWIG.graph.keySet()) {
            for (Integer user2: inputUDWIG.graph.get(user1).keySet()) {

                Interaction unfilteredInteraction = inputUDWIG.graph.get(user1).get(user2);
                if(!(unfilteredInteraction.getMinTime() > upperbound ||
                        unfilteredInteraction.getMaxTime() < lowerBound))
                {
                    Interaction filteredInteraction =
                            new Interaction(unfilteredInteraction, lowerBound, upperbound);
                    addInteraction(user1, user2, filteredInteraction);
                    addInteraction(user2, user1, filteredInteraction);
                }
            }
        }
        users = UserBuildingHelpers.createUserMapUDW(graph);
        NthMostActiveUser = UserBuildingHelpers.createUsersSortedByActivityUDW(new HashMap<>(users));
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
            if(inputUDWIG.users.containsKey(userID)) {
                usersInFinalMap.addAll(inputUDWIG.users.get(userID).getSetOfAdjacentUsers());
            }
        }
        for (Integer user : inputUDWIG.graph.keySet()) {
             if(!(usersInFinalMap.contains(user))){
             graph.remove(user);
            }
        }
        Hashtable<Integer, Hashtable<Integer, Interaction>> nextGraphForIter = new Hashtable<>(graph);
        for (Integer user : nextGraphForIter.keySet()) {
            for (Integer userInteractedWith : nextGraphForIter.get(user).keySet()) {
                if(!(usersInFinalMap.contains(userInteractedWith))){
                    graph.get(user).remove(userInteractedWith);
                }
            }
        }
        users = UserBuildingHelpers.createUserMapUDW(graph);
        NthMostActiveUser = UserBuildingHelpers.createUsersSortedByActivityUDW(new HashMap<>(users));
    }

    /**
     * Creates a new UDWInteractionGraph from a DWInteractionGraph object.
     *
     * @param inputDWIG a DWInteractionGraph object
     */
    public UDWInteractionGraph(DWInteractionGraph inputDWIG) {
        HashSet<Integer> userList = new HashSet<>();
        graph = new Hashtable<>();
        users = new HashMap<>();
        NthMostActiveUser = new ArrayList<>();
        userList = inputDWIG.getUserIDs();
        for (Integer user : userList) {
            graph.put(user, new Hashtable<>());
            for(Integer user2 : userList){
                if(inputDWIG.isInteractive(user, user2) && inputDWIG.isInteractiveReceiver(user, user2)){
                    Interaction interaction = new Interaction(inputDWIG.getUserInteraction(user,user2), inputDWIG.getUserInteractionReceiver(user,user2));
                    addInteraction(user, user2, interaction);
                    addInteraction(user2, user, interaction);
                }
                if(inputDWIG.isInteractive(user,user2)){
                    addInteraction(user, user2, inputDWIG.getUserInteraction(user, user2));
                    addInteraction(user2, user, inputDWIG.getUserInteraction(user, user2));
                }
                if(inputDWIG.isInteractiveReceiver(user,user2)){
                    addInteraction(user, user2, inputDWIG.getUserInteractionReceiver(user, user2));
                    addInteraction(user2, user, inputDWIG.getUserInteractionReceiver(user, user2));
                }
            }
        }
        users = UserBuildingHelpers.createUserMapUDW(graph);
        NthMostActiveUser = UserBuildingHelpers.createUsersSortedByActivityUDW(new HashMap<>(users));
    }

    /**
     * Given an email, adds an interaction to the graph.
     *
     * @param email an Email object
     */
    private void addEmail(Email email){
        int user1 = email.getSender();
        int user2 = email.getReceiver();
        int time = email.getTimeStamp();
        addInteractionTime(user1, user2, time);
        if (user1 != user2){
            addInteractionTime(user2, user1, time);
        }
    }

    /**
     * Adds a single interaction between user1 and user2 at
     * a given time.
     *
     * @param user1 the userID of the first user
     * @param user2 the userID of the second user
     * @param time the time of the interaction
     */
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
            Hashtable<Integer, Interaction> newUser1Table = new Hashtable<>();
            newUser1Table.put(user2, new Interaction(time));
            graph.put(user1, newUser1Table);
        }
    }

    /**
     * Adds an interaction object between user1 and user2
     * to the graph.
     *
     * @param user1 the userID of the first user
     * @param user2 the userID of the second user
     * @param interaction the interaction object to add
     */
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
        if(!(users.containsKey(user1) && users.containsKey(user2))){
            return 0;
        }
        return graph.get(user1).get(user2).getInteractionCount();
    }

    /**
     * @param timeWindow is an int array of size 2 [t0, t1]
     *                   where t0<=t1
     * @return an int array of length 2, with the following structure:
     *  [NumberOfUsers, NumberOfEmailTransactions]
     */
    public int[] ReportActivityInTimeWindow(int[] timeWindow) {
       UDWInteractionGraph filtered = new UDWInteractionGraph(this, timeWindow);
       int[] activity = new int[2];
       activity[0] = filtered.users.keySet().size();
       int sum = 0;
        for (Integer user1: filtered.graph.keySet()) {
            for (Integer user2: filtered.graph.keySet()) {
                if(filtered.graph.get(user1).containsKey(user2)){
                    sum += filtered.graph.get(user1).get(user2).getInteractionCount();
                    if(Objects.equals(user1, user2)){
                        sum += filtered.graph.get(user1).get(user2).getInteractionCount();
                    }
                }
            }
        }
       activity[1] = sum/2;
        return activity;
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
        int[] report = new int[2];
        if(!(users.keySet().contains(userID))){
            report[0]=0;
            report[1]=0;
        } else {
            report[0] = users.get(userID).getTotalInteractions();
            report[1] = users.get(userID).getSetOfAdjacentUsers().size();
        }
        return report;
    }

    /**
     * @param N a positive number representing rank. N=1 means the most active.
     * @return the User ID for the Nth most active user
     */
    public int NthMostActiveUser(int N) {
        if (N >= NthMostActiveUser.size()){
            return -1;
        }
        return NthMostActiveUser.get(N-1);
    }


    /**
     * @return the number of completely disjoint graph
     *    components in the UDWInteractionGraph object.
     */
    public int NumberOfComponents() {
        if (users.isEmpty()){
            return 0;
        }
        int numComponents = 0;
        HashSet<Integer> seenUsers = new HashSet<>();
        for (Integer userID : users.keySet()) {
            if (!seenUsers.contains(userID)){
                HashSet<Integer> nextComponent = SearchAlgorithms.
                        findComponent(users.get(userID), new HashMap<>(users));
                seenUsers.addAll(nextComponent);
                numComponents++;
            }
        }
        return numComponents;
    }

    /**
     * @param userID1 the user ID for the first user
     * @param userID2 the user ID for the second user
     * @return true if a path exists between the users
     *         false otherwise
     */
    public boolean PathExists(int userID1, int userID2) {
        return SearchAlgorithms.pathExists(users.get(userID1), users.get(userID2), new HashMap<>(users));
    }
}
