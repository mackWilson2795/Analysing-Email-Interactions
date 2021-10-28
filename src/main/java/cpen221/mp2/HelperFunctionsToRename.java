package cpen221.mp2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;

public class HelperFunctionsToRename {

    /** Reader function
     * Give it an input file (.txt)
     * creates a list of emails (sender, receiver, timestamp)
     *
     */
    public static ArrayList<Email> readerFunction(String fileName) {
        ArrayList<Email> emailList = new ArrayList<>();
        try {
            String rawData = fileReader(fileName);
            BreakIterator iterator = BreakIterator.getLineInstance();
            int start = iterator.first();

            for (int end = iterator.next();
                 end != BreakIterator.DONE;
                 start = end, end = iterator.next()){

            }
        } catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("Error reading data file! Check that the file path and name is correct.");
        }

        return null;
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
