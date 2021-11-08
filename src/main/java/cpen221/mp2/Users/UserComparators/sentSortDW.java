package cpen221.mp2.Users.UserComparators;

import cpen221.mp2.SendOrReceive;
import cpen221.mp2.Users.DirectedUser;

import java.util.Comparator;

public class sentSortDW implements Comparator<DirectedUser> {
    public int compare(DirectedUser user1, DirectedUser user2){
        if(user1.getSent() < user2.getSent()){
            return 1;
        } else if(user2.getSent() < user1.getSent()){
            return -1;
        }else {
            return Integer.compare(user1.getUserID(), user2.getUserID());
        }
    }
}
