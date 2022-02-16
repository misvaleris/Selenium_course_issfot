package parser;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.Cart;
import shop.RealItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParserTest {

    public static final String RESOURCE_PATH = "src/main/resources/%s.json";
    private JsonParser jsonParser;
    private static String cartName;
    private static String filePath;
    private static Faker faker;
    private static ArrayList<String> listOfFiles;

    @BeforeEach
    public void init() {
        jsonParser = new JsonParser();
        faker = new Faker();
        cartName = faker.name().firstName().toLowerCase(Locale.ROOT);
        filePath = String.format(RESOURCE_PATH, cartName);
        listOfFiles = new ArrayList<>();
        listOfFiles.add(filePath);
    }

    @Disabled("Disabled for testing purposes")
    @DisplayName(value = "JSON Parser test - Write to existing file")
    @Test
    public void ParserWritePositiveTest() {
        Cart cart = new Cart(cartName);
        jsonParser.writeToFile(cart);

        File file = new File(filePath);
        Cart expectedCart = jsonParser.readFromFile(file);
        String expectedCartName = expectedCart.getCartName();

        assertEquals(expectedCartName, cartName, "Error on write to file");
    }

    @DisplayName(value = "JSON Parser test - Read from file")
    @Test
    public void ParserReadTestPositive() {
        String expectedCartName = faker.name().lastName().toLowerCase(Locale.ROOT);
        Cart expectedCart = new Cart(expectedCartName);

        RealItem flowerRealItem = new RealItem();
        double bottleItemPrice = 876.0;
        double expectedTotalPrice = bottleItemPrice + bottleItemPrice*0.2;
        flowerRealItem.setPrice(bottleItemPrice);
        expectedCart.addRealItem(flowerRealItem);
        jsonParser.writeToFile(expectedCart);

        String filePath = String.format(RESOURCE_PATH, expectedCartName);
        listOfFiles.add(filePath);
        File file = new File(filePath);
        Cart actualCart = jsonParser.readFromFile(file);

        assertNotNull(actualCart);
        String actualCartName = actualCart.getCartName();
        assertNotNull(actualCartName);

        double actualTotalPrice = actualCart.getTotalPrice();

        assertAll("Real Item test:",
                () -> assertEquals(expectedCartName, actualCartName, "Invalid expectedCart name"),
                () -> assertEquals(expectedTotalPrice, actualTotalPrice, "Invalid expectedCart total price")
        );
    }

    @ParameterizedTest
    @DisplayName(value = "JSON Parser test - Read from invalid files")
    @CsvSource(value = {"fileName:123456", "fileName:DashaTest$$$%%", "fileName:      PetyaTestWithSpaces", "fileName:___NikitaTest", "fileName:D://Downloads/testFileWindows.json", "fileName:/home/user/tmp/fileTest23Linux.json", "fileName:/Users/misv/Documents/Docs/fileTestMac.json", "fileName:soLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFile.json"}, delimiter = ':')
    public void ParserReadTestNegative2(String filePath) {
        File file = new File(filePath);
        assertThrows(NoSuchFileException.class, () -> {
            jsonParser.readFromFile(file);
        });
    }

    @AfterAll
    static void destroy() {
        for (String filePath : listOfFiles) {
            File file = new File(filePath);
            if (file.exists() && !file.delete()) {
                throw new RuntimeException("Can't delete temp file");
            }
        }
    }
}
