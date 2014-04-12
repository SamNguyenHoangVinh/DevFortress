/*
 * @author Aoi Mizu
 */

package model.skill;

import static org.junit.Assert.assertTrue;
import org.junit.*;

public class DefaultSkillListTest {
    
    public DefaultSkillListTest() {
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
     * Test of getSkillsList method, of class DefaultSkillList.
     */
    @Test
    public void testGetSkillsList() {
        System.out.println("getSkillsList");
        SkillsList result = DefaultSkillList.getSkillsList();
        assertTrue(result.size()==34);
    }
}
