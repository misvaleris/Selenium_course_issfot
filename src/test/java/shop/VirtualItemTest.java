package shop;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VirtualItemTest {

    private VirtualItem desk;

    @BeforeAll
    public void setup() {
        desk = new VirtualItem();

    }

    @DisplayName(value = "Virtual Item test - To String for Virtual Item")
    @Test
    public void virtualItemToStringTest() {
        double sizeOnDisc = 123.0;
        String virtualItemName = "testVirtualName";
        double virtualItemPrice = 321.0;
        desk.setName(virtualItemName);
        desk.setPrice(virtualItemPrice);
        desk.setSizeOnDisk(sizeOnDisc);
        String expectedString = "Class: " + desk.getClass() + "; Name: " + virtualItemName + "; Price: " + virtualItemPrice +"; Size on disk: " +sizeOnDisc;

        String actualString = desk.toString();
        assertEquals(expectedString,actualString, "Text is not equals");
    }

    @AfterAll
    public void teardown() {
        desk = null;
    }
}