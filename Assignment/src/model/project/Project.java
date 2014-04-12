/*
 * @author Aoi Mizu
 */

package model.project;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import model.Developer;
import model.area.Area;
import model.area.AreasList;
import model.effect.SkillAdvancement;
import model.skill.Skill;

/**
 * This class represents a project model.
 * @author Hung
 * @author Aoi Mizu
 */
public class Project implements Serializable {
   
    private Skill mainSkill; //The main skill requirement for this project.
    private int id; //Project's ID.
    private int level; //Project level.
    private int duration; //The duration in month.
    private int currTurnNum; //The number of turn this project has been accepted
    private int totalFp;
    private boolean idSet; //True if the ID was set.
    private String name;

    private Map<String, Developer> developers; //Set of developers
                                               // who work on this project.
    
    // Areas list of the project
    private AreasList areaList;
    
    /**
     * Construct a Project object.
     * @param level The level of this project.
     * @param duration The duration of this project.
     * @param mainSkill The main skill required by this project.
     * @param capital The amount of money spent for this project.
     */
    public Project(int level, int duration, Skill mainSkill, AreasList list, String name) {
//        this.id = autoincrementID++;
        this.level = level;
        this.duration = duration;
        this.mainSkill = mainSkill;
        developers = new LinkedHashMap<String, Developer>();
        this.areaList = list;
        this.name = name;
        
        for(Area a : areaList.getList()) {
            a.setProject(this);
        }
    }
    
    /**
     * Add the given developer into this project.
     * @param dev The given developer.
     * @return True if the adding is successful. If that developer is already
     * <br/> in this project, return False.
     */
    public boolean addDeveloper(Developer dev) {
        if(developers.containsKey(dev.getName()) || !dev.isAvailable()) {
//            System.out.println("Add Developer to Project FAILED");
            return false;
        }
        
        developers.put(dev.getName(), dev);
//        dev.setCurrArea(this);        
//        System.out.println("Add Developer to Project SUCCESSFUL");
        return true;
    }
    
    /**
     * Remove a specific developer from this project.
     * @param devName The name of the removed developer.
     * @return The removed Developer object, or null if there is no developer<br/>
     * with that given name.
     * <br/> with the given name.
     */
    public Developer removeDeveloper(String devName) {
        Developer dev = developers.remove(devName);
        
        if(dev != null) {
            dev.setCurrArea(null);
        }
        
        return dev;
    }
    
    /**
     * Remove all developers from this project
     */    
    public void removeAllDev() {
        areaList.removeAllDev();
        developers.clear();
    }
    
    /**
     * Cancel the project and release all the developers.
     */
    public void cancel() {
        areaList.removeAllDev();
    }
    
    /**
     * Get a developer in this project with his/her name.
     * @param devName The developer's name.
     * @return The developer who has that name, or null if there's no one who<br/>
     * has that name.
     */
    public Developer getDeveloper(String devName) {
        return developers.get(devName);
    }
    
    public Map<String, Developer> getDevelopers() {
        return developers;
    }    
    
    public int getDuration() {
        return duration;
    }

    public int getLevel() {
        return level;
    }

    public Skill getMainSkill() {
        return mainSkill;
    }

    public int getID() {
        return id;
    }
    
    public AreasList getAreas() {
        return areaList;
    }

    /**
     * If the ID is already set once, it can't be set any more.
     * @param id The ID to be set.
     */
    public void setId(int id) {
        if(!idSet) {
            this.id = id;
            idSet = true;
            System.out.println("ID was set successfully: " + id);
        }        
        else {
            System.out.println("This project ID was already set.");
        }
    }
    
    public int getCapital() {
        return 1000*level + areaList.getCost();
    }
    
    public boolean isFinished() {
        return areaList.isFinished();
    }
    
    /**
     * Calculate FPs done each week
     */
    public void nextWeek() {
        areaList.nextWeek(mainSkill);
        
        // Improve developers skill after finishing the project
        if(isFinished()) {
            SkillAdvancement.applyEffect(this);
        }
    }
    
    public String getName(){
        return name;
    }
    
    public void print() {
        System.out.println("------------------SKILL------------------");
        System.out.println("Level: " + level);
        System.out.println("Skills: " + mainSkill.getName());
        System.out.println("Time: " + duration);
        System.out.println("Capital: " + getCapital());
        areaList.print();
        System.out.println("-----------------------------------------\n");        
    }
    
}
