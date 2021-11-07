package cpen221.mp2;

import cpen221.mp2.Users.UndirectedUser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class TEMP_UserBuildingHelpers {

    private static Hashtable<Integer, Hashtable<Integer, Interaction>> graph;
    private static HashMap<Integer, UndirectedUser> users;

    public static void createUserMap (){
        for (Integer user1 : graph.keySet()) {
            for (Integer user2: graph.get(user1).keySet()) {
               // graph.get(user1).get(user2)
            }
        }
    }
}
