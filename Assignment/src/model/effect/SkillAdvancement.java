/*
 * @author Aoi Mizu
 */

package model.effect;

import java.util.Collection;
import java.util.Random;
import model.Developer;
import model.project.Project;
import model.skill.Skill;

public class SkillAdvancement {
    private static int skill_Advancement_Rate = 50;
    private static int total_Rate = 100;
    
    private SkillAdvancement() {
    }
    
    public static void applyEffect(Project p) {
        Skill main = p.getMainSkill();
        Collection<Developer> devs = p.getDevelopers().values();
        
        for(Developer d: devs) {
            // Check if the skill is improved
            Random r = new Random();
            int i = r.nextInt(total_Rate + 1);
            if(i>skill_Advancement_Rate) {
                
                // Add new skill and improve skill
                d.addSkill(main);
                if(d.getSkill(main.getName()).upgrade()) {
                    System.out.println(d.getName()+" improves "+main.getName()+" skill");
                }
            }
        }
    }
}
