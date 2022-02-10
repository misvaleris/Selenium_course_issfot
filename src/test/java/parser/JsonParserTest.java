package parser;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.Cart;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParserTest {

    private JsonParser jsonParser;

    @BeforeEach
    public void init() {
        jsonParser = new JsonParser();
    }

    @DisplayName(value = "JSON Parser test - Write to existing file")
    @Test
    public void PARSER_WRITE_TEST_POSITIVE_1() throws IOException {

        String cartName = "parser-cart";
        Cart cart = new Cart(cartName);

        InputStream inputStreamExpected = JsonParserTest.class.getResourceAsStream("/parser-test.json");
        String expectedData = readFromInputStream(inputStreamExpected);

        jsonParser.writeToFile(cart);

        InputStream inputStreamActual = JsonParserTest.class.getResourceAsStream("/parser-cart.json");
        String actualData = readFromInputStream(inputStreamActual);
        assertEquals(expectedData, actualData, "Error on write to file");
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    @Disabled("Disabled for testing purposes")
    @DisplayName(value = "JSON Parser test - Cart is null")
    @Test
    public void PARSER_WRITE_TEST_NEGATIVE_1() {
        assertThrows(NullPointerException.class, () -> {
            jsonParser.writeToFile(null);
        });
    }

    @DisplayName(value = "JSON Parser test - Read from file")
    @Test
    public void PARSER_READ_TEST_POSITIVE_1() {
        String filePath = "src/main/resources/andrew-cart.json";
        File file = new File(filePath);
        Cart cart = jsonParser.readFromFile(file);

        assertNotNull(cart);
        String actualCartName = cart.getCartName();
        assertNotNull(actualCartName);
        String expectedCartName = "andrew-cart";

        double expectedTotalPrice = 38445.479999999996;
        double actualTotalPrice = cart.getTotalPrice();

        assertAll("Real Item test:",
                () -> assertEquals(expectedCartName, actualCartName, "Invalid cart name"),
                () -> assertEquals(expectedTotalPrice, actualTotalPrice, "Invalid cart total price")
        );
    }

    @DisplayName(value = "JSON Parser test - Read from invalid file")
    @Test
    public void PARSER_READ_TEST_NEGATIVE_1() {
        assertThrows(NullPointerException.class, () -> {
            jsonParser.readFromFile(null);
        });
    }

    @ParameterizedTest
    @DisplayName(value = "JSON Parser test - Read from invalid files")
    @CsvSource(value = {"fileName:OlgaTest", "fileName:DashaTest", "fileName:PetyaTest", "fileName:NikitaTest", "fileName:KatyaTest"}, delimiter = ':')
    public void PARSER_READ_TEST_NEGATIVE_2(String filePath) {
        File file = new File(filePath);
        assertThrows(NoSuchFileException.class, () -> {
            jsonParser.readFromFile(file);
        });
    }

    @AfterAll
    static void destroy() {
        File file = new File("src/main/resources/parser-test.json");
        if (!file.delete()) {
            throw new RuntimeException("Can't delete temp file");
        }
    }
}
