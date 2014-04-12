/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AllTest;

import generator.CompGeneratorTest;
import model.area.AreaTest;
import model.area.AreasListTest;
import model.project.ProjectTest;
import model.room.WorkingTableTest;
import model.saveload.SaveLoadTest;
import model.skill.DefaultSkillListTest;
import model.skill.SkillTest;
import model.skill.SkillsListTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author USER
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value={AreaTest.class, AreasListTest.class, ProjectTest.class, 
SaveLoadTest.class, DefaultSkillListTest.class, SkillTest.class, SkillsListTest.class, 
CompGeneratorTest.class, WorkingTableTest.class})
public class TestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
}
