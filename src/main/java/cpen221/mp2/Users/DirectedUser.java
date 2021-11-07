package cpen221.mp2.Users;

public class DirectedUser extends AbstractUser {
    private int sent = 0;
    private int received = 0;

    public DirectedUser (int id) {
        super(id);
    }

    public void sendEmail (int receiver){
        super.uniqueUsersInteractedWith.add(receiver);
        sent++;
    }

    public void receiveEmail (int sender){
        super.uniqueUsersInteractedWith.add(sender);
        received++;
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
