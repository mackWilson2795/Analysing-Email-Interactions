package cpen221.mp2.Users;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractUser {
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
    public HashSet<Integer> setOfUsersInteractedWith() {
         HashSet<Integer> userSet = new HashSet<>();
         userSet.addAll(uniqueUsersInteractedWith);
         return userSet;
    }

    // Override equals and hashcode (hashcode == userID)
    @Override
    public boolean equals(Object that){
         if (that instanceof AbstractUser){
             if (this.getUserID() == ((AbstractUser) that).getUserID()){
                 return true;
             }
         }
         return false;
    }

    @Override
    public int hashCode() {
         return getUserID();
    }

    }
