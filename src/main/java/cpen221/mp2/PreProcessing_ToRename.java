package cpen221.mp2;

import cpen221.mp2.Exceptions.InvalidEmailException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;

public class PreProcessing_ToRename {

    /** Reader function
     * Give it an input file (.txt)
     * creates a list of emails (sender, receiver, timestamp)
     *
     */
    // TODO: make spec
    public static ArrayList<Email> readerFunction(String fileName) {
        ArrayList<Email> emailList = new ArrayList<>();
        try {
            String rawData = fileReader(fileName);
            BreakIterator iterator = BreakIterator.getLineInstance();
            int start = iterator.first();

            for (int end = iterator.next();
                 end != BreakIterator.DONE;
                 start = end, end = iterator.next()){
                emailList.add(new Email(rawData.substring(iterator.first(), iterator.next())));
            }
            // TODO: decide throw vs. catch of the InvalidEmailException
        } catch (IOException | InvalidEmailException ioe){
            ioe.printStackTrace();
            if (ioe instanceof IOException){
                System.out.println("Error reading data file at " + fileName
                        + " Check that the file path and name is correct.");
            }
            if (ioe instanceof InvalidEmailException){
                System.out.println("Detected issue with data formatting in " + fileName
                        + " ensure all lines contain three entries, corresponding to sender, receiver, and timestamp.");
            }
        }
        return emailList;
    }

    /**
     * Reads the text given a text file location
     * @param fileName path to file
     *                 requires: file path is accessible locally
     * @return String containing all text in the file
     * @throws IOException if unable to access file at given path
     */
    public static String fileReader(String fileName) throws IOException {
        StringBuilder rawData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        for (String fileLine = reader.readLine(); fileLine != null; fileLine = reader.readLine()) {
            rawData.append(fileLine);
        }
        reader.close();
        return rawData.toString();
    }
}
