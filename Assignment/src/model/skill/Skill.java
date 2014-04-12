/*
 * @author Aoi Mizu
 */

package model.skill;

import java.io.Serializable;

public class Skill implements Serializable {
    
    //types
    public static final String TECHNICAL = "Technical";
    public static final String META = "Meta";
    public static final String PERSONAL = "Personal";
    public static final String CONFIG = "Config";
    
    //Cost formulas
    public static final int PLUS_2 = 0;
    public static final int PLUS_4 = 1;
    public static final int TIME_2 = 2;
    
    private String name;
    private String type;
    private int startCost;
    private int level;
    private int costFormula;
//    private SkillInterface costInterface;
    
//    public Skill(String name, String type, int startCost, 
//                                                 SkillInterface costInterface) {
//        this.name = name;
//        this.type = type;
//        this.startCost = startCost;
//        level = 0;
//        this.costInterface = costInterface;
//    }
//    
//    public Skill(String name, String type, int startCost,
//                                      int level, SkillInterface costInterface) {
//        this.name = name;
//        this.type = type;
//        this.startCost = startCost;
//        this.level = level;
//        this.costInterface = costInterface;
//    }
//    
//    public Skill(Skill s) {
//        this.name = s.getName();
//        this.type = s.getType();
//        this.startCost = s.getStartCost();
//        this.level = s.getLevel();
//        this.costInterface = s.getCostInterface();
//    }
    
    /**
     * Construct a Skill object with a given name, type, starting cost, & <br/>
     * cost formula.
     * @param name The name of this skill.
     * @param type The type of this skill.
     * @param startCost The starting cost of this skill.
     * @param costFormula The cost formula to calculate the real cost.
     */
    public Skill(String name, String type, int startCost, int costFormula) {
        this.name = name;
        this.type = type;
        this.startCost = startCost;
        level = 1;
        this.costFormula = costFormula;
    }
    
    /**
     * Construct a Skill object with a given name, type, starting cost, <br/>
     * level, & cost formula.
     * @param name The name of this skill.
     * @param type The type of this skill.
     * @param startCost The starting cost of this skill.
     * @param level The original skill level.
     * @param costFormula The cost formula to calculate the real cost.
     */
    public Skill(String name, String type, int startCost, 
                                                   int level, int costFormula) {
        this.name = name;
        this.type = type;
        this.startCost = startCost;        
        this.costFormula = costFormula;
        
        if(level>=1 && level<=10) {
            this.level = level;
        }        
        else if(level < 1) {
            this.level = 1;
        }
        else if(level > 10) {
            this.level = 10;
        }
    }
    
    /**
     * Construct a Skill object & copy all data from another Skill object <br/>
     * to this object
     * @param s The source Skill object.
     */
    public Skill(Skill s) {
        name = s.getName();
        type = s.getType();
        startCost = s.getStartCost();
        level = s.getLevel();
        costFormula = s.getCostFormula();
    }
    
    /**
     * Upgrade this skill by 1 level.
     */
    public boolean upgrade() {
        if(level < 10) {
            level++;
            return true;
        }
        else {
            return false;
        }
    }
    
    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }
    public String getType() {
        return type;
    }
    
//    public int getCost() {
//        return costInterface.getCost(startCost, level);
//    }
    
    /**
     * Get the cost needed for this skill. The cost will be calculated based<br/>
     * on the cost formula.
     * @return 
     */
    public int getCost() {
        if(level < 1 || level > 10) {
            return 0;
        }
        
        if(costFormula == PLUS_2) {
            // Calculate the cost by the function cost(n) = cost(n-1) + 2
            return startCost + level + level - 2;
        }
        else if(costFormula == PLUS_4) {
            // Calculate the cost by the function cost(n) = cost(n-1) + 4
            return startCost + level + level + level + level - 4;
        }
        else { //costFormula == TIME_2
            // Calculate the cost by the function cost(n) = cost(n-1) * 2
            return (int) ((int) startCost * Math.pow(2, level - 1));
        }
    }
    
    public void setLevel(int level) {
        if(level>=1 && level<=10) {
            this.level = level;
        }        
        else {
            System.out.println("The skill must be from 1 to 10");
        }
    }

    public int getStartCost() {
        return startCost;
    }

//    public SkillInterface getCostInterface() {
//        return costInterface;
//    }   
    public int getCostFormula() {
        return costFormula;
    }    
    
    @Override
    public String toString() {
        return name + " (" + type + "): " + level + " - cost: " + getCost();
    }
}
