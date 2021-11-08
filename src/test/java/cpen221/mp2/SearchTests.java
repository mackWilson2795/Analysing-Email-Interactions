package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SearchTests {

    private static DWInteractionGraph dwig1;
    private static DWInteractionGraph dwig2;
    private static DWInteractionGraph dwig3;
    private static UDWInteractionGraph udbig1;

    @BeforeAll
    public static void setupTests() {
        dwig1 = new DWInteractionGraph("resources/Task4Transactions1.txt");
        dwig2 = new DWInteractionGraph("resources/Task4Transactions2.txt");
        dwig3 = new DWInteractionGraph("resources/Task4Transactions3.txt");
        udbig1 = new UDWInteractionGraph("resources/email-Eu-core-temporal-Dept1.txt");
    }

    @Test
    public void testNumComponentLargeDataSet() {
        Assertions.assertEquals(9, udbig1.NumberOfComponents());
    }

    @Test
    public void searchPathImpossibles() {
        Assertions.assertEquals(false, udbig1.PathExists(9999999,1));
        Assertions.assertEquals(false, udbig1.PathExists(1,9999999));
    }

}
