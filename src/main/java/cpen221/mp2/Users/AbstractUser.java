package cpen221.mp2.Users;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractUser implements User {
    protected Set<Integer> setOfAdjacentUsers;
    protected int userID;

    // TODO: rep invars or abs functs?

    /**
     * Default constructor for a user
     *
     * @param id the identification number associated with the user
     */
     public AbstractUser(int id){
         userID = id;
         setOfAdjacentUsers = new HashSet<>();
     }

    /**
     * Obtain the user ID number of the user
     *
     * @return the user ID
     */
    public int getUserID(){
         return userID;
    }

    /**
     * Obtain the set of all unique users that the given user has interacted with
     *
     * @return a Set of user ID numbers corresponding to all others the user has
     *         interacted with
     */
    public HashSet<Integer> getSetOfAdjacentUsers() {
        return new HashSet<>(setOfAdjacentUsers);
    }

    //TODO: do i write specs for these two??

    @Override
    public boolean equals(Object that){
         if (that instanceof AbstractUser){
             return this.getUserID() == ((AbstractUser) that).getUserID();
         }
         return false;
    }

    @Override
    public int hashCode() {
         return getUserID();
    }

    }
