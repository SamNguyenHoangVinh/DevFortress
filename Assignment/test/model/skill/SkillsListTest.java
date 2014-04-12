/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.skill;

import static org.junit.Assert.assertTrue;
import org.junit.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author USER
 */
public class SkillsListTest {

    public SkillsListTest() {
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
     * Test_01 of add method, of class SkillsList.
     */
    @Test
    public void testAdd_01() {
        System.out.println("add");
        
        Skill s = mock(Skill.class);        
        SkillsList instance = new SkillsList();
        instance.add(s);
        int result = instance.size();
        assertTrue(result==1);
    }

    /**
     * Test_02 of add method, of class SkillsList.
     */
    @Test
    public void testAdd_02() {
        System.out.println("add");
        
        Skill s = mock(Skill.class);
        when(s.getName()).thenReturn("Java");
        
        SkillsList instance = new SkillsList();
        instance.add(s);
        instance.add(s);
        int result = instance.size();
        assertTrue(result==1);
    }

    /**
     * Test_01 of get method, of class SkillsList.
     */
    @Test
    public void testGet_01() {
        System.out.println("get");
        Skill s = mock(Skill.class);
        when(s.getName()).thenReturn("Java");
        when(s.getType()).thenReturn("Technical");
        when(s.getCost()).thenReturn(2);
        when(s.getLevel()).thenReturn(4);
        when(s.getCostFormula()).thenReturn(Skill.PLUS_2);
        SkillsList instance = new SkillsList();
        instance.add(s);
        Skill result = instance.get("Java");
        assertTrue(result.getName().equals("Java"));
    }

    /**
     * Test_02 of get method, of class SkillsList.
     */
    @Test
    public void testGet_02() {
        System.out.println("get");
        Skill s = mock(Skill.class);
        when(s.getName()).thenReturn("Java");
        when(s.getType()).thenReturn("Technical");
        when(s.getCost()).thenReturn(2);
        when(s.getLevel()).thenReturn(4);
        when(s.getCostFormula()).thenReturn(Skill.PLUS_2);
        SkillsList instance = new SkillsList();
        instance.add(s);
        Skill result = instance.get("C");
        assertTrue(result==null);
    }

    /**
     * Test_03 of get method, of class SkillsList.
     */
    @Test
    public void testGet_03() {
        System.out.println("get");
        SkillsList instance = new SkillsList();
        Skill result = instance.get("Java");
        assertTrue(result==null);
    }

    /**
     * Test_01 of getLowest method, of class SkillsList.
     */
    @Test
    public void testGetLowest_01() {
        System.out.println("getLowest");
        
        Skill s1 = mock(Skill.class);
        when(s1.getName()).thenReturn("Java");
        when(s1.getType()).thenReturn("Technical");
        when(s1.getCost()).thenReturn(2);
        when(s1.getLevel()).thenReturn(4);
        when(s1.getCostFormula()).thenReturn(Skill.PLUS_2);
        Skill s2 = mock(Skill.class);
        when(s2.getName()).thenReturn("C");
        when(s2.getType()).thenReturn("Technical");
        when(s2.getCost()).thenReturn(2);
        when(s2.getLevel()).thenReturn(5);
        when(s2.getCostFormula()).thenReturn(Skill.PLUS_2);
        
        SkillsList instance = new SkillsList();
        instance.add(s1);
        instance.add(s2);
        String result = instance.getLowest().getName();
        assertTrue(result.equals("Java"));
    }

    /**
     * Test_02 of getLowest method, of class SkillsList.
     */
    @Test
    public void testGetLowest_02() {
        System.out.println("getLowest");;
        SkillsList instance = new SkillsList();
        Skill result = instance.getLowest();
        assertTrue(result==null);
    }

    /**
     * Test_03 of getLowest method, of class SkillsList.
     */
    @Test
    public void testGetLowest_03() {
        System.out.println("getLowest");
        
        Skill s1 = mock(Skill.class);
        when(s1.getName()).thenReturn("Java");
        when(s1.getType()).thenReturn("Technical");
        when(s1.getCost()).thenReturn(2);
        when(s1.getLevel()).thenReturn(4);
        when(s1.getCostFormula()).thenReturn(Skill.PLUS_2);
        Skill s2 = mock(Skill.class);
        when(s2.getName()).thenReturn("C");
        when(s2.getType()).thenReturn("Technical");
        when(s2.getCost()).thenReturn(2);
        when(s2.getLevel()).thenReturn(5);
        when(s2.getCostFormula()).thenReturn(Skill.PLUS_2);
        
        SkillsList instance = new SkillsList();
        instance.add(s2);
        instance.add(s1);
        String result = instance.getLowest().getName();
        assertTrue(result.equals("Java"));
    }

    /**
     * Test_01 of upgradeSkill method, of class SkillsList.
     */
    @Test
    public void testUpgradeSkill_01() {
        System.out.println("upgradeSkill");
        
//        Skill s = mock(Skill.class);
//        when(s.getName()).thenReturn("Java");
//        when(s.getType()).thenReturn("Technical");
//        when(s.getCost()).thenReturn(2);
//        when(s.getLevel()).thenReturn(4);
//        when(s.getCostFormula()).thenReturn(Skill.PLUS_2);
        Skill s = new Skill("Java","Technical",2,4,Skill.PLUS_2);
        
        SkillsList instance = new SkillsList();
        instance.add(s);
        instance.upgradeSkill("Java");
        int result = instance.get("Java").getLevel();
        assertTrue(result==5);
    }

     /**
     * Test_02 of upgradeSkill method, of class SkillsList.
     */
    @Test
    public void testUpgradeSkill_02() {
        System.out.println("upgradeSkill");
        SkillsList instance = new SkillsList();
        instance.upgradeSkill("Java");
        int result = instance.size();
        assertTrue(result==0);
    }

     /**
     * Test_03 of upgradeSkill method, of class SkillsList.
     */
    @Test
    public void testUpgradeSkill_03() {
        System.out.println("upgradeSkill");
        
        Skill s = mock(Skill.class);
        when(s.getName()).thenReturn("Java");
        when(s.getType()).thenReturn("Technical");
        when(s.getCost()).thenReturn(2);
        when(s.getLevel()).thenReturn(4);
        when(s.getCostFormula()).thenReturn(Skill.PLUS_2);
        
        SkillsList instance = new SkillsList();
        instance.add(s);
        instance.upgradeSkill("C");
        int result = instance.get("Java").getLevel();
        assertTrue(result==4);
    }

     /**
     * Test_04 of upgradeSkill method, of class SkillsList.
     */
    @Test
    public void testUpgradeSkill_04() {
        System.out.println("upgradeSkill");
        
        Skill s = mock(Skill.class);
        when(s.getName()).thenReturn("Java");
        when(s.getType()).thenReturn("Technical");
        when(s.getCost()).thenReturn(2);
        when(s.getLevel()).thenReturn(10);
        when(s.getCostFormula()).thenReturn(Skill.PLUS_2);
        
        SkillsList instance = new SkillsList();
        instance.add(s);
        instance.upgradeSkill("Java");
        int result = instance.get("Java").getLevel();
        assertTrue(result==10);
    }

    /**
     * Test_01 of getCost method, of class SkillsList.
     */
    @Test
    public void testGetCost_01() {
        System.out.println("getCost");
        SkillsList instance = new SkillsList();
        int result = instance.getCost();
        assertTrue(result==0);
    }

    /**
     * Test_02 of getCost method, of class SkillsList.
     */
    @Test
    public void testGetCost_02() {
        System.out.println("getCost");
        Skill s = new Skill("Java", "Technical", 2, 3, Skill.PLUS_2);
        
        SkillsList instance = new SkillsList();
        instance.add(s);
        int result = instance.getCost();
        assertTrue(result==6);
    }

//    /**
//     * Test_01 of updateMainSkillCompareToWholeList method, of class SkillsList.
//     */
//    @Test
//    public void testUpdateMainSkillCompareToWholeList_01() {
//        System.out.println("updateMainSkillCompareToWholeList");
//        Skill s = new Skill("Java", "Technical", 2, 3, Skill.PLUS_2);
//        SkillsList instance = new SkillsList();
//        instance.add(s);
//        String result = instance.getMainSkill().getName();
//        assertTrue(result.equals("Java"));
//    }

    /**
     * Test_01 of updateMainSkillCompareToNewChange method, of class SkillsList.
     */
    @Test
    public void testUpdateMainSkillCompareToNewChange_01() {
        System.out.println("updateMainSkillCompareToNewChange");
        //Skill s = new Skill("Java", "Technical", 2, 3, Skill.PLUS_2);
        
        Skill s = mock(Skill.class);
        when(s.getName()).thenReturn("Java");
        when(s.getType()).thenReturn("Technical");
        when(s.getCost()).thenReturn(2);
        when(s.getLevel()).thenReturn(3);
        when(s.getCostFormula()).thenReturn(Skill.PLUS_2);
        SkillsList instance = new SkillsList();
        instance.add(s);
        String result = instance.getMainSkill().getName();
        assertTrue(result.equals("Java"));
    }

    /**
     * Test_02 of updateMainSkillCompareToNewChange method, of class SkillsList.
     */
    @Test
    public void testUpdateMainSkillCompareToNewChange_02() {
        System.out.println("updateMainSkillCompareToNewChange");
        SkillsList instance = new SkillsList();
        Skill result = instance.getMainSkill();
        assertTrue(result==null);
    }

    /**
     * Test_03 of updateMainSkillCompareToNewChange method, of class SkillsList.
     */
    @Test
    public void testUpdateMainSkillCompareToNewChange_03() {
        System.out.println("updateMainSkillCompareToNewChange");
        //Skill s1 = new Skill("Java", "Technical", 2, 3, Skill.PLUS_2);
        //Skill s2 = new Skill("C", "Technical", 2, 5, Skill.PLUS_2);
        
        Skill s1 = mock(Skill.class);
        when(s1.getName()).thenReturn("Java");
        when(s1.getType()).thenReturn("Technical");
        when(s1.getCost()).thenReturn(2);
        when(s1.getLevel()).thenReturn(3);
        when(s1.getCostFormula()).thenReturn(Skill.PLUS_2);
        Skill s2 = mock(Skill.class);
        when(s2.getName()).thenReturn("C");
        when(s2.getType()).thenReturn("Technical");
        when(s2.getCost()).thenReturn(2);
        when(s2.getLevel()).thenReturn(5);
        when(s2.getCostFormula()).thenReturn(Skill.PLUS_2);
        
        SkillsList instance = new SkillsList();
        instance.add(s1);
        instance.add(s2);
        String result = instance.getMainSkill().getName();
        assertTrue(result.equals("C"));
    }

    /**
     * Test_04 of updateMainSkillCompareToNewChange method, of class SkillsList.
     */
    @Test
    public void testUpdateMainSkillCompareToNewChange_04() {
        System.out.println("updateMainSkillCompareToNewChange");
        Skill s1 = new Skill("Java", "Technical", 2, 3, Skill.PLUS_2);
        Skill s2 = new Skill("C", "Technical", 2, 5, Skill.PLUS_2);
       
        SkillsList instance = new SkillsList();
        instance.add(s1);
        instance.add(s2);
        instance.upgradeSkill("Java");
        instance.upgradeSkill("Java");
        instance.upgradeSkill("Java");
        String result = instance.getMainSkill().getName();
        assertTrue(result.equals("Java"));
    }

    /**
     * Test_05 of updateMainSkillCompareToNewChange method, of class SkillsList.
     */
    @Test
    public void testUpdateMainSkillCompareToNewChange_05() {
        System.out.println("updateMainSkillCompareToNewChange");
        Skill s1 = new Skill("Java", "Technical", 2, 3, Skill.PLUS_2);
        Skill s2 = new Skill("C", "Technical", 2, 5, Skill.PLUS_2);
        SkillsList instance = new SkillsList();
        instance.add(s1);
        instance.add(s2);
        instance.upgradeSkill("Java");
        instance.upgradeSkill("Java");
        String result = instance.getMainSkill().getName();
        assertTrue(result.equals("C"));
    }
}