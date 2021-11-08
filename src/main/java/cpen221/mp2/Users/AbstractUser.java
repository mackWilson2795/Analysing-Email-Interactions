package cpen221.mp2.Users;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractUser implements User {
    protected Set<Integer> setOfAdjacentUsers;
    protected int userID;

    // TODO: rep invars or abs functs?
    /* Abstraction Function:
        setOfAdjacentUsers = the set of all users which this user has
                             interacted with.
        userID = the unique identifier associated with the user
     */

    /* Rep invariant:
         hashCode() == userID
         equals() must compare user IDs, if two users have the same
                  id they must be considered the same user.
     */

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

    /**
     * Indicates whether two user objects represent the same user.
     */
    @Override
    public boolean equals(Object that){
         if (that instanceof AbstractUser){
             return this.getUserID() == ((AbstractUser) that).getUserID();
         }
         return false;
    }

    /**
     * Creates a hash code value for this user.
     * @return a hash code value for this user.
     */
    @Override
    public int hashCode() {
         return getUserID();
    }

    }
