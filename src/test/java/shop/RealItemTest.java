package shop;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RealItemTest {

    private static RealItem car;
    @BeforeAll
    static void setUp() {
        car = new RealItem();
    }

    @DisplayName(value = "Real Item test - Set weight for Real Item")
    @Test
    public void REAL_ITEM_SET_WEIGHT_TEST_1() {
        double expectedWeight = 1123.0;
        car.setWeight(expectedWeight);

        double actualWeight = car.getWeight();
        assertEquals(expectedWeight, actualWeight, "Weight is invalid");
    }

    @AfterAll
    static void tearDown() {
        car = null;
    }
}