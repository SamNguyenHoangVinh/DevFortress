/*
 * @author Aoi Mizu
 */

package model.saveload;

import generator.RandDevGenerator;
import generator.RandProjGenerator;
import java.io.File;
import java.util.ArrayList;
import model.Developer;
import model.player.Player;
import model.project.Project;
import static org.junit.Assert.assertTrue;
import org.junit.*;

public class SaveLoadTest {
    
    public SaveLoadTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        File f = new File(SaveLoad.SAVE_PATH_1);
        f.delete();
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
     * Test of save method, of class SaveLoad.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        Player p = Player.getInstance();
        p.reset();
//        Player p = new Player("Aoi Mizu");
//        Player p = Player.getInstance("Aoi Mizu");
        
        p.setMoney(10000);
        RandDevGenerator.prepare();
        RandProjGenerator.loadProjectNameList();
        ArrayList<Developer> devs = RandDevGenerator.generateRandDevList();
        System.out.println("Devs: "+devs.size());
        ArrayList<Project> projs = RandProjGenerator.generateProjectList();
        System.out.println("Projs: "+projs.size());
        p.hireDeveloper(devs.get(0));
        p.acceptProject(projs.get(0));
        
        boolean result = SaveLoad.save(p,"Player");
        assertTrue(result==true);
    }

    /**
     * Test of load method, of class SaveLoad.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        Player result = Player.getInstance();
        result.reset();
        
        int noDevs = result.getDevelopers().values().size();
        int noProjs = result.getProjects().values().size();
        assertTrue(noDevs==0 && noProjs==0);
    }
}
