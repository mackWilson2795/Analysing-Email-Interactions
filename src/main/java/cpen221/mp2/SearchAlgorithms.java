package cpen221.mp2;

import cpen221.mp2.Users.AbstractUser;

import java.util.*;

public class SearchAlgorithms {
    private static Map<Integer, AbstractUser> userMap;
    private static HashSet<Integer> seen;
    private static List<Integer> searchOrder;
    private static boolean found;

    // todo: this could take in ints as well - decide what is easiest
    public static List<Integer> DFS(AbstractUser startUser, AbstractUser endUser, Set<AbstractUser> setOfAllUsers){
        //todo: revise
        // Checks to make sure the users exist in the graph
        if (!setOfAllUsers.contains(startUser) || !setOfAllUsers.contains(endUser)){
            return null;
        }

        found = false;
        userMap = new HashMap<>();
        for (AbstractUser user : setOfAllUsers) {
            userMap.put(user.getUserID(), user);
        }
        seen = new HashSet<>();
        searchOrder = new ArrayList<>();

        recursiveDFSHelper(startUser, endUser);

        if (found) {
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
}
