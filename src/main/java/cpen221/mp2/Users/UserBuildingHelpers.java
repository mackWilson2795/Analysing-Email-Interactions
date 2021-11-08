package cpen221.mp2.Users;

import cpen221.mp2.InternalFrameworks.Interaction;
import cpen221.mp2.SendOrReceive;
import cpen221.mp2.Users.UserComparators.receiverSortDW;
import cpen221.mp2.Users.UserComparators.sentSortDW;
import cpen221.mp2.Users.UserComparators.sortUDW;

import java.util.*;

public class UserBuildingHelpers {

    /**
     * Produces a map of userIDs -> UndirectedUsers given a UDW graph
     *
     * @param graph an undirected graph of interactions
     * @return a map of userIDs -> UndirectedUsers
     */
    public static HashMap<Integer, UndirectedUser> createUserMapUDW (Hashtable<Integer,
                                               Hashtable<Integer, Interaction>> graph){
        HashMap<Integer, UndirectedUser> userMap = new HashMap<>();

        for (Integer user1 : graph.keySet()) {
            userMap.put(user1, new UndirectedUser(user1));
            for (Integer user2: graph.get(user1).keySet()) {
                userMap.get(user1).interactWithUser(user2, graph.get(user1).get(user2).getInteractionCount());
            }
        }
        return userMap;
    }

    /**
     * Produces a map of userIDs -> DirectedUsers from a DW graph
     *
     * @param senderGraph a directed graph of interactions
     * @return a map of userIDs -> DirectedUsers
     */
    public static HashMap<Integer, DirectedUser> createUserMapDW (Hashtable<Integer,
                                     Hashtable<Integer, Interaction>> senderGraph) {
        HashMap<Integer, DirectedUser> userMap = new HashMap<>();
        for (Integer sender: senderGraph.keySet()) {
            if(!(userMap.containsKey(sender))){
                userMap.put(sender, new DirectedUser(sender));
            }
            for(Integer receiver : senderGraph.get(sender).keySet()){
                int numEmails = senderGraph.get(sender).get(receiver).getInteractionCount();
                userMap.get(sender).sendEmail(receiver, numEmails);
                if(!(userMap.containsKey(receiver))){
                    userMap.put(receiver, new DirectedUser(receiver));
                }
                userMap.get(receiver).receiveEmail(sender,numEmails);
            }
        }
        return userMap;
    }

    /**
     * Produces a list of userIDs sorted by the activity of the users.
     * Where activity represents the total number of emails sent and
     * received.
     * The users in the resulting list are sorted in ascending order
     * based on activity, in cases where two users have the same
     * number of total interactions the user with the lower userID
     * will be at the lower index.
     *
     * @param users a map of userIDs -> UndirectedUsers
     * @return a sorted ArrayList of userIDs
     */
    public static ArrayList<Integer> createUsersSortedByActivityUDW(HashMap<Integer, UndirectedUser> users) {
        List<User> userList = new ArrayList<>();
        for (Integer user : users.keySet()) {
            userList.add(users.get(user));
        }
        userList.sort(new sortUDW());

        ArrayList<Integer> userIDsSortedByActivity = new ArrayList<>();
        for (User user : userList) {
            userIDsSortedByActivity.add(user.getUserID());
        }
        return userIDsSortedByActivity;
    }

    /**
     * Produces a list of userIDs sorted by the activity of the users.
     * Where activity represents either the total number of emails sent
     * or received, determined by the given {@code interactionType}.
     * The users in the resulting list are sorted in ascending order
     * based on activity, in cases where two users have the same
     * number of sent/received emails the user with the lower userID
     * will be at the lower index.
     *
     * @param users a map of userIDs -> DirectedUsers
     * @param interactionType the direction of interaction to sort by
     * @return an ArrayList of userIDs sorted by interactionType
     */
    public static ArrayList<Integer> createUsersSortedByActivityDW(HashMap<Integer, DirectedUser> users,
                                                                   SendOrReceive interactionType) {
        List<DirectedUser> userList = new ArrayList<>();
        for (Integer user : users.keySet()) {
            userList.add(users.get(user));
        }
        if(interactionType == SendOrReceive.RECEIVE){
            userList.sort(new receiverSortDW());
        }
        if(interactionType == SendOrReceive.SEND){
            userList.sort(new sentSortDW());
        }
        ArrayList<Integer> userIDsSortedByActivity = new ArrayList<>();
        for (User user : userList) {
            userIDsSortedByActivity.add(user.getUserID());
        }
        return userIDsSortedByActivity;
    }
}

