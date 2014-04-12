/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import generator.RandEventGenerator;
import java.io.Serializable;
import model.area.Area;
import model.event.EventCollector;
import model.project.Project;
import model.room.Hardware;
import model.skill.Skill;
import model.skill.SkillsList;

/**
 * This class represents a developer model.
 * @author Hung
 */
public class Developer implements Serializable {

    public static final int MIN_FUNCT_POINT_OUTPUT = 1; //Minimum function point
    
    //States
    public static final int DO_NOTHING = 200;
    public static final int WORKING = 201;
    public static final int TRAINING = 202;
    
    //Emotions
    public static final int VERY_HAPPY = 100;
    public static final int HAPPY = 70;
    public static final int NORMAL = 50;
    public static final int UNHAPPY = 30;
    public static final int ANGRY = 0;
    
    //Basic details
    public static final int NAME = 0;
    public static final int MAIN_SKILL = 1;
    public static final int CURR_AREA = 2;
    
    private String name; //Developer's name.
    private SkillsList skills; //The list of skills this developer has.
    private Area currArea; //The current area this developer is working on
                           // If it's null, the developer is currently free.
    private Skill trainingSkl; //The current training skill;
//    private Player player;
    private String weeklyEvent;
    private int maxNumSkills = 9; //The maximum number of skills this developer
                                  // can have.
    private int currFpOutput = 1; //The current function point output.
    private int emotion;
    private int state;
    
    private boolean fpAffected;
    private boolean eating;
    private boolean female;
    private boolean employed;
    private Hardware computer;
//    private boolean training;

    /**
     * Construct a Developer object with a given name & a set of skills.
     * @param n The name of this developer.
     * @param s The map of original skills of this developer.
     */
    public Developer(String n, SkillsList s) {
        name = n;
        skills = s;
    }
    
    public void print() {
        System.out.println("Name: "+name);
        System.out.println("Skills List: ");
        skills.print();
    }

    /**
     * Add a new skill. If the number of skills is exceeded the maximum number
     * <br/> number of skills, it will return false.
     * @param skill The new skill.
     */
    public boolean addSkill(Skill skill) {
        if(skills.size() >= maxNumSkills) {
            return false;
        }
        return skills.add(skill);
    }   
    
    /**
     * Upgrade level of a skill
     * @param skillName the skill to be upgraded
     */
    public void train(String skillName) {
        trainingSkl = skills.upgradeSkill(skillName);
        System.out.println("trainingSkl is null : " + trainingSkl==null);
    }
    
    /**
     * Receive a random weekly event.
     */
    public void receiveWeeklyEvent() {
        weeklyEvent = RandEventGenerator.generateRandEvent();
        
        System.out.println(name + " : " + weeklyEvent);
        if(currArea == null) {
//            System.out.println(name + " is currently free. No effect");
            weeklyEvent = RandEventGenerator.EVENTS[RandEventGenerator.NOTHING];
            return;
        }
        
        EventCollector.getInstance().add(weeklyEvent);
        
        if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.SICKNESS])) {
            RandEventGenerator.sickness(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.KILL_ANOTHER])) {
            RandEventGenerator.killAnother(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.REQUIREMENT_CHANGE])) {
            RandEventGenerator.requirementChange(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.NEW_TECH])) {
            RandEventGenerator.newTech(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.SOLUTION_NOT_SCALE])) {
            RandEventGenerator.solutionNotScale(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.HACKED])) {
            RandEventGenerator.hacked(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.BACKUP_FAILED])) {
            RandEventGenerator.backupFailed(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.HOLIDAY])) {
            RandEventGenerator.holiday(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.FEATURE_CUT])) {
            RandEventGenerator.featureCut(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.BONUS])) {
            RandEventGenerator.bonus();
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.TEAM_EXERCISE])) {
            RandEventGenerator.teamExercise(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.REDUNDANCIES])) {
            RandEventGenerator.redundancies(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.IDIOT_MARKETING])) {
            RandEventGenerator.idiotMarketing(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.INTERNS])) {
            RandEventGenerator.interns(this);
        }
        else if(weeklyEvent.equals(
                RandEventGenerator.EVENTS[RandEventGenerator.NOTHING])) {
//            System.out.println("Nothing");
        }
        
        if(weeklyEvent == null) {
            weeklyEvent = RandEventGenerator.EVENTS[RandEventGenerator.NOTHING];
        }
    }

    public String[] getBasicDetails() {
        String[] details = new String[3];
        details[NAME] = name;
        details[MAIN_SKILL] = skills.getMainSkill().getName();
        details[CURR_AREA] = (currArea != null) ? "" + currArea.getName() 
                                                 : "Unknown";
        return details;
    }
    
    public Skill getSkill(String skillName) {
        return skills.get(skillName);
    }
    
    public String getName() {
        return name;
    }
    
    public int getSalary() {
        return skills.getCost();
    }

    public SkillsList getSkills() {
        return skills;
    }
    
    public boolean isAvailable() {
        return currArea==null && trainingSkl==null;
    }
    
    public boolean isWorking() {
        return currArea!=null;
    }
    
    public boolean isTraining() {
        return trainingSkl!=null;
    }
       
    public Project getCurrProj() {
        if(currArea!=null) {
            return currArea.getProject();
        }
        return null;
    }

    public int getCurrFpOutput() {
        return currFpOutput;
    }

    /**
     * Set the function point output. If the input fpOutput is less than 1,<br/>
     * then the function point output will be 1.
     * @param fpOutput The new function point output.
     */
    public void setCurrFpOutput(int fpOutput) {        
        currFpOutput = (fpOutput < MIN_FUNCT_POINT_OUTPUT) ? 
                                              MIN_FUNCT_POINT_OUTPUT : fpOutput;
    }

    public int getEmotion() {
        return emotion;
    }

    /**
     * Set the emotion of the developer. The degree of emotion must be from<br/>
     * 0 to 100. If the degree of emotion is less than or equal to UNHAPPY, <br/>
     * there is a chance that the developer will leave the company.
     * @param emotion The degree of emotion.
     */
    public void setEmotion(int emotion) {
        if(emotion > HAPPY) {
            this.emotion = HAPPY;
        }
        else if(emotion < ANGRY) {
            this.emotion = ANGRY;
        }
        else {
            this.emotion = emotion;
        }     
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isEating() {
        return eating;
    }

    public void setEating(boolean eating) {
        this.eating = eating;
    }

    public Area getCurrArea() {
        return currArea;
    }

    public void setCurrArea(Area currArea) {
        this.currArea = currArea;
    }

    public boolean isFpAffected() {
        return fpAffected;
    }

    public void setFpAffected(boolean fpAffected) {
        this.fpAffected = fpAffected;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    public String getWeeklyEvent() {
        return weeklyEvent;
    }
    
    public boolean isEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }

    public Skill getTrainingSkl() {
        return trainingSkl;
    }

    public void setTrainingSkl(Skill trainingSkl) {
        this.trainingSkl = trainingSkl;
    }
    
    public Hardware getComputer() {
        return computer;
    }
    
    public void setComputer(Hardware computer) {
        this.computer = computer;
    }
}
