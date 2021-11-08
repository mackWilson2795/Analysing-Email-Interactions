package cpen221.mp2.Users;

import cpen221.mp2.InternalFrameworks.Interaction;

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
}
