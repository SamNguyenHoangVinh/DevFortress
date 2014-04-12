/*
 * @author Aoi Mizu
 */

package model.calculator;

import generator.RandAreaGenerator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Developer;
import model.area.Area;
import model.project.Project;
import model.skill.Skill;
import model.skill.SkillsList;

public class FBCalculator {
    
    public static final String LISP = "Lisp";
    public static final String HASKELL = "Haskell";
    public static final String FORTH = "Forth";
    public static final String DESIGN = "Design";
    public static final String ALGORITHMS = "Algorithms";
    public static final String ANALYSIS  = "Analysis";
    public static final String TEAM_PLAYER = "Team player";
    public static final String COMMUNICATION = "Communication";
    
    private FBCalculator(){
    }
    
    public static int calculate(Skill mainProSkill, Area area, int devs, Developer developer) {
        int result = 0;
        
        int devSkillPoint = 0;
        SkillsList skills = developer.getSkills();
        
        SkillsList techs = new SkillsList();
        SkillsList metas = new SkillsList();
        SkillsList personals = new SkillsList();
        SkillsList specials = new SkillsList();
        SkillsList config = new SkillsList();
        
        for(Skill s: skills.getList()) {
            // Check if the skill is special (LISP, HASKELL or FORTH)
            if(s.getType().equals(Skill.TECHNICAL)) {
                if(s.getName().equals(LISP) || s.getName().equals(HASKELL) || 
                                                    s.getName().equals(FORTH)) {
                    // Add special skills to special list
                    specials.add(s);
                }
                else {
                    // If the skill is not special, add it to techs list
                    techs.add(s);
                }
            }
            else if(s.getType().equals(Skill.META)) {
                // Add meta skills to meta list
                metas.add(s);
            }
            else if(s.getType().equals(Skill.PERSONAL)) {
                // Add personal skills to personal list
                personals.add(s);
            }
            else if(s.getType().equals(Skill.CONFIG)) {
                // Add config skills to config list
                config.add(s);
            }
        }
        
        /*
         * Calculate technical skills
         */
        
        // Developer does not have the main skill of project
        if(techs.get(mainProSkill.getName())==null && 
                                   specials.get(mainProSkill.getName())==null) {
            // Developer does not have special skills
            if(specials.size()==0) {
                devSkillPoint = techs.getLowest().getLevel()/2;
                result = result + devSkillPoint;
                System.out.println("Technical halved: "+result);
            }
            // Developer have special skills
            else {
                // Get the higest level special skill
                devSkillPoint = specials.getList().get(0).getLevel();
                for(Skill s: specials.getList()) {
                    if(s.getLevel()>devSkillPoint) {
                        devSkillPoint = s.getLevel();
                    }
                }
                result = result + devSkillPoint;
                System.out.println("Special: "+result);
            }
        }
        // Developer does have the main skill of project
        else {
            // Get the skill of developer
            Skill devSkill = techs.get(mainProSkill.getName());
            if(devSkill==null) {
                devSkill = specials.get(mainProSkill.getName());
            }
            devSkillPoint = devSkill.getLevel();
            result = result + devSkillPoint;
            System.out.println("Technical: "+result);
        }
        
        /*
         * Calculate meta skills
         */
        for(Skill s: metas.getList()) {
            // Developer has Design skill
            if(s.getName().equals(FBCalculator.DESIGN)) {
                result = result + 2*s.getLevel();
                System.out.println("Design: "+result);
            }
            // Developer has Algorithms skill
            else if(s.getName().equals(FBCalculator.ALGORITHMS)) {
                result = result + devSkillPoint*s.getLevel();
                System.out.println("Algorithms: "+result);
            }
            // Developer has Analysis skill
            else if(s.getName().equals(FBCalculator.ANALYSIS)) {
                result = result + 3*s.getLevel();
                System.out.println("Analysis: "+result);
            }
        }
        
        /*
         * Calculate Team_Player skills
         */
        for(Skill s: personals.getList()) {
            // Developer has Team player skill
            if(s.getName().equals(FBCalculator.TEAM_PLAYER)) {
                result = result + devs*s.getLevel();
                System.out.println("Team: "+result);
                break;
            }
        }
        
        /*
         * Calculate config skills
         */
        if(config.size()==0) {
            result = result/12;
            System.out.println("No Config: "+result);
        }
        else {
            result = result/(12-config.getList().get(0).getLevel());
            System.out.println("Config: "+result);
        }
        
        /*
         * Calculate Communication skill
         */
        for(Skill s: personals.getList()) {
            // Developer has Communication skill
            if(s.getName().equals(FBCalculator.COMMUNICATION)) {
                if(area.isKnown()==false) {
                    // Add level of Communication skill of developer
                    result = result + s.getLevel();
                    System.out.println("Communication: "+result);
                    break;
                }
            }
        }
        
        // Check if result is less than 1
        if(result==0) {
            return 1;
        }
        return result;
    }
    
    public static void main(String[] args) {
        try {
            Skill java = new Skill("Java", Skill.TECHNICAL, 1, Skill.PLUS_2);
            Skill c = new Skill("C", Skill.TECHNICAL, 1, Skill.PLUS_2);
            Skill lisp = new Skill(FBCalculator.LISP, Skill.TECHNICAL, 1, Skill.PLUS_4);
            Skill design = new Skill(FBCalculator.DESIGN, Skill.META, 1, Skill.PLUS_4);
            Skill config = new Skill("Config", Skill.CONFIG, 1, Skill.PLUS_4);
            Skill comm = new Skill(COMMUNICATION, Skill.PERSONAL, 1, Skill.PLUS_4);
            
            Project p = new Project(3, 4, c, RandAreaGenerator.generateAreasList(4, 4, 10), "1");
            
            SkillsList skills1 = new SkillsList();
            SkillsList skills2 = new SkillsList();
            
            skills1.add(new Skill(java));
            skills1.get("Java").setLevel(10);
            skills2.add(new Skill(java));
            skills2.get("Java").setLevel(10);
            
            skills1.add(new Skill(design));
            skills1.get(FBCalculator.DESIGN).setLevel(5);
            skills2.add(new Skill(design));
            skills2.get(FBCalculator.DESIGN).setLevel(5);
            
            skills1.add(new Skill(config));
            skills1.get("Config").setLevel(10);
            skills2.add(new Skill(config));
            skills2.get("Config").setLevel(10);
            
            skills1.add(new Skill(lisp));
            skills1.get(FBCalculator.LISP).setLevel(1);
            skills2.add(new Skill(lisp));
            skills2.get(FBCalculator.LISP).setLevel(1);
            
            skills1.add(new Skill(comm));
            skills1.get(COMMUNICATION).setLevel(5);
            skills2.add(new Skill(comm));
            skills2.get(COMMUNICATION).setLevel(10);
            
            Developer d1 = new Developer("Urabe Mikoto", skills1);
            Developer d2 = new Developer("Urabe Mikoto", skills2);
            p.getAreas().get(0).addDeveloper(d1);
            p.getAreas().get(0).addDeveloper(d2);
            
            //System.out.println(calculate(java, p.getAreas().get(0), 1, d1));
            
            p.getAreas().get(0).print();
            p.nextWeek();
            p.getAreas().get(0).print();
        } catch (Exception ex) {
            Logger.getLogger(FBCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
