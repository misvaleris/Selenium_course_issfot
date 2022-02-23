package shop;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Locale;

public class CartTest {

    private static String cartName;

    @BeforeMethod (alwaysRun = true)
    public void setup() {
        Faker faker = new Faker();
        cartName = faker.name().lastName().toLowerCase(Locale.ROOT);
    }

    @Test (description = "Cart test - Add Real item to Cart test", groups = {"addToCart"})
    public void cartAddRealItemToCartTest() {

        RealItem bottleRealItem = new RealItem();
        double bottleItemPrice = 876.0;
        bottleRealItem.setPrice(bottleItemPrice);

        Cart realItemCart = new Cart(cartName);
        realItemCart.addRealItem(bottleRealItem);

        double actualTotalPrice = realItemCart.getTotalPrice();
        double expectedTotalPrice = bottleItemPrice + bottleItemPrice * 0.2;

        Assert.assertEquals(expectedTotalPrice, actualTotalPrice, "Cart total price is invalid");
    }

    @Test (description = "Cart test - Add Virtual item to Cart test", groups = {"addToCart"})
    public void cartAddVirtualItemToCartTest() {

        VirtualItem printVirtualItem = new VirtualItem();
        double printItemPrice = 432.01;
        printVirtualItem.setPrice(printItemPrice);

        Cart virtualItemCart = new Cart(cartName);
        virtualItemCart.addVirtualItem(printVirtualItem);

        double actualTotalPrice = virtualItemCart.getTotalPrice();
        double expectedTotalPrice = printItemPrice + printItemPrice * 0.2;

        Assert.assertEquals(expectedTotalPrice, actualTotalPrice, "Cart total price is invalid");
    }

    @Test (description = "Cart test - Delete Item test", groups = {"deleteFromCart"})
    public void cartDeleteRealItemTest() {

        RealItem testRealItem = new RealItem();
        double realItemPrice = 123.0;
        testRealItem.setPrice(realItemPrice);

        Cart realItemCartToDelete = new Cart(cartName);

        double expectedTotalPrice = realItemCartToDelete.getTotalPrice();
        realItemCartToDelete.addRealItem(testRealItem);

        realItemCartToDelete.deleteRealItem(testRealItem);
        double actualTotalPrice = realItemCartToDelete.getTotalPrice();

        Assert.assertEquals(expectedTotalPrice, actualTotalPrice, "Total Price is invalid");
    }

    @AfterClass
    public void teardown() {
      cartName = null;
    }
}