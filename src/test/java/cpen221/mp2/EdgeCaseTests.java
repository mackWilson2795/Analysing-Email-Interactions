package cpen221.mp2;

import cpen221.mp2.Exceptions.InvalidEmailException;
import cpen221.mp2.InternalFrameworks.ReadingMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class EdgeCaseTests {

    private static UDWInteractionGraph udEmpty;
    private static DWInteractionGraph dwEmpty;

    @BeforeAll
    public static void setupTests() {
        udEmpty = new UDWInteractionGraph("resources/empty.txt");
        dwEmpty = new DWInteractionGraph("resources/empty.txt");
    }

    @Test
    public void emptyTests() {
        Assertions.assertEquals(0, dwEmpty.getEmailCount(0, 1));
        Assertions.assertEquals(0, udEmpty.getEmailCount(0, 1));
    }

    @Test
    public void exceptionTests() {
        System.out.println("\nThe following exceptions are intended for our testing:\n");
        try {
            ReadingMethods.readerFunction("resources/brokenFormatting.txt");
        } catch (NoSuchElementException e){
        }
        try {
            ReadingMethods.readerFunction("resources/brokenFormatting1.txt");
        } catch (NoSuchElementException e){
        }
        System.out.println("\nEnd of intended exceptions\n");

    }
}
