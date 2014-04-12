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

public class AreasListTest {
    
    public AreasListTest() {
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
     * Test_01 of add method, of class AreasList.
     */
    @Test
    public void testAdd_01() {
        try {
            System.out.println("add");
            AreasList model = RandAreaGenerator.generateAreasList(4, 4, 4);
            AreasList instance = new AreasList();
            instance.add(model.get(0));
            int result = instance.size();
            assertTrue(result==1);
        } catch (Exception ex) {
            //Logger.getLogger(AreasListTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test_02 of add method, of class AreasList.
     */
    @Test
    public void testAdd_02() {
        try {
            System.out.println("add");
            AreasList instance = new AreasList();
            int result = instance.size();
            assertTrue(result==0);
        } catch (Exception ex) {
            //Logger.getLogger(AreasListTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test_01 of remove method, of class AreasList.
     */
    @Test
    public void testRemove_01() {
        try {
            System.out.println("remove");
            AreasList model = RandAreaGenerator.generateAreasList(4, 4, 4);
            AreasList instance = new AreasList();
            instance.add(model.get(0));
            instance.add(model.get(1));
            
            instance.remove(0);
            int result = instance.size();
            assertTrue(result==1);
        } catch (Exception ex) {
            Logger.getLogger(AreasListTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test_02 of remove method, of class AreasList.
     */
    @Test
    public void testRemove_02() {
        try {
            System.out.println("remove");
            AreasList model = RandAreaGenerator.generateAreasList(4, 4, 4);
            AreasList instance = new AreasList();
            instance.add(model.get(0));
            
            instance.remove(0);
            int result = instance.size();
            assertTrue(result==0);
        } catch (Exception ex) {
            Logger.getLogger(AreasListTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test_03 of remove method, of class AreasList.
     */
    @Test
    public void testRemove_03() {
        Exception e = null;
        try {
            System.out.println("remove");
            AreasList model = RandAreaGenerator.generateAreasList(4, 4, 4);
            AreasList instance = new AreasList();
            instance.remove(0);
            int result = instance.size();
            assertTrue(result==0);
        } catch (Exception ex) {
            e = ex;
        }
        boolean expected = e instanceof IndexOutOfBoundsException;
        assertTrue(expected==true);
    }

    /**
     * Test_01 of nextWeek method, of class AreasList.
     */
    @Test
    public void testNextWeek_01() {
        try {
            System.out.println("nextWeek");
            Skill mainProSkill = new Skill("Java", "Technical", 1, 4, Skill.PLUS_2);
            AreasList instance = RandAreaGenerator.generateAreasList(4, 4, 4);
            instance.nextWeek(mainProSkill);
            int total = 0;
            for(Area a: instance.getList()) {
                total = total + a.getDone();
            }
            assertTrue(total==0);
        } catch (Exception ex) {
            //Logger.getLogger(AreasListTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test_02 of nextWeek method, of class AreasList.
     */
    @Test
    public void testNextWeek_02() {
        try {
            System.out.println("nextWeek");
            ArrayList<Developer> devs = RandDevGenerator.generateRandDevList();
            Skill mainProSkill = new Skill("Java", "Technical", 1, 4, Skill.PLUS_2);
            AreasList instance = RandAreaGenerator.generateAreasList(4, 4, 4);
            instance.get(0).addDeveloper(devs.get(0));
            instance.nextWeek(mainProSkill);
            int total = 0;
            for(Area a: instance.getList()) {
                total = total + a.getDone();
            }
            assertTrue(total!=0);
        } catch (Exception ex) {
            //Logger.getLogger(AreasListTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test_01 of isFinished method, of class AreasList.
     */
    @Test
    public void testIsFinished_01() {
        try {
            System.out.println("isFinished");
            AreasList instance = RandAreaGenerator.generateAreasList(4, 4, 4);
            boolean result = instance.isFinished();
            assertTrue(result==false);
        } catch (Exception ex) {
            Logger.getLogger(AreasListTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test_02 of isFinished method, of class AreasList.
     */
    @Test
    public void testIsFinished_02() {
        try {
            System.out.println("isFinished");
            AreasList instance = RandAreaGenerator.generateAreasList(4, 4, 4);
            for(Area a: instance.getList()) {
                a.setFinished();
            }
            boolean result = instance.isFinished();
            assertTrue(result==true);
        } catch (Exception ex) {
            Logger.getLogger(AreasListTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getCost method, of class AreasList.
     */
    @Test
    public void testGetCost() {
        try {
            System.out.println("getCost");
            AreasList instance = RandAreaGenerator.generateAreasList(4, 4, 4);
            int totalFPs = 0;
            int totalMoney = instance.getCost();
            for(Area a: instance.getList()) {
                totalFPs = totalFPs + a.getFP();
            }
            
            assertTrue(totalFPs*10==totalMoney);
        } catch (Exception ex) {
            Logger.getLogger(AreasListTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
