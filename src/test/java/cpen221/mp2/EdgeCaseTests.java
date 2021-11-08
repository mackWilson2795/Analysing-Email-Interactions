package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EdgeCaseTests {
    // TODO: fill this with tests on almost all methods when given an empty graph
    // TODO: invalid email exception

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
}
