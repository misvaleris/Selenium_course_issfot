package shop;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class CartTest {

    private static String cartName;

    @BeforeAll
    public void setup() {
        Faker faker = new Faker();
        cartName = faker.name().lastName().toLowerCase(Locale.ROOT);
    }

    @DisplayName(value = "Cart test - Add Real item to Cart test")
    @Test
    public void CartAddRealItemToCartTest() {

        RealItem bottleRealItem = new RealItem();
        double bottleItemPrice = 876.0;
        bottleRealItem.setPrice(bottleItemPrice);

        Cart realItemCart = new Cart(cartName);
        realItemCart.addRealItem(bottleRealItem);

        double actualTotalPrice = realItemCart.getTotalPrice();
        double expectedTotalPrice = bottleItemPrice + bottleItemPrice * 0.2;

        assertEquals(expectedTotalPrice, actualTotalPrice, "Cart total price is invalid");
    }

    @DisplayName(value = "Cart test - Add Virtual item to Cart test")
    @Test
    public void CartAddVirtualItemToCartTest() {

        VirtualItem printVirtualItem = new VirtualItem();
        double printItemPrice = 432.01;
        printVirtualItem.setPrice(printItemPrice);

        Cart virtualItemCart = new Cart(cartName);
        virtualItemCart.addVirtualItem(printVirtualItem);

        double actualTotalPrice = virtualItemCart.getTotalPrice();
        double expectedTotalPrice = printItemPrice + printItemPrice * 0.2;

        assertEquals(expectedTotalPrice, actualTotalPrice, "Cart total price is invalid");
    }

    @DisplayName(value = "Cart test - Delete Item test")
    @Test
    public void CartDeleteRealItemTest() {

        RealItem testRealItem = new RealItem();
        double realItemPrice = 123.0;
        testRealItem.setPrice(realItemPrice);

        Cart realItemCartToDelete = new Cart(cartName);

        double expectedTotalPrice = realItemCartToDelete.getTotalPrice();
        realItemCartToDelete.addRealItem(testRealItem);

        realItemCartToDelete.deleteRealItem(testRealItem);
        double actualTotalPrice = realItemCartToDelete.getTotalPrice();

        assertEquals(expectedTotalPrice, actualTotalPrice, "Total Price is invalid");
    }

    @AfterAll
    public void teardown() {
      cartName = null;
    }
}