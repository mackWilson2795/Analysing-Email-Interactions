package cpen221.mp2.Users.UserComparators;

import cpen221.mp2.SendOrReceive;
import cpen221.mp2.Users.DirectedUser;

import java.util.Comparator;

public class receiverSortDW implements Comparator<DirectedUser> {
    /**
     * Compares two DirectedUsers by the amount of emails they
     * have received. First compares number of emails sent, in
     * the case that both users have received the same number of emails
     * then compares userID numbers.
     *
     * @param user1 the first user
     * @param user2 the second user
     * @return the result of the comparison
     */
    public int compare(DirectedUser user1, DirectedUser user2){
            if(user1.getReceived() < user2.getReceived()){
                return 1;
            } else if(user2.getReceived() < user1.getReceived()){
                return -1;
            } else{
                return Integer.compare(user1.getUserID(), user2.getUserID());
            }
    }
}
