package cpen221.mp2.Users;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractUser implements User {
    protected Set<Integer> uniqueUsersInteractedWith;
    protected int userID;

    // constr
     public AbstractUser(int id){
         userID = id;
         uniqueUsersInteractedWith = new HashSet<>();
     }

    // getUserID
    public int getUserID(){
         return userID;
    }

    // setOfUsersInteractedWith
    public HashSet<Integer> getSetOfInteractedUsers() {
        return new HashSet<>(uniqueUsersInteractedWith);
    }

    // Override equals and hashcode (hashcode == userID)
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
