package cpen221.mp2.Users;

import cpen221.mp2.SendOrReceive;

import java.util.HashSet;

public interface User {

    /**
     * Obtain the user ID number of the user
     *
     * @return the user ID
     */
    int getUserID();

    /** TODO: this ok?
     * @return the total number of emails this user has sent and received
     */
    int getTotalInteractions();

    /**
     * Obtain the set of all unique users that the given user has interacted with
     *
     * @return a Set of user ID numbers corresponding to all others the user has
     *         interacted with
     */
    HashSet<Integer> getSetOfAdjacentUsers();

    // TODO: specs for these
    @Override
    boolean equals(Object that);

    @Override
    int hashCode();
}
