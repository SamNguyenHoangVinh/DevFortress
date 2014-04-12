/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.util.Random;
import model.Developer;
import model.effect.*;

/**
 * This is a utility class that can generate a random event. It will be used at
 * <br/> the beginning of each week.
 * @author Hung
 */
public class RandEventGenerator {
    
    public static final String[] EVENTS = {
        "Developer is sick", // 0
        "Developer kills another", // 1nnnnnnnnnnnnnnnnn
        "Requirements change", // 2
        "New Technology", // 3
        "Solution doesn't scale", // 4
        "Hacked", // 5
        "Backup failed", // 6
        "Holiday", // 7
        "Feature cut", // 8
        "Bonus", // 9
        "Team building exercise", // 10
        "Redundancies", // 11nnnnnnnnnnnnnnnnnnn
        "Idiot marketing", // 12nnnnnnnnnnnn
        "Interns", // 13
        "Nothing" // 14
    };
    
    public static final int SICKNESS = 0;
    public static final int KILL_ANOTHER = 1;
    public static final int REQUIREMENT_CHANGE = 2;
    public static final int NEW_TECH = 3;
    public static final int SOLUTION_NOT_SCALE = 4;
    
    public static final int HACKED = 5;
    public static final int BACKUP_FAILED = 6;
    public static final int HOLIDAY = 7;
    public static final int FEATURE_CUT = 8;
    public static final int BONUS = 9;
    
    public static final int TEAM_EXERCISE = 10;
    public static final int REDUNDANCIES = 11;
    public static final int IDIOT_MARKETING = 12;
    public static final int INTERNS = 13;
    public static final int NOTHING = 14;
    
    public static final int SICKNESS_RATE = 5; // 5
    public static final int KILL_ANOTHER_RATE = 6; // 1
    public static final int REQUIREMENT_CHANGE_RATE = 16; // 10
    public static final int NEW_TECH_RATE = 21; // 5
    public static final int SOLUTION_NOT_SCALE_RATE = 26; // 5
    
    public static final int HACKED_RATE = 27; // 1
    public static final int BACKUP_FAILED_RATE = 32; // 5
    public static final int HOLIDAY_RATE = 42; // 10
    public static final int FEATURE_CUT_RATE = 47; // 5
    public static final int BONUS_RATE = 52; // 5
    
    public static final int TEAM_EXERCISE_RATE = 57; // 5
    public static final int REDUNDANCIES_RATE = 62; // 5
    public static final int IDIOT_MARKETING_RATE = 72; // 10
    public static final int INTERNS_RATE = 82; // 10
    public static final int NOTHING_RATE = 132; // 50
       
    private RandEventGenerator() {   
    }
    
    /**
     * Generate a name of a random event.
     * @return 
     */    
    public static String generateRandEvent() {
        Random r = new Random();
        int i = r.nextInt(131) + 1; //generate a random number from 1 to 100.
        
        if(i <= SICKNESS_RATE) {
            return EVENTS[SICKNESS];
        }
        else if(i <= KILL_ANOTHER_RATE) {
            return EVENTS[KILL_ANOTHER];
        }
        else if(i <= REQUIREMENT_CHANGE_RATE) {
            return EVENTS[REQUIREMENT_CHANGE];
        }
        else if(i <= NEW_TECH_RATE) {
            return EVENTS[NEW_TECH];
        }
        else if(i <= SOLUTION_NOT_SCALE_RATE) {
            return EVENTS[SOLUTION_NOT_SCALE];
        }
        else if(i <= HACKED_RATE) {
            return EVENTS[HACKED];
        }
        else if(i <= BACKUP_FAILED_RATE) {
            return EVENTS[BACKUP_FAILED];
        }
        else if(i <= HOLIDAY_RATE) {
            return EVENTS[HOLIDAY];
        }
        else if(i <= FEATURE_CUT_RATE) {
            return EVENTS[FEATURE_CUT];
        }
        else if(i <= BONUS_RATE) {
            return EVENTS[BONUS];
        }
        else if(i <= TEAM_EXERCISE_RATE) {
            return EVENTS[TEAM_EXERCISE];
        }
        else if(i <= REDUNDANCIES_RATE) {
            return EVENTS[REDUNDANCIES];
        }
        else if(i <= IDIOT_MARKETING_RATE) {
            return EVENTS[IDIOT_MARKETING];
        }
        else if(i <= INTERNS_RATE) {
            return EVENTS[INTERNS];
        }
        else {
            return EVENTS[NOTHING];
        }
//        return EVENTS[TEAM_EXERCISE];
    }
    
    /**
     * When a developer is sick, his/her function point output will decrease<br/>
     * by half.
     * @param dev The sick developer. 
     */
    public static void sickness(Developer dev) {
        int i = dev.getCurrFpOutput()/2;
        if(i==0) {
            i = 1;
        }
        FPModification e = new FPModification(i, dev, null);
        e.applyEffect();
    }
    
    public static void killAnother(Developer dev) {
        TeamHappinessModification e = 
                new TeamHappinessModification(Developer.UNHAPPY, dev, 
                new DeveloperRemoval(dev, null));
        e.applyEffect();
    }
    
    public static void requirementChange(Developer dev) {
        AreaModification e = new AreaModification(20, dev.getCurrArea(), null);
        e.applyEffect();
    }
    
    public static void newTech(Developer dev) {
        AreaModification e = new AreaModification(-50, dev.getCurrArea(), null);
        e.applyEffect();
    }
    
    public static void solutionNotScale(Developer dev) {
        AreaModification e = new AreaModification(10, dev.getCurrArea(), null);
        e.applyEffect();
    }
    
    public static void hacked(Developer dev) {
        FPModification e = new FPModification(0, dev, null);
        e.applyEffect();
    }
    
    public static void backupFailed(Developer dev) {
        AreaModification e = new AreaModification(25, dev.getCurrArea(), null);
        e.applyEffect();
    }
    
    public static void holiday(Developer dev) {
        FPModification e = new FPModification(1, dev, null);
        e.applyEffect();
    }
    
    public static void featureCut(Developer dev) {
        AreaRemoval e = new AreaRemoval(dev, null);
        e.applyEffect();
    }
    
    public static void bonus() {
        MoneyModification e = new MoneyModification(1000, null);
        e.applyEffect();
    }
    
    public static void teamExercise(Developer dev) {
        TeamFPModification e = new TeamFPModification(5, dev,
                new TeamHappinessModification(Developer.HAPPY, dev, null));
        e.applyEffect();
    }
    
    public static void redundancies(Developer dev) {
        TeamHappinessModification e = 
                new TeamHappinessModification(Developer.UNHAPPY, dev, 
                new DeveloperRemoval(dev, null));
        e.applyEffect();
    }
    
    public static void idiotMarketing(Developer dev) {
        AreaModification e = new AreaModification(10, dev.getCurrArea(), 
                new HappinessModification(Developer.UNHAPPY, dev, null));
        e.applyEffect();
    }
    
    public static void interns(Developer dev) {
        AreaModification e = new AreaModification(5, dev.getCurrArea(), 
                new HappinessModification(Developer.HAPPY, dev, null));
        e.applyEffect();
    }
}
