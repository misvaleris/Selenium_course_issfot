package shop;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class RealItemTest {

    private RealItem car;
    @BeforeAll
    public void setup() {
        car = new RealItem();
    }

    @DisplayName(value = "Real Item test - To String for Real Item")
    @Test
    public void realItemToStringTest() {
        double realItemWeight = 123.0;
        String realItemName = "testRealName";
        double realItemPrice = 321.0;
        car.setName(realItemName);
        car.setPrice(realItemPrice);
        car.setWeight(realItemWeight);
        String expectedString = "Class: " + car.getClass() + "; Name: " + realItemName + "; Price: " + realItemPrice +"; Weight: " +realItemWeight;

        String actualString = car.toString();
        assertEquals(expectedString,actualString, "Text is not equals");
    }

    @AfterAll
    public void teardown() {
        car = null;
    }
}