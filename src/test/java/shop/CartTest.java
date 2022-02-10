package shop;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private static Cart newCart;

    @BeforeAll
    static void setUp() {
        String cartName = "Test-cart";
        newCart = new Cart(cartName);
    }

    @DisplayName(value = "Cart test - Add invalid Real Item")
    @Test
    void CART_ADD_REAL_ITEM_TEST_1() {
        assertThrows(NullPointerException.class, () -> {
            newCart.addRealItem(null);
        });
    }

    @DisplayName(value = "Cart test - Delete Item test")
    @Test
    void CART_DELETE_REAL_ITEM_TEST_1() {

        RealItem testRealItem = new RealItem();
        double realItemPrice = 123.0;
        testRealItem.setPrice(realItemPrice);

        double expectedTotalPrice = newCart.getTotalPrice();
        newCart.addRealItem(testRealItem);

        newCart.deleteRealItem(testRealItem);
        double actualTotalPrice = newCart.getTotalPrice();

        assertEquals(expectedTotalPrice, actualTotalPrice, "Total Price is invalid");
    }

    @AfterAll
    static void tearDown() {
        newCart = null;
    }
}