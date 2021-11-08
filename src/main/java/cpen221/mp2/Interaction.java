package cpen221.mp2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Hashtable;
import java.util.TreeSet;

public class Interaction{

    private int interactionNumber;
    private TreeSet<Integer> times;
    private ArrayList<Integer> alltimes;

    public Interaction(){
        interactionNumber = 0;
    }

    public Interaction(int time){

        times = new TreeSet<Integer>();
        times.add(time);
        alltimes = new ArrayList<Integer>();
        alltimes.add(time);
        interactionNumber = 1;

    }

/*    public Interaction(int interactions, Set<Integer> times){
        for(Integer time: times){
            this.times.add(time);
        }
        interactionNumber = interactions;

    }*/
    public Interaction(int interactions, ArrayList<Integer> times){

        if (alltimes == null){
            alltimes = new ArrayList<Integer>();
        }
        if(this.times == null){
            this.times = new TreeSet<Integer>();
        }

        for(Integer time: times){
            alltimes.add(time);
            this.times.add(time);
        }
        interactionNumber = interactions; //be careful

    }


    public void addInteraction(int time){

        if (alltimes == null){
            alltimes = new ArrayList<Integer>();
        }
        if(times == null){
            times = new TreeSet<Integer>();
        }

        times.add(time);
        alltimes.add(time);
        interactionNumber++;

    }

    public int getInteractionCount(){
        return interactionNumber;
    }

    public List<Integer> getTimes() {

            List<Integer> copy = new ArrayList<Integer>();
            for (Integer time : times) {
                copy.add(time);
            }
            return copy;
    }





}