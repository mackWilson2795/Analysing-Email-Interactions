package cpen221.mp2.InternalFrameworks;



import cpen221.mp2.Exceptions.InvalidEmailException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Email {
    private Integer sender;
    private Integer receiver;
    private Integer timeStamp;

    /*
        Abstraction function:
        sender = id number of the sender of the email
        receiver = id number of the receiver of the email
        timeStamp = the time, in seconds, of the email
     */

    /*  Rep invariant:
            sender, receiver, and timeStamp should never be non-null.
     */
    public boolean checkRep(){
        boolean rep = true;
        if (sender == null || receiver == null || timeStamp == null){
            rep = false;
        }
        return rep;
    }

    /**
     * Creates an email from three integers:
     *
     * @param sender id of the sender
     * @param receiver id of the receiver
     * @param timeStamp timeStamp of the email
     */
    public Email (int sender, int receiver, int timeStamp){
        this.sender = sender;
        this.receiver = receiver;
        this.timeStamp = timeStamp;
    }

    /**
     * Creates an Email from a String containing at least three integers.
     * The first three integers in the string will be used, where the:
     *     First integer is the id of the sender
     *     Second integer is the id of the receiver
     *     Third integer is the timestamp of the email
     *
     * @param emailData String containing email information
     * @throws InvalidEmailException if the string does not contain at least
     *                               three values
     */
    public Email (String emailData) throws InvalidEmailException {
        Scanner scanner = new Scanner(emailData);
        try {
            int[] emailNumbers = new int[3];
            for (int i = 0; i < 3; i++){
                emailNumbers[i] = scanner.nextInt();
            }
            sender = emailNumbers[0];
            receiver = emailNumbers[1];
            timeStamp = emailNumbers[2];
        } catch (NoSuchElementException e){
            e.printStackTrace();
            throw new InvalidEmailException("Detected line with less than three entries" +
                    " ensure input data is formatted properly");
        }
        if (!checkRep()){
            throw new InvalidEmailException("Null email detected");
        }
    }

    /**
     * Returns the id of the sender of the email
     * @return id of sender
     */
    public int getSender (){
        return sender;
    }

    /**
     * Returns the id of the receiver of the email
     * @return id of receiver
     */
    public int getReceiver (){
        return receiver;
    }

    /**
     * Returns timeStamp of the email in seconds
     * @return id of timeStamp
     */
    public int getTimeStamp (){
        return timeStamp;
    }

}
