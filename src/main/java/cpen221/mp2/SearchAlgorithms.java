package cpen221.mp2;

import cpen221.mp2.Users.AbstractUser;
import cpen221.mp2.Users.UndirectedUser;

import java.util.*;

public class SearchAlgorithms {
    private static Map<Integer, AbstractUser> userMap;
    private static HashSet<Integer> seen;
    private static List<Integer> searchOrder;
    private static boolean found;

    // todo: this could take in ints as well - decide what is easiest
    public static List<Integer> DFS(AbstractUser startUser, AbstractUser endUser, Map<Integer, AbstractUser> mapOfAllUsers){

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
        } else if (endUser.getUserID() == -1) {
            return searchOrder;
        } else {
            return null;
        }
    }

    public static void recursiveDFSHelper(AbstractUser startUser, AbstractUser endUser){
        // todo: remove
        //      Set<AbstractUser> adjacentUsers = new HashSet<>();
        seen.add(startUser.getUserID());
        searchOrder.add(startUser.getUserID());

        if (startUser.equals(endUser)){
            found = true;
            return;
        }

        // TODO: fix to work with directed
        List<Integer> adjacentUsers = new ArrayList<>();
        for (Integer i: startUser.getSetOfInteractedUsers()) {
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

    public static boolean pathExists (AbstractUser user1, AbstractUser user2, Map<Integer, AbstractUser> mapOfAllUsers){
        if (mapOfAllUsers.keySet().isEmpty() || !mapOfAllUsers.containsKey(user1.getUserID())
                                             || !mapOfAllUsers.containsKey(user2.getUserID())){
            return false;
        }

        DFS(user1, user2, mapOfAllUsers);

        return found;
    }

    public static HashSet<Integer> findComponent (AbstractUser user1, Map<Integer, AbstractUser> mapOfAllUsers){
        if (mapOfAllUsers.keySet().isEmpty() || !mapOfAllUsers.containsKey(user1.getUserID())){
            return new HashSet<>(user1.getUserID());
        }

        // TODO: very important to verify this
        DFS(user1, new UndirectedUser(-1), mapOfAllUsers);

        return new HashSet<>(seen);
    }
}
