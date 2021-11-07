package cpen221.mp2.Users;

import java.util.HashSet;

public class UndirectedUser extends AbstractUser {
    private int totalInteractions = 0;

    public UndirectedUser(int id){
        super(id);
    }

    public UndirectedUser(UndirectedUser user){
        super(user.userID);
        totalInteractions = user.totalInteractions;
        uniqueUsersInteractedWith = new HashSet<>(user.uniqueUsersInteractedWith);
    }

    public UndirectedUser(DirectedUser user) {
        super(user.userID);
        totalInteractions = user.getTotalInteractions();
        uniqueUsersInteractedWith = new HashSet<>(user.uniqueUsersInteractedWith);
    }

    public void interactWithUser(int id, int numEmails) {
        super.uniqueUsersInteractedWith.add(id);
        totalInteractions += numEmails;
    }

    // getInteractions
    public int getTotalInteractions(){
        return totalInteractions;
    }
}
