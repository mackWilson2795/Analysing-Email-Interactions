package cpen221.mp2.InternalFrameworks;

import cpen221.mp2.Exceptions.InvalidEmailException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReadingMethods {

    /**
     *
     *
     *
     */
    // TODO: make spec
    public static ArrayList<Email> readerFunction(String fileName) {
        ArrayList<Email> emailList = new ArrayList<>();
        try {
            String rawData = fileReader(fileName);

            StringTokenizer reader = new StringTokenizer(rawData, "\n");

            while (reader.hasMoreTokens()){
                String nextEmail = reader.nextToken();
                emailList.add(new Email(nextEmail));
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
            rawData.append("\n");
        }
        reader.close();
        return rawData.toString();
    }
}
