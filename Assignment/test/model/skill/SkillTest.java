/*
 * @author Aoi Mizu
 */

package model.skill;

import static org.junit.Assert.assertTrue;
import org.junit.*;

public class SkillTest {
    
    public SkillTest() {
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
     * Test_01 of upgrade method, of class Skill.
     */
    @Test
    public void testUpgrade_01() {
        System.out.println("upgrade");
        Skill instance = new Skill("Java", "Technical", 1, 4, Skill.PLUS_2);
        instance.upgrade();
        int result = instance.getLevel();
        assertTrue(result==5);
    }

    /**
     * Test_02 of upgrade method, of class Skill.
     */
    @Test
    public void testUpgrade_02() {
        System.out.println("upgrade");
        Skill instance = new Skill("Java", "Technical", 1, 10, Skill.PLUS_2);
        instance.upgrade();
        int result = instance.getLevel();
        assertTrue(result==10);
    }

    /**
     * Test_03 of upgrade method, of class Skill.
     */
    @Test
    public void testUpgrade_03() {
        System.out.println("upgrade");
        Skill instance = new Skill("Java", "Technical", 1, 11, Skill.PLUS_2);
        instance.upgrade();
        int result = instance.getLevel();
        assertTrue(result==10);
    }

    /**
     * Test_01 of getCost method, of class Skill.
     */
    @Test
    public void testGetCost_01() {
        System.out.println("getCost");
        Skill instance = new Skill("Java", "Technical", 2, 0, Skill.PLUS_2);
        int result = instance.getCost();
        assertTrue(result==2);
    }

    /**
     * Test_02 of getCost method, of class Skill.
     */
    @Test
    public void testGetCost_02() {
        System.out.println("getCost");
        Skill instance = new Skill("Java", "Technical", 1, 11, Skill.PLUS_2);
        int result = instance.getCost();
        assertTrue(result==19);
    }

    /**
     * Test_03 of getCost method, of class Skill.
     */
    @Test
    public void testGetCost_03() {
        System.out.println("getCost");
        Skill instance = new Skill("Java", "Technical", 3, 5, Skill.PLUS_4);
        int result = instance.getCost();
        assertTrue(result==19);
    }

    /**
     * Test_04 of getCost method, of class Skill.
     */
    @Test
    public void testGetCost_04() {
        System.out.println("getCost");
        Skill instance = new Skill("Java", "Technical", 2, 8, Skill.TIME_2);
        int result = instance.getCost();
        assertTrue(result==256);
    }

    /**
     * Test_01 of setLevel method, of class Skill.
     */
    @Test
    public void testSetLevel_01() {
        System.out.println("setLevel");
        Skill instance = new Skill("Java", "Technical", 2, 8, Skill.TIME_2);
        instance.setLevel(-1);
        int result = instance.getLevel();
        assertTrue(result==8);
    }

    /**
     * Test_02 of setLevel method, of class Skill.
     */
    @Test
    public void testSetLevel_02() {
        System.out.println("setLevel");
        Skill instance = new Skill("Java", "Technical", 2, 8, Skill.TIME_2);
        instance.setLevel(12);
        int result = instance.getLevel();
        assertTrue(result==8);
    }

    /**
     * Test_03 of setLevel method, of class Skill.
     */
    @Test
    public void testSetLevel_03() {
        System.out.println("setLevel");
        Skill instance = new Skill("Java", "Technical", 2, 5, Skill.TIME_2);
        instance.setLevel(9);
        int result = instance.getLevel();
        assertTrue(result==9);
    }
}
