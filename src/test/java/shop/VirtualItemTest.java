package shop;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Ignore
public class VirtualItemTest {

    private VirtualItem desk;

    @BeforeClass
    public void setup() {
        desk = new VirtualItem();

    }

    @Test (description = "Virtual Item test - To String for Virtual Item")
    public void virtualItemToStringTest() {
        double sizeOnDisc = 123.0;
        String virtualItemName = "testVirtualName";
        double virtualItemPrice = 321.0;
        desk.setName(virtualItemName);
        desk.setPrice(virtualItemPrice);
        desk.setSizeOnDisk(sizeOnDisc);
        String expectedString = String.format("Class: %s; Name: %s; Price: %s; Size on disk: %s",desk.getClass(),virtualItemName,virtualItemPrice,sizeOnDisc);


        String actualString = desk.toString();
        Assert.assertEquals(expectedString,actualString, "Text is not equals");
    }

    @AfterClass
    public void teardown() {
        desk = null;
    }
}