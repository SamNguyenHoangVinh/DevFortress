/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.skill;

import java.util.Comparator;

/**
 * This comparator can compare 2 composers's names and return whether one <br/>
 * is higher than or equal to another. This class is used to support the <br/>
 * sorting feature.
 * @author s3342128
 */
public class SkillComparator implements Comparator {

    private static final SkillComparator skillComparator =
                                                        new SkillComparator();
    private SkillComparator(){
    }
    
    @Override
    public int compare(Object o1, Object o2) {
        Skill s1 = (Skill) o1;
        Skill s2 = (Skill) o2;

        if (s1.getLevel() > s2.getLevel()) {
            return 1;
        } else if (s1.getLevel() < s2.getLevel()) {
            return -1;
        }

        //If s1.getLevel() == s2.getLevel()
        return s2.getName().compareTo(s1.getName());
    }
    
    public static SkillComparator getInstance() {
        return skillComparator;
    }
}
