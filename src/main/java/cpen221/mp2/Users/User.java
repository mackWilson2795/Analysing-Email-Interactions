package cpen221.mp2.Users;

import java.util.HashSet;

public interface User {

    // TODO: specs

    int getUserID();

    int getTotalInteractions();

    HashSet<Integer> getSetOfInteractedUsers();

    @Override
    boolean equals(Object that);

    @Override
    int hashCode();

}
