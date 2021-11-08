package cpen221.mp2.Users;

import cpen221.mp2.Interaction;

import java.util.*;

public class TEMP_UserBuildingHelpers {

    private static Hashtable<Integer, Hashtable<Integer, Interaction>> graph;
    private static HashMap<Integer, UndirectedUser> users;
    private static ArrayList<Integer> usersSortedByActivity;

    public static void createUserMap (){
        Map<Integer, UndirectedUser> userMap = new HashMap<>();
        for (Integer user1 : graph.keySet()) {
            userMap.put(user1, new UndirectedUser(user1));
            for (Integer user2: graph.get(user1).keySet()) {
                userMap.get(user1).interactWithUser(user2, graph.get(user1).get(user2).getInteractionCount());
            }
        }
    }

    public static void createUsersSortedByActivity() {
        // TODO: double check logic here - im tired
        List<UndirectedUser> userList = new ArrayList<>();
        for (Integer user : users.keySet()) {
            userList.add(users.get(user));
        }
        userList.sort(new sortByActivity());

        for (User user : userList) {
            usersSortedByActivity.add(user.getUserID());
        }

        /*
        // TODO: remove me
        if (userList.size() > 0){
            usersSortedByActivity.add(userList.get(1).getUserID());
            int lastValue = userList.get(1).getTotalInteractions();
            for (int i = 1; i < userList.size(); i++) {
                if (userList.get(i).getTotalInteractions() != lastValue) {
                    usersSortedByActivity.add(userList.get(i).getUserID());
                    lastValue = userList.get(i).getTotalInteractions();
                }
            }
        }
         */
    }
}

class sortByActivity implements Comparator<User> {
    public int compare(User user1, User user2){
        if (user1.getTotalInteractions() < user2.getTotalInteractions()){
            return -1;
        } else if (user2.getTotalInteractions() < user1.getTotalInteractions()) {
            return 1;
        } else {
            // TODO: it wants me to use Integer.compare()...
            if (user1.getUserID() < user2.getUserID()){
                return 1;
            } else if (user2.getUserID() < user1.getUserID()){
                return -1;
            } else {
                return 0;
            }
        }
    }
}
