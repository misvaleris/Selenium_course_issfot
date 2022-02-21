package parser;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.testng.Assert.assertThrows;

public class JsonParserTest {

    public static final String RESOURCE_PATH = "src/main/resources/%s.json";
    private JsonParser jsonParser;
    private Gson gson;
    private static String cartName;
    private static String filePath;
    private static Faker faker;
    private static List<String> listOfFiles;

    @BeforeClass
    public void init() {
        jsonParser = new JsonParser();
        faker = new Faker();
        gson = new Gson();
        cartName = faker.name().firstName().toLowerCase(Locale.ROOT);
        filePath = String.format(RESOURCE_PATH, cartName);
        listOfFiles = new ArrayList<>();
        listOfFiles.add(filePath);
    }

    @Test(description = "JSON Parser test - Write to existing file")
    public void parserWritePositive() throws IOException {
        Cart cart = new Cart(cartName);

        VirtualItem laptop = new VirtualItem();
        double laptopPrice = 567.0;
        laptop.setPrice(laptopPrice);
        cart.addVirtualItem(laptop);
        double actualTotalPrice = cart.getTotalPrice();
        double expectedTotalPrice = laptopPrice + laptopPrice * 0.2;

        jsonParser.writeToFile(cart);
        String actualData = readFromInputStream();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(actualData.contains(cartName), "Error on writeToFile");
        softAssert.assertEquals(expectedTotalPrice, actualTotalPrice, "Invalid expectedCart total price");
        softAssert.assertAll();

    }

    private String readFromInputStream() throws IOException {
        Path path = Paths.get(filePath);
        List<String> fileContent = Files.readAllLines(path);
        return fileContent.isEmpty() ? StringUtils.EMPTY : fileContent.get(0);
    }


    @Test(description = "JSON Parser test - Read from file")
    public void parserReadTestPositive() throws IOException {
        String expectedCartName = faker.name().lastName().toLowerCase(Locale.ROOT);
        Cart expectedCart = new Cart(expectedCartName);

        RealItem flowerRealItem = new RealItem();
        double bottleItemPrice = 876.0;
        double expectedTotalPrice = bottleItemPrice + bottleItemPrice * 0.2;
        flowerRealItem.setPrice(bottleItemPrice);
        expectedCart.addRealItem(flowerRealItem);

        String filePath = String.format(RESOURCE_PATH, expectedCartName);
        writeToFile(expectedCart, filePath);

        listOfFiles.add(filePath);
        File file = new File(filePath);
        Cart actualCart = jsonParser.readFromFile(file);


        Assert.assertNotNull(actualCart);
        String actualCartName = actualCart.getCartName();
        Assert.assertNotNull(actualCartName);

        double actualTotalPrice = actualCart.getTotalPrice();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(expectedCartName, actualCartName, "Invalid expectedCart name");
        softAssert.assertEquals(expectedTotalPrice, actualTotalPrice, "Invalid expectedCart total price");
        softAssert.assertAll();
    }

    private void writeToFile(Cart cart, String filePath) throws IOException {
        String jsonToWrite = gson.toJson(cart);
        Path path = Paths.get(filePath);

        Files.write(path, jsonToWrite.getBytes());
    }

    /**
     * First version od parametrized test
     * */


    @DataProvider(name = "fileName")
    public static Object[][] fileNames() {
        return new Object[][]{{"123456"}, {"DashaTest$$$%%"}, {"      PetyaTestWithSpaces"}, {"___NikitaTest"}, {"D://Downloads/testFileWindows.json"}, {"/home/user/tmp/fileTest23Linux.json"}, {"fileName:/Users/misv/Documents/Docs/fileTestMac.json"}, {"soLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFilesoLongNameOfFile.json"}};
    }

    @Test(dataProvider = "fileName", description = "JSON Parser test - Read from invalid files")
    public void parserReadTestNegative2(String filePath) {
        File file = new File(filePath);
        assertThrows(NoSuchFileException.class, () -> {
            jsonParser.readFromFile(file);
        });
    }

    /**
     * Second version od parametrized test
     * */

    @Parameters({ "fileName" })
    @Test(description = "JSON Parser test - Read from invalid files")
    public void parserReadTestNegative2_1(String filePath) {
        File file = new File(filePath);
        assertThrows(NoSuchFileException.class, () -> {
            jsonParser.readFromFile(file);
        });
    }

    @AfterClass
    static void destroy() {
        for (String filePath : listOfFiles) {
            File file = new File(filePath);
            if (file.exists() && !file.delete()) {
                throw new RuntimeException("Can't delete temp file");
            }
        }
    }
}
