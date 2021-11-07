package cpen221.mp2.Users;

public class UndirectedUser extends AbstractUser {
    private int totalInteractions = 0;

    public UndirectedUser(int id){
        super(id);
    }

    public void interactWithUser(int id, int numEmails) {
        super.uniqueUsersInteractedWith.add(id);
        totalInteractions+= numEmails;
    }

    // getInteractions
    public int getTotalInteractions(){
        return totalInteractions;
    }
}
