package parser;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.Cart;

import java.io.*;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParserTest {

    private JsonParser jsonParser;
    private String expectedData;
    private String cartName;

    @BeforeEach
    public void init() throws IOException {
        jsonParser = new JsonParser();
        Faker faker = new Faker();
        cartName = faker.name().firstName().toLowerCase(Locale.ROOT);
        String fileName = "src/main/resources/" + cartName + ".json";
        File file = new File(fileName);
        if (file.createNewFile()) {
            InputStream inputStreamExpected = JsonParserTest.class.getResourceAsStream(file.getAbsolutePath());
            expectedData = readFromInputStream(inputStreamExpected);
        }
    }

//    @Disabled("Disabled for testing purposes")
    @DisplayName(value = "JSON Parser test - Write to existing file")
    @Test
    public void ParserWritePositiveTest() {
        //TODO спросить у Димы по поводу handle IOException
        try {

            Cart cart = new Cart(cartName);
            jsonParser.writeToFile(cart);
            PrintWriter writer = new PrintWriter(cartName, "UTF-8");
            writer.write("The first line");
            writer.write("The second line");
            writer.close();

            InputStream inputStreamActual = JsonParserTest.class.getResourceAsStream("/" + cartName + ".json");
            String actualData = readFromInputStream(inputStreamActual);
            assertEquals(expectedData, actualData, "Error on write to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @DisplayName(value = "JSON Parser test - Read from file")
    @Test
    public void ParserReadTestPositive() {
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

    @ParameterizedTest
    @DisplayName(value = "JSON Parser test - Read from invalid files")
    @CsvSource(value = {"fileName:123456", "fileName:DashaTest$$$%%", "fileName:      PetyaTestWithSpaces", "fileName:___NikitaTest", "fileName:D://Downloads/testFileWindows.json","fileName:/home/user/tmp/fileTest23Linux.json","fileName:/Users/misv/Documents/Docs/fileTestMac.json","fileName:soLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFile.json"}, delimiter = ':')
    public void ParserReadTestNegative2(String filePath) {
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
