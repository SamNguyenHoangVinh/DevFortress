/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.project;

import generator.RandDevGenerator;
import generator.RandProjGenerator;
import java.util.List;
import model.Developer;
import model.player.Player;
import static org.junit.Assert.assertTrue;
import org.junit.*;

/**
 *
 * @author USER
 */
public class ProjectTest {

    public ProjectTest() {
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
     * Test_01 of addDeveloper method, of class Project.
     */
    @Test
    public void testAddDeveloper_01() {
        System.out.println("addDeveloper");
        RandDevGenerator.prepare();
        RandProjGenerator.loadProjectNameList();
        List <Developer> dev = RandDevGenerator.generateRandDevList();
        List <Project> instance = RandProjGenerator.generateProjectList();
        instance.get(0).addDeveloper(dev.get(0));
        int devs = instance.get(0).getDevelopers().values().size();
        assertTrue(devs==1);
    }

    /**
     * Test_02 of addDeveloper method, of class Project.
     */
    @Test
    public void testAddDeveloper_02() {
        System.out.println("addDeveloper");
        RandDevGenerator.prepare();
        RandProjGenerator.loadProjectNameList();
        List <Developer> dev = RandDevGenerator.generateRandDevList();
        List <Project> instance = RandProjGenerator.generateProjectList();
        instance.get(0).addDeveloper(dev.get(0));
        boolean result = instance.get(0).addDeveloper(dev.get(0));
        assertTrue(result==false);
    }

    /**
     * Test_03 of addDeveloper method, of class Project.
     */
    @Test
    public void testAddDeveloper_03() {
        System.out.println("addDeveloper");
        RandDevGenerator.prepare();
        RandProjGenerator.loadProjectNameList();
        List <Developer> dev = RandDevGenerator.generateRandDevList();
        List <Project> instance = RandProjGenerator.generateProjectList();
        boolean result = instance.get(0).addDeveloper(dev.get(0));
        assertTrue(result==false);
    }

    /**
     * Test_01 of removeDeveloper method, of class Project.
     */
    @Test
    public void testRemoveDeveloper_01() {
        System.out.println("removeDeveloper");
        RandDevGenerator.prepare();
        RandProjGenerator.loadProjectNameList();
        List <Developer> dev = RandDevGenerator.generateRandDevList();
        List <Project> instance = RandProjGenerator.generateProjectList();
        Developer result = instance.get(0).removeDeveloper("Dien");
        assertTrue(result==null);
    }

    /**
     * Test_02 of removeDeveloper method, of class Project.
     */
    @Test
    public void testRemoveDeveloper_02() {
        System.out.println("removeDeveloper");
        RandDevGenerator.prepare();
        RandProjGenerator.loadProjectNameList();
        List <Developer> dev = RandDevGenerator.generateRandDevList();
        List <Project> instance = RandProjGenerator.generateProjectList();
        instance.get(0).addDeveloper(dev.get(0));
        String result1 = instance.get(0).removeDeveloper(dev.get(0).getName()).
                                                                      getName();
        assertTrue(result1.equals(dev.get(0).getName()));
    }

    /**
     * Test_03 of removeDeveloper method, of class Project.
     */
    @Test
    public void testRemoveDeveloper_03() {
        System.out.println("removeDeveloper");
        List <Developer> dev = RandDevGenerator.generateRandDevList();
        List <Project> instance = RandProjGenerator.generateProjectList();
        instance.get(0).addDeveloper(dev.get(0));
        Developer d = instance.get(0).removeDeveloper(dev.get(1).getName());
        assertTrue(d==null);
    }

    /**
     * Test_01 of cancel method, of class Project.
     */
    @Test
    public void testCancel_01() {
        System.out.println("cancel");
        RandDevGenerator.prepare();
        RandProjGenerator.loadProjectNameList();
        List <Developer> dev = RandDevGenerator.generateRandDevList();
        List <Project> instance = RandProjGenerator.generateProjectList();
        instance.get(0).addDeveloper(dev.get(0));
        instance.get(0).cancel();
        boolean result1 = dev.get(0).isAvailable();
        Project result2 = dev.get(0).getCurrProj();
        assertTrue(result1==true && result2==null);
    }

    /**
     * Test_02 of cancel method, of class Project.
     */
    @Test
    public void testCancel_02() {
        System.out.println("cancel");
        RandDevGenerator.prepare();
        RandProjGenerator.loadProjectNameList();
        List <Developer> dev = RandDevGenerator.generateRandDevList();
        List <Project> instance = RandProjGenerator.generateProjectList();
        instance.get(0).addDeveloper(dev.get(0));
        instance.get(0).cancel();
        boolean result1 = dev.get(0).isAvailable();
        Project result2 = dev.get(0).getCurrProj();
        assertTrue(result1==true && result2==null);
    }

    /**
     * Test of getCapital method, of class Project.
     */
    @Test
    public void testGetCapital() {
        System.out.println("getCapital");
        List <Project> instance = RandProjGenerator.generateProjectList();
        int result = instance.get(0).getCapital();
        int expected = instance.get(0).getLevel()*1000 +
                                           instance.get(0).getAreas().getCost();
        assertTrue(result==expected);
    }

}