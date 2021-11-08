package cpen221.mp2;

import cpen221.mp2.Users.UndirectedUser;
import cpen221.mp2.Users.User;

import java.util.*;

public class SearchAlgorithms {
    private static Map<Integer, User> userMap;
    private static HashSet<Integer> seen;
    private static List<Integer> searchOrder;
    private static boolean found;

    /**
     * Performs a depth first search on a Map of user ids -> users
     * when a user has interacted with many other users, will search
     * in the direction of the lowest user ID first.
     *
     * @param startUser the user to begin searching from
     * @param endUser the user to search to
     * @param mapOfAllUsers a Map of user ids -> users
     * @return a list of integers which corresponds to the order
     *         the users in the map were visited during the search
     */
    private static List<Integer> DFS(User startUser, User endUser, Map<Integer, User> mapOfAllUsers){

        found = false;
        userMap = new HashMap<>(mapOfAllUsers);
        seen = new HashSet<>();
        searchOrder = new ArrayList<>();

        if (mapOfAllUsers.isEmpty() || !mapOfAllUsers.containsKey(startUser.getUserID())){
            return null;
        }

        recursiveDFSHelper(startUser, endUser);

        if (found) {
            return searchOrder;
        } else {
            return null;
        }
    }

    /**
     * Helper function to run the DFS search
     *
     * @param startUser the user to start this branch of the search from
     * @param endUser the target user
     */
    private static void recursiveDFSHelper(User startUser, User endUser){
        seen.add(startUser.getUserID());
        searchOrder.add(startUser.getUserID());

        if (startUser.equals(endUser)){
            found = true;
            return;
        }

        List<Integer> adjacentUsers = new ArrayList<>();
        for (Integer i: startUser.getSetOfAdjacentUsers()) {
            if (!seen.contains(i)) {
                adjacentUsers.add(i);
            }
        }

        if (adjacentUsers.size() > 0) {
            Collections.sort(adjacentUsers);
            for (Integer adjacentUser : adjacentUsers) {
                if (found) {
                    break;
                }
                recursiveDFSHelper(userMap.get(adjacentUser), endUser);
            }
        }
    }

    /**
     * Determine if a path exists between two users given both users
     * and a map containing all the users in their graph.
     *
     * @param user1 the user to start searching from
     * @param user2 the user to search to
     * @param mapOfAllUsers a map of userIDs -> Users which
     *                     contains all users in the graph
     * @return true if a path exists between user1 and user2
     *         false otherwise
     */
    public static boolean pathExists (User user1, User user2, Map<Integer, User> mapOfAllUsers){
        if (mapOfAllUsers.keySet().isEmpty() || !mapOfAllUsers.containsKey(user1.getUserID())
                                             || !mapOfAllUsers.containsKey(user2.getUserID())){
            return false;
        }

        DFS(user1, user2, mapOfAllUsers);

        return found;
    }

    /**
     * Determine the full component that a User is a member of.
     *
     * @param user the user to find the component of
     * @param mapOfAllUsers a map of userIDs -> Users which
     *                      contains all users in the graph
     * @return a HashSet containing all userIDs in the user's component
     */
    public static HashSet<Integer> findComponent (User user, Map<Integer, User> mapOfAllUsers){
        if (mapOfAllUsers.keySet().isEmpty() || !mapOfAllUsers.containsKey(user.getUserID())){
            return new HashSet<>(user.getUserID());
        }
        DFS(user, new UndirectedUser(-1), mapOfAllUsers);
        return new HashSet<>(seen);
    }

    /**
     * Perform a depth first search on a Directed Interaction Graph
     * given a user to start searching from and a user to search towards.
     * When given an option of multiple users, the search will always favor
     * searching towards the user with the lowest userID number first.
     *
     * @param user1 the user to begin searching from
     * @param user2 the user to search for
     * @param mapOfAllUsers a map of userIDs -> Users which
     *                      contains all users in the graph
     * @return a list of integers which corresponds to the order
     *         the users in the map were visited during the search
     */
    public static List<Integer> directedDFS (User user1, User user2, Map<Integer, User> mapOfAllUsers){
        return DFS(user1, user2, mapOfAllUsers);
    }
}
