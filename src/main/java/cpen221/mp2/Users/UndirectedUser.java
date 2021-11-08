package cpen221.mp2.Users;

import java.util.HashSet;

public class UndirectedUser extends AbstractUser implements User {
    private int totalInteractions = 0;

    /* Abstraction Function:
        totalInteractions = the number of emails this user has sent and received
     */

    /* Rep invariant:
         totalInteractions >= super.setOfAdjacentUsers.size();
     */
    public boolean checkRep(){
        boolean rep = true;
        if (totalInteractions <= setOfAdjacentUsers.size()){
            rep = false;
        }
        return rep;
    }

    /**
     * Constructs a new undirected user given an identifier
     *
     * @param id identifier associated with the specific user
     */
    public UndirectedUser(int id){
        super(id);
    }

    /**
     * Document a given number of emails between this user
     * and another user, given the other's id and the number
     * of interactions.
     *
     * @param id the ID associated with the other user
     * @param numEmails the number of emails
     *                  requires: numEmails is non-negative
     */
    public void interactWithUser(int id, int numEmails) {
        super.setOfAdjacentUsers.add(id);
        totalInteractions += numEmails;
    }

    /**
     * Obtain the total number of interactions this user has
     * been involved in.
     *
     * @return the number of interactions
     */
    public int getTotalInteractions(){
        return totalInteractions;
    }
}
