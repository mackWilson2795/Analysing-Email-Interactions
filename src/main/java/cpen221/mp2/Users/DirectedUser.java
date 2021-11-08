package cpen221.mp2.Users;

import cpen221.mp2.SendOrReceive;

import java.util.HashSet;

public class DirectedUser extends AbstractUser {
    private int sent = 0;
    private int received = 0;
    private HashSet<Integer> uniqueUsersSentTo;
    private HashSet<Integer> uniqueUsersReceivedFrom;

    /* Rep invariant:
         uniqueUsersInteractedWith == union of uniqueUsersSentTo and uniqueUsersReceivedFrom
     */

    /**
     * Constructs a new directed user given an identifier
     *
     * @param id identifier associated with the specific user
     */
    public DirectedUser (int id) {
        super(id);
    }

    /**
     * Constructs a new directed user from another directed user
     *
     * @param user a directed user object
     */
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

    /**
     * Document the sending of a given number of emails from
     * this user to another user, given the receiver's ID.
     *
     * @param receiver the ID associated with the receiver
     * @param numEmails the number of emails to be sent
     *                  requires: numEmails is non-negative //TODO: might be able to remove this requires
     */
    public void sendEmail (int receiver, int numEmails){
        super.uniqueUsersInteractedWith.add(receiver);
        uniqueUsersSentTo.add(receiver);
        sent += numEmails;
    }

    /**
     * Document the receiving of a given number of emails
     * from another user, given the sender's ID.
     *
     * @param sender the ID associated with the sender
     * @param numEmails the number of emails to be sent
     *                  requires: numEmails is non-negative
     */
    public void receiveEmail (int sender, int numEmails){
        super.uniqueUsersInteractedWith.add(sender);
        uniqueUsersReceivedFrom.add(sender);
        received += numEmails;
    }

    /**
     * Obtain the set of all other users this user has received
     * emails from.
     *
     * @return a set of integers corresponding to all the users
     *         which have emailed this user.
     */
    public HashSet<Integer> getUniqueUsersReceivedFrom(){
        return new HashSet<>(uniqueUsersReceivedFrom);
    }

    /**
     * Obtain the set of all other users this user has sent
     * emails to.
     *
     * @return a set of integers corresponding to all the users
     *         this user has emailed.
     */
    public HashSet<Integer> getUniqueUsersSentTo(){
        return new HashSet<>(uniqueUsersSentTo);
    }

    /**
     * // TODO: this ok?
     * @return the total number of emails this user has sent and received
     */
    public int getTotalInteractions(){
        return sent + received;
    }

    /**
     *
     * @return the total number of emails this user has sent
     */
    public int getSent(){
        return sent;
    }

    /**
     *
     * @return the total number of emails this user has received
     */
    public int getReceived(){
        return received;
    }
}
