package shop;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class CartTest {

    private Cart newCart;

    @BeforeAll
    public void setup() {
        Faker faker = new Faker();
        String cartName = faker.name().lastName().toLowerCase(Locale.ROOT);
        newCart = new Cart(cartName);
    }

    @DisplayName(value = "Cart test - Add item to Cart test")
    @Test
    public void CartAddItemToCartTest() {

        //TODO test for add to cart operation

    }

    @DisplayName(value = "Cart test - Delete Item test")
    @Test
    public void CartDeleteRealItemTest() {

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
    public void teardown() {
        newCart = null;
    }
}