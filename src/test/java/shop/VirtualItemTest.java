package shop;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class VirtualItemTest {

    private static VirtualItem desk;

    @BeforeAll
    static void setUp() {
        desk = new VirtualItem();
    }

    @DisplayName(value = "Virtual Item test - Set size on disk for Virtual Item")
    @Test
    public void VIRTUAL_ITEM_SET_SIZE_TEST_1() {
        double expectedSize = 3000.0;
        desk.setSizeOnDisk(expectedSize);

        double actualSize = desk.getSizeOnDisk();
        assertEquals(expectedSize, actualSize, "Size is invalid");
    }

    @AfterAll
    static void tearDown() {
        desk = null;
    }
}