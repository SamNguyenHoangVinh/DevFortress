/*
 * @author Aoi Mizu
 */

package model.area;

import generator.RandAreaGenerator;
import generator.RandDevGenerator;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Developer;
import model.skill.Skill;
import static org.junit.Assert.assertTrue;
import org.junit.*;

public class AreaTest {
    
    public AreaTest() {
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
     * Test of addDeveloper method, of class Area.
     */
    @Test
    public void testAddDeveloper() {
        try {
            System.out.println("addDeveloper");
            ArrayList<Developer> devs = RandDevGenerator.generateRandDevList();
            AreasList instance = RandAreaGenerator.generateAreasList(4, 4, 4);
            instance.get(0).addDeveloper(devs.get(0));
            int result = instance.get(0).getDevs().size();
            assertTrue(result==1);
        } catch (Exception ex) {
            //Logger.getLogger(AreaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test_01 of nextWeek method, of class Area.
     */
    @Test
    public void testNextWeek_01() {
        try {
            System.out.println("nextWeek");
            Skill mainProSkill = new Skill("Java", "Technical", 1, 4, Skill.PLUS_2);
            AreasList instance = RandAreaGenerator.generateAreasList(4, 4, 4);
            instance.get(0).nextWeek(mainProSkill);
            int result = instance.get(0).getDone();
            assertTrue(result==0);
        } catch (Exception ex) {
            //Logger.getLogger(AreaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test_02 of nextWeek method, of class Area.
     */
    @Test
    public void testNextWeek_02() {
        try {
            System.out.println("nextWeek");
            Skill mainProSkill = new Skill("Java", "Technical", 1, 4, Skill.PLUS_2);
            AreasList instance = RandAreaGenerator.generateAreasList(4, 4, 4); 
            ArrayList<Developer> devs = RandDevGenerator.generateRandDevList();
            instance.get(0).addDeveloper(devs.get(0));
            instance.get(0).nextWeek(mainProSkill);
            int result = instance.get(0).getDone();
            assertTrue(result!=0);
        } catch (Exception ex) {
            //Logger.getLogger(AreaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
