package cpen221.mp2;
import java.util.HashSet;
import java.util.Set;
import java.util.Hashtable;

public class Interaction{

    private Hashtable<String, Integer> userInteracts;
    private int totalInteractions;
    private String receiver;
    private HashSet<String> receivers;
    int numinteracts;


    public Interaction(){
        totalInteractions = 0;
    }

    public Interaction(String receiver){
        if (userInteracts.containsKey(receiver)){
            numinteracts = userInteracts.get(receiver);
            numinteracts++;
            userInteracts.remove(receiver);
            userInteracts.put(receiver, numinteracts);
            totalInteractions++;
        }
        else{
            userInteracts.put(receiver, 0);
            totalInteractions++;
            receivers.add(receiver);
        } //replace with add()
    }
//add getter methods

    public void add(String receiver){
        if (userInteracts.containsKey(receiver)){
            numinteracts = userInteracts.get(receiver);
            numinteracts++;
            userInteracts.remove(receiver);
            userInteracts.put(receiver, numinteracts);
            totalInteractions++;
        }
        else{
            userInteracts.put(receiver, 0);
            totalInteractions++;
            receivers.add(receiver);
        }

    }





}
