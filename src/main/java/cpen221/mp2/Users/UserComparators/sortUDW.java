package cpen221.mp2.Users.UserComparators;

import cpen221.mp2.Users.User;

import java.util.Comparator;

public class sortUDW implements Comparator<User> {
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
