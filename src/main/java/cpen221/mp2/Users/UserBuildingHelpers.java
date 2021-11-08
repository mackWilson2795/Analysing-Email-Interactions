package cpen221.mp2.Users;

import cpen221.mp2.InternalFrameworks.Interaction;
import cpen221.mp2.SendOrReceive;

import java.util.*;

public class UserBuildingHelpers {
//TODO: removal
    private static Hashtable<Integer, Hashtable<Integer, Interaction>> graph;
    private static HashMap<Integer, UndirectedUser> users;
    private static ArrayList<Integer> usersSortedByActivity;


    // TODO: getsent/ getrecieved replace with get interactions that takes in enum
    public static HashMap<Integer, UndirectedUser> createUserMapUDW (Hashtable<Integer, Hashtable<Integer
            , Interaction>> graph){
        HashMap<Integer, UndirectedUser> userMap = new HashMap<>();

        for (Integer user1 : graph.keySet()) {
            userMap.put(user1, new UndirectedUser(user1));
            for (Integer user2: graph.get(user1).keySet()) {
                userMap.get(user1).interactWithUser(user2, graph.get(user1).get(user2).getInteractionCount());
            }
        }
        return userMap;
    }
    public static HashMap<Integer, DirectedUser> createUserMapDW
            (Hashtable<Integer, Hashtable<Integer, Interaction>> senderGraph) {
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

    public static ArrayList<Integer> createUsersSortedByActivityUDW(HashMap<Integer, UndirectedUser> users) {
        List<User> userList = new ArrayList<>();
        for (Integer user : users.keySet()) {
            userList.add(users.get(user));
        }
        userList.sort(new sortByActivity());

        ArrayList<Integer> userIDsSortedByActivity = new ArrayList<>();
        for (User user : userList) {
            userIDsSortedByActivity.add(user.getUserID());
        }
        return userIDsSortedByActivity;
    }
}

class sortByActivity implements Comparator<User> {
    public int compare(User user1, User user2){
        if (user1.getTotalInteractions() < user2.getTotalInteractions()){
            return 1;
        } else if (user2.getTotalInteractions() < user1.getTotalInteractions()) {
            return -1;
        } else {
            return Integer.compare(user1.getUserID(), user2.getUserID());
        }
    }

    public int compare(DirectedUser user1, DirectedUser user2, SendOrReceive direction){
        if(direction == SendOrReceive.SEND){
            if(user1.getSent() < user2.getSent()){
                return 1;
            } else if(user2.getSent() < user1.getSent()){
                return -1;
            }
        }
        if(direction == SendOrReceive.RECEIVE){
            if(user1.getReceived() < user2.getReceived()){
                return 1;
            } else if(user2.getReceived() < user1.getReceived()){
                return -1;
            }
        }
        return Integer.compare(user1.getUserID(), user2.getUserID());
    }
}
