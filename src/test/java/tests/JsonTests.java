package tests;

import com.google.gson.Gson;
import model.Output;
import model.TestParameters;
import org.junit.jupiter.api.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JsonTests {

    private static final String JSON_RESULT_PATH = "src/test/resources/result.json";
    private static final String TEST_PARAMETERS_PATH = "src/test/resources/test_parameters.json";
    private final Gson gson = new Gson();
    private Output output;
    private TestParameters testParameters;

    @BeforeEach
    public void setUp() {
        try {
            output = gson.fromJson(new FileReader(JSON_RESULT_PATH), Output.class);
            testParameters = gson.fromJson(new FileReader(TEST_PARAMETERS_PATH), TestParameters.class);
        } catch (IOException e) {
            fail("Error reading JSON file: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    @Tag("positive")
    public void testDetectiveCountPositive() {
        assertTrue(output.detectives.length >= testParameters.detectiveCountMin && output.detectives.length <= testParameters.detectiveCountMax,
                "Detective count is out of the expected range");
    }

    @Test
    @Order(2)
    @Tag("negative")
    public void testDetectiveCountNegative() {
        assertFalse(output.detectives.length < testParameters.detectiveCountMin || output.detectives.length > testParameters.detectiveCountMax,
                "Detective count is within the expected range");
    }

    @Test
    @Order(3)
    @Tag("positive")
    public void testMainIdPositive() {
        assertTrue(Arrays.stream(output.detectives).allMatch(d -> d.MainId >= testParameters.MainIdMin && d.MainId <= testParameters.MainIdMax),
                "MainId is out of the expected range");
    }

    @Test
    @Order(4)
    @Tag("negative")
    public void testMainIdNegative() {
        assertFalse(Arrays.stream(output.detectives).anyMatch(d -> d.MainId < testParameters.MainIdMin || d.MainId > testParameters.MainIdMax),
                "MainId is within the expected range");
    }

    @Test
    @Order(5)
    @Tag("positive")
    public void testCategoryIdPositive() {
        assertTrue(Arrays.stream(output.detectives).flatMap(d -> Arrays.stream(d.categories)).allMatch(c -> c.CategoryID == testParameters.CategoryIdMin || c.CategoryID == testParameters.CategoryIdMax),
                "CategoryID is invalid");
    }

    @Test
    @Order(6)
    @Tag("negative")
    public void testCategoryIdNegative() {
        assertFalse(Arrays.stream(output.detectives).flatMap(d -> Arrays.stream(d.categories)).anyMatch(c -> c.CategoryID != testParameters.CategoryIdMin && c.CategoryID != testParameters.CategoryIdMax),
                "CategoryID is valid");
    }

    @Test
    @Order(7)
    @Tag("positive")
    public void testExtraPositive() {
        assertTrue(Arrays.stream(output.detectives).flatMap(d -> Arrays.stream(d.categories)).allMatch(c -> c.CategoryID != testParameters.CategoryIdMax || c.extra == null),
                "Extra field is not null for CategoryID 2");
    }

    @Test
    @Order(8)
    @Tag("negative")
    public void testExtraNegative() {
        assertFalse(Arrays.stream(output.detectives).flatMap(d -> Arrays.stream(d.categories)).anyMatch(c -> c.CategoryID == testParameters.CategoryIdMax && c.extra != null),
                "Extra field is null for CategoryID 2");
    }

    @Test
    @Order(9)
    @Tag("positive")
    public void testExtraArrayPositive() {
        assertTrue(Arrays.stream(output.detectives).flatMap(d -> Arrays.stream(d.categories))
                        .allMatch(c -> c.CategoryID != testParameters.CategoryIdMin || (c.extra != null && c.extra.extraArray.length >= testParameters.CategoryIdMin)),
                "Extra array does not have enough elements for CategoryID 1");
    }

    @Test
    @Order(10)
    @Tag("negative")
    public void testExtraArrayNegative() {
        assertFalse(Arrays.stream(output.detectives).flatMap(d -> Arrays.stream(d.categories))
                        .anyMatch(c -> c.CategoryID == testParameters.CategoryIdMin && (c.extra == null || c.extra.extraArray.length < testParameters.CategoryIdMin)),
                "Extra array has enough elements for CategoryID 1");
    }

    @Test
    @Order(11)
    @Tag("positive")
    public void testSuccessPositive() {
        assertTrue(!output.success || Arrays.stream(output.detectives).anyMatch(d -> d.firstName.equals(testParameters.successCheckName)),
                "Success is true but detective name is not Sherlock");
    }

    @Test
    @Order(12)
    @Tag("negative")
    public void testSuccessNegative() {
        assertFalse(output.success && Arrays.stream(output.detectives).noneMatch(d -> d.firstName.equals(testParameters.successCheckName)),
                "Success is false but detective name is Sherlock");
    }
}
