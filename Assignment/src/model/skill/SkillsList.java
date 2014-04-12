/*
 * @author Aoi Mizu
 */

package model.skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SkillsList implements Serializable {
    
    private Skill mainSkill;
    private ArrayList<Skill> list;
    
    public SkillsList() {
        list = new ArrayList<Skill>();
    }
    
    public ArrayList<Skill> getList() {
        return list;
    }
    
    public Skill getMainSkill() {
        return mainSkill;
    }
    
    
    /**
     * Get total number of skills of the list
     * @return number of skills
     */
    public int size() {
        return list.size();
    }
    
    /**
     * Add a skill to the list
     * @param s the skill to add
     * @return -true: the skill is added
     *         -false: skill has already added
     */
    public boolean add(Skill s) {
        // If the list is empty, the added skill will be the main skill
        if(list.isEmpty()) {
            list.add(s);
            mainSkill = s;
        }
        else {
            // Check if the skill already added
            for(Skill temp: list) {
                if(temp.getName().equals(s.getName())) {
                    return false;
                }
            }
            
            // Add new skill and update new main skill
            list.add(s);
            Collections.sort(list, SkillComparator.getInstance());
//            updateMainSkillCompareToWholeList();
            updateMainSkillCompareToNewChange(s);
        }
        return true;
    }
    
    /**
     * Get the skill in the list
     * @param name the name of the skill
     * @return -the skill with that name
     *         -null if the skill does not exist
     */
    public Skill get(String name) {
        if(!list.isEmpty()) {
            for(Skill s: list) {
//                System.out.println("Skill name: " + s.getName());
                if(s.getName().equals(name)) {
                    return s;
                }
            }
        }
        return null;
    }
    
    /**
     * Get the skill which has the lowest level
     * @return -the lowest-level skill
     *         -null if the list is empty
     */
    public Skill getLowest() {
        if(!list.isEmpty()) {
            Skill result = list.get(0);
            for(Skill s: list) {
                if(s.getLevel()<result.getLevel()) {
                    result = s;
                }
            }
            return result;
        }
        return null;
    }
    
    
    /**
     * Update skill and get new main skill
     * @param skillName skill to be upgraded
     */
    public Skill upgradeSkill(String skillName) {
        // Increase level of skill
        Skill skl = get(skillName);
        if(skl != null) {
            skl.upgrade();
            
//            SkillComparator sc = SkillComparator.getInstance();
//            if(sc.compare(mainSkill, skl) < 0) {
//                mainSkill = skl;
//            }
            updateMainSkillCompareToNewChange(skl);
            return skl;            
        }
        
        return null;
        // Set new main skill
//        updateMainSkillCompareToWholeList();
    }
    
    /**
     * Get the totals cost of all skills in the list
     * @return - 0 if the list is empty
     *         - else the total cost
     */
    public int getCost() {
        if(list.isEmpty()) {
            return 0;
        }
        else {
            int total = 0;
            for(Skill s: list) {
                total = total + s.getCost();
            }
            return total;
        }
    }
    
    /**
     * Update new main skill form the skills list
     */
//    public void updateMainSkillCompareToWholeList() {
//        SkillComparator sc = SkillComparator.getInstance();
//        for(Skill s: list) {
//            if(sc.compare(mainSkill, s) < 0) {
//                mainSkill = s;
//            }
//        }
//    }
    
    public void updateMainSkillCompareToNewChange(Skill changedSkill) {
        SkillComparator sc = SkillComparator.getInstance();
        if (sc.compare(mainSkill, changedSkill) < 0) {
            mainSkill = changedSkill;
        }
    }
    
    public void print() {
        for(Skill s: list) {
            System.out.println(s.toString());
        }
    }
}
