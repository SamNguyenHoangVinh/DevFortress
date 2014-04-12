/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.room;

import generator.CompGenerator;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import static org.mockito.Mockito.*;
import view.roomproperties.WorkingTableView;

/**
 *
 * @author USER
 */
public class WorkingTableTest {
    ArrayList<Hardware> list;

    public WorkingTableTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        list = CompGenerator.generateCompList();
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Test_01 of putComputer method, of class WorkingTable.
     */
    @Test
    public void testPutComputer_01() {
        System.out.println("putComputer");
        Hardware computer = list.get(0);
        WorkingTable instance = new WorkingTable(new Room(),"Room1");
        boolean result = instance.putComputer(computer, 0);
        assertTrue(result == true);
    }

    /**
     * Test_02 of putComputer method, of class WorkingTable.
     */
    @Test
    public void testPutComputer_02() {
        System.out.println("putComputer");
        Hardware computer = list.get(0);
        WorkingTable instance = new WorkingTable(new Room(),"Room1");
        for(int i=0; i< WorkingTableView.TABLE_PC_CAPACITY;i++){
            instance.putComputer(computer, i);
        }
        boolean result = instance.putComputer(computer, 0);
        assertTrue(result == false);
    }

    /**
     * Test_01 of removeComputer method, of class WorkingTable.
     */
    @Test
    public void testRemoveComputer_01() {
        System.out.println("removeComputer");
        Hardware computer = list.get(0);
        WorkingTable instance = new WorkingTable(new Room(),"Room1");
        instance.putComputer(computer, 0);
        instance.removeComputer(computer);
        int result= instance.getComputers().size();
        assertTrue(result == 0);
    }

    /**
     * Test_02 of removeComputer method, of class WorkingTable.
     */
    @Test
    public void testRemoveComputer_02() {
        System.out.println("removeComputer");
        Hardware computer = list.get(0);
        WorkingTable instance = new WorkingTable(new Room(),"Room1");
        instance.removeComputer(computer);
        int result= instance.getComputers().size();
        assertTrue(result == 0);
    }

    /**
     * Test_01 of putMonitor method, of class WorkingTable.
     */
    @Test
    public void testPutMonitor_01() {
        System.out.println("putMonitor");
        Hardware monitor = list.get(0);;
        WorkingTable instance = new WorkingTable(new Room(),"Room1");
        boolean result = instance.putMonitor(monitor);
        assertTrue(result==true);
    }

    /**
     * Test_02 of putMonitor method, of class WorkingTable.
     */
    @Test
    public void testPutMonitor_02() {
        System.out.println("putMonitor");
        Hardware monitor = list.get(0);
        WorkingTable instance = new WorkingTable(new Room(),"Room1");
        for(int i=0; i< WorkingTableView.TABLE_PC_CAPACITY;i++){
            instance.putMonitor(monitor);
        }
        boolean result = instance.putMonitor(monitor);
        assertTrue(result==false);
    }

    /**
     * Test_01 of removeMonitor method, of class WorkingTable.
     */
    @Test
    public void testRemoveMonitor_01() {
        System.out.println("removeMonitor");
        Hardware monitor = list.get(0);
        WorkingTable instance = new WorkingTable(new Room(),"Room1");
        instance.putMonitor(monitor);
        instance.removeMonitor(monitor);
        int result= instance.getMonitors().size();
        assertTrue(result==0);
    }

    /**
     * Test_02 of removeMonitor method, of class WorkingTable.
     */
    @Test
    public void testRemoveMonitor_02() {
        System.out.println("removeMonitor");
        Hardware monitor = list.get(0);
        WorkingTable instance = new WorkingTable(new Room(),"Room1");
        instance.removeMonitor(monitor);
        int result= instance.getMonitors().size();
        assertTrue(result==0);
    }

}