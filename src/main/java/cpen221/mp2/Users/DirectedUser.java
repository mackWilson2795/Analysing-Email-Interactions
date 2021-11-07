package cpen221.mp2.Users;

import java.util.HashSet;

public class DirectedUser extends AbstractUser {
    private int sent = 0;
    private int received = 0;
    private HashSet<Integer> uniqueUsersSentTo;
    private HashSet<Integer> uniqueUsersReceivedFrom;

    // Rep invariant:
    //     uniqueUsersInteractedWith == union of uniqueUsersSentTo and uniqueUsersReceivedFrom

    // TODO: should be a different map for users emailed to for directed user

    public DirectedUser (int id) {
        super(id);
    }

    public DirectedUser(DirectedUser user){
        super(user.userID);
        sent = user.sent;
        received = user.received;
        uniqueUsersInteractedWith = new HashSet<>(user.uniqueUsersInteractedWith);
        uniqueUsersSentTo = new HashSet<>(user.uniqueUsersSentTo);
        uniqueUsersReceivedFrom = new HashSet<>(user.uniqueUsersReceivedFrom);
    }

    // TODO: I want to turn these next two methods into overloads of the same method that takes an ENUM as a modifier
    //       to determine sent vs received - does that make this an invalid subtype?
    public void sendEmail (int receiver, int numEmails){
        super.uniqueUsersInteractedWith.add(receiver);
        uniqueUsersSentTo.add(receiver);
        sent += numEmails;
    }
    public void receiveEmail (int sender, int numEmails){
        super.uniqueUsersInteractedWith.add(sender);
        uniqueUsersReceivedFrom.add(sender);
        received += numEmails;
    }

    public HashSet<Integer> getUniqueUsersReceivedFrom(){
        return new HashSet<>(uniqueUsersReceivedFrom);
    }

    public HashSet<Integer> getUniqueUsersSentTo(){
        return new HashSet<>(uniqueUsersSentTo);
    }

    public int getTotalInteractions(){
        return sent + received;
    }

    // getSent
    public int getSent(){
        return sent;
    }

    // getReceiver
    public int getReceived(){
        return received;
    }
}
