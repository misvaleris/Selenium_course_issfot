package shop;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RealItemTest {

    private RealItem car;
    @BeforeClass
    public void setup() {
        car = new RealItem();
    }

    @Test (description = "Real Item test - To String for Real Item")
    public void realItemToStringTest() {
        double realItemWeight = 123.0;
        String realItemName = "testRealName";
        double realItemPrice = 321.0;
        car.setName(realItemName);
        car.setPrice(realItemPrice);
        car.setWeight(realItemWeight);
        String expectedString = String.format("Class: %s; Name: %s; Price: %s; Weight: %s",car.getClass(),realItemName,realItemPrice,realItemWeight);

        String actualString = car.toString();
        Assert.assertEquals(expectedString,actualString, "Text is not equals");
    }

    @AfterClass
    public void teardown() {
        car = null;
    }
}