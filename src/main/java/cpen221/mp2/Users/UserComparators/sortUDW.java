package cpen221.mp2.Users.UserComparators;

import cpen221.mp2.Users.User;

import java.util.Comparator;

public class sortUDW implements Comparator<User> {
    /**
     * Compares two DirectedUsers by the amount of interactions
     * they have been involved in. First compares number of interactions,
     * in the case that both users have the same number of interactions,
     * compares userID numbers.
     *
     * @param user1 the first user
     * @param user2 the second user
     * @return the result of the comparison
     */
    public int compare(User user1, User user2) {
        if (user1.getTotalInteractions() < user2.getTotalInteractions()) {
            return 1;
        } else if (user2.getTotalInteractions() < user1.getTotalInteractions()) {
            return -1;
        } else {
            return Integer.compare(user1.getUserID(), user2.getUserID());
        }
    }
}
