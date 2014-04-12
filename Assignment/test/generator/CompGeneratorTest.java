/*
 * @author Aoi Mizu
 */

package generator;

import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import org.junit.*;

public class CompGeneratorTest {
    
    public CompGeneratorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of generateCompList method, of class CompGenerator.
     */
    @Test
    public void testGenerateCompList() {
        System.out.println("generateCompList");
        ArrayList result = CompGenerator.generateCompList();
        int expected = result.size();
        assertTrue(expected==4);
    }
}
