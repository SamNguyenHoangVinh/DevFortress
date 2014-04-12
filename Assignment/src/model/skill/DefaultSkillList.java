/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.skill;

/**
 *
 * @author s3342128
 */
public class DefaultSkillList {
    
    private static SkillsList skillsList;
    
    private DefaultSkillList() {
    }

    public static SkillsList getSkillsList() {
        if(skillsList == null) {
            skillsList = new SkillsList();
            // Load skill from excel file.
            try {
                LoadSkills a = new LoadSkills(skillsList);
            }
            catch (Exception e) {
                System.out.println("gomenasai");
            }
        }
        return skillsList;
    }
}
