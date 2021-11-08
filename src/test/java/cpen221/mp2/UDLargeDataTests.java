package cpen221.mp2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class UDLargeDataTests {

    // TODO: tests on large data here

    private static UDWInteractionGraph udwbig1;
    private static UDWInteractionGraph udwbig2;
    private static UDWInteractionGraph udwbig3;
    private static UDWInteractionGraph udwbig4;

    @BeforeAll
    public static void setupTests() {
        udwbig1 = new UDWInteractionGraph("resources/email-Eu-core-temporal-Dept1.txt");
        udwbig2 = new UDWInteractionGraph("resources/email-Eu-core-temporal-Dept2.txt");
        udwbig3 = new UDWInteractionGraph("resources/email-Eu-core-temporal-Dept3.txt");
        udwbig4 = new UDWInteractionGraph("resources/email-Eu-core-temporal-Dept4.txt");
    }

    @Test
    public void constructorTests() {
        UDWInteractionGraph udwRemake = new UDWInteractionGraph(udwbig1, Arrays.asList(0, 45506334));
        Assertions.assertEquals(udwRemake.getEmailCount(26, 4), udwbig1.getEmailCount(26, 4));
        Assertions.assertEquals(udwRemake.NumberOfComponents(), udwbig1.NumberOfComponents());
    }

}
