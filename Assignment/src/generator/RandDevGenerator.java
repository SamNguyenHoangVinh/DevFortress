/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import model.Developer;
import model.skill.DefaultSkillList;
import model.skill.Skill;
import model.skill.SkillsList;

/**
 * This is a utility class that can generate a list of random developers. <br/>
 * This list will be used at the beginning of each turn, and those developers 
 * <br/>will apply to the company.
 * @author s3342128
 */
public class RandDevGenerator {
    
    public static final String NAME_LIST_FILE = "namelist.txt";
    public static final int NUM_RAND_DEV = 7; //The number of random developers
                                              // generated each turn.
    private static final int MAX_NUM_SKILLS = 7; //Maximum number of random skills
    private static final int MIN_NUM_SKILLS = 3; //Minimum number of random skills
    private static final int CONFIG_SKILL_RATE = 20; // 20%
    private static final int TOTAL_RAND_SK_LV_RATE = 550;
    private static final int SK_LV_1_RATE = 100; // 100 / 550
    private static final int SK_LV_2_RATE = 190; // 90 / 550
    private static final int SK_LV_3_RATE = 270; // 80 / 550
    private static final int SK_LV_4_RATE = 340; // 70 / 550
    private static final int SK_LV_5_RATE = 400; // 60 / 550
    private static final int SK_LV_6_RATE = 450; // 50 / 550
    private static final int SK_LV_7_RATE = 490; // 40 / 550
    private static final int SK_LV_8_RATE = 520; // 30 / 550
    private static final int SK_LV_9_RATE = 540; // 20 / 550
    private static final int SK_LV_10_RATE = 550; // 10 / 550    
    
    private static List<String> nameList;
    private static List<String> techSkills;
    private static List<String> metaSkills;
    private static List<String> personalSkills;
    
    private static ArrayList<Developer> thisWeekDevList;
//    private static SkillsList skillsList;
    
    /**
     * Construct a RandDevGenerator, load developer names from namelist.txt,<br/>
     * and load skill details from excel file.
     */
    private RandDevGenerator() {
              
    }
    
    private static void init() {        
        nameList = new ArrayList<String>();
        techSkills = new ArrayList<String>();
        metaSkills = new ArrayList<String>();
        personalSkills = new ArrayList<String>();
        
//        skillsList = new SkillsList();
        
        File file = new File(NAME_LIST_FILE);
        Scanner scanner = null;
        
        try {
            scanner =  new Scanner(file);
            
            while(scanner.hasNext()) {
                String s = scanner.nextLine();
//                String[] name = s.split(" ", 2);
//                
//                nameSet.add(name[1]);
                nameList.add(s);
//                System.out.println(s);
            }
            
        } catch(IOException ex) {
            
        }
        
        scanner.close();
        
        
//        // Load skill from excel file.
//        try {
//            LoadSkills a = new LoadSkills(DefaultSkillList.getSkillsList());
//        }
//        catch (Exception e) {
//            System.out.println("gomenasai");
//        }
        
        for(Skill s: DefaultSkillList.getSkillsList().getList()) {
//            System.out.println("Skill added; "+s.getName());
            if(s.getType().equals(Skill.TECHNICAL)) {
                techSkills.add(s.getName());
            }
            else if(s.getType().equals(Skill.META)) {
                metaSkills.add(s.getName());
            }
            else if(s.getType().equals(Skill.PERSONAL)) {
                personalSkills.add(s.getName());
            }
            else if(s.getType().equals(Skill.CONFIG)) {
//                techSkills.add(s.getName());
            }            
        }
        
//        System.out.println("TECH " + techSkills.size());
//        System.out.println("PERSONAL " + personalSkills.size());
//        System.out.println("META " + metaSkills.size());
        
        
//        generateRandDevList();
        
//        Writer writer = null;
//        
//        try {
//            writer = new PrintWriter(file);
//            
//            for(String name : nameSet) {
//                writer.write(name + "\n");
//            }
//            
//            writer.close();
//            
//        } catch(IOException ex) {
//            
//        } 
    }
    
    /**
     * Generate a list of random developers. This method is used at the <br/>
     * beginning of each turn.
     * @return The list of random developers.
     */
    public static ArrayList<Developer> generateRandDevList() {
        if(nameList==null || techSkills==null || metaSkills==null 
                || personalSkills==null) {
            init();
        }
        
        if(thisWeekDevList != null) {
            return thisWeekDevList;
        }
        
//        ArrayList<Developer> l = new ArrayList<Developer>();
        thisWeekDevList = new ArrayList<Developer>();
        
        for(int i=0; i<NUM_RAND_DEV; i++) {
            String name = getOneRandName();
            SkillsList s = getRandSkills();
            Developer dev = new Developer(name, s);
            dev.setFemale(getRandGender());
            thisWeekDevList.add(dev);
        }
        
        return thisWeekDevList;
    }
    
    public static void prepare() {
        if(nameList==null || techSkills==null || metaSkills==null 
                || personalSkills==null) {
            init();
        }
    }
    
    /**
     * Generate a random name for a developer. The name is taken from the file
     * <br/>"namelist.txt". The chosen name will be then removed from the <br/>
     * file.
     * @return A random name for a developer.
     */
    private static String getOneRandName() {  
        Random rand = new Random();
        int i = rand.nextInt(nameList.size());
        
        return nameList.remove(i);        
    }
    
    /**
     * Generate a random list of skills for a developer. This list has a random
     * <br/>number of skills, from 3 to 7 skills. Each skill has a random level,
     * <br/>from 1 to 10.
     * @return A random list of skills for a developer.
     */
    private static SkillsList getRandSkills() {
        SkillsList resultedSk = new SkillsList();
        
        getRandTechSkills(resultedSk);
        getRandMetaSkills(resultedSk);
        getRandPersonalSkills(resultedSk);
        
        int configSkRate = new Random().nextInt(100) + 1;
        
        if(configSkRate <= CONFIG_SKILL_RATE) {
            Skill configSkill = new Skill(DefaultSkillList.getSkillsList()
                                                    .get("Config management"));
            configSkill.setLevel(getRandSkillLv());
            resultedSk.add(configSkill);
        }
        
        return resultedSk;
    }
    
    /**
     * Get from 2 to 4 random Technical skills and put them to the <br/>
     * <code>resultedSk</code>
     * @param resultedSk The map that will contain the resulted skills.
     */
    private static void getRandTechSkills(SkillsList resultedSk) {
        Random rand = new Random();
        int numSkills = rand.nextInt(3) + 2; //The number of skill
        ArrayList<String> sl = new ArrayList<String>(techSkills); //Skill list
        
        retrieveRandSkillList(sl, numSkills, resultedSk);
    }
    
    /**
     * Get from 1 to 2 random Meta skills and put them to the <br/>
     * <code>resultedSk</code>
     * @param resultedSk The map that will contain the resulted skills.
     */
    private static void getRandMetaSkills(SkillsList resultedSk) {        
        Random rand = new Random();
        int numSkills = rand.nextInt(2) + 1; //The number of skill
        ArrayList<String> sl = new ArrayList<String>(metaSkills); //Skill list
        
        retrieveRandSkillList(sl, numSkills, resultedSk);
    }
    
    /**
     * Get from 0 to 1 random Personal skills and put them to the <br/>
     * <code>resultedSk</code>
     * @param resultedSk The map that will contain the resulted skills.
     */
    private static void getRandPersonalSkills(SkillsList resultedSk) {        
        Random rand = new Random();
        int numSkills = rand.nextInt(2); //The number of skill
        ArrayList<String> sl = new ArrayList<String>(personalSkills); //Skill list
        
        retrieveRandSkillList(sl, numSkills, resultedSk);
    }
    
    /**
     * Retrieve <code>numSkills</code> skills from the list <code>sl</code>,<br/>
     * then put them to the map <code>resultedSk</code>.
     * @param sl The list of skills .
     * @param numSkills The number of needed skills.
     * @param resultedSk The map containing resulted skills.
     */
    private static void retrieveRandSkillList(ArrayList<String> sl, 
                             int numSkills, SkillsList resultedSk) {
        Random rand = new Random();
        
        for(int i=0; i<numSkills; i++) {
            int skIdx = rand.nextInt(sl.size());
//            System.out.print(" " + skIdx);
            String skName = sl.remove(skIdx);
//            System.out.println("List size: " + sl.size());
            int skLv = getRandSkillLv();
            Skill s = new Skill(DefaultSkillList.getSkillsList().get(skName));
            
            s.setLevel(skLv);
            resultedSk.add(s);
        }
    }
    
    /**
     * Generate a gender for the developer randomly.
     * @return True is female.
     */
    private static boolean getRandGender() {
        Random rand = new Random();
        int n = rand.nextInt(99) + 1;
        
        if(n <= 30) {
            return true;
        }
        return false;
    }
    
    /**
     * Generate a random level for a skill. The higher level has lower chance
     * <br/>to be generated.
     * @return The random level for a skill.
     */
    private static int getRandSkillLv() {
        Random rand = new Random();
        int range = rand.nextInt(TOTAL_RAND_SK_LV_RATE) + 1;
        
//        System.out.println("Range : " + range);
        
        if(range>=1 && range<=SK_LV_1_RATE) { //Lv 1
            return 1;
        }
        else if(range>SK_LV_1_RATE && range<=SK_LV_2_RATE) { //Lv 2
            return 2;
        }
        else if(range>SK_LV_2_RATE && range<=SK_LV_3_RATE) { //Lv 3
            return 3;
        }
        else if(range>SK_LV_3_RATE && range<=SK_LV_4_RATE) { //Lv 4
            return 4;
        }
        else if(range>SK_LV_4_RATE && range<=SK_LV_5_RATE) { //Lv 5
            return 5;
        }
        else if(range>SK_LV_5_RATE && range<=SK_LV_6_RATE) { //Lv 6
            return 6;
        }
        else if(range>SK_LV_6_RATE && range<=SK_LV_7_RATE) { //Lv 7
            return 7;
        }
        else if(range>SK_LV_7_RATE && range<=SK_LV_8_RATE) { //Lv 8
            return 8;
        }
        else if(range>SK_LV_8_RATE && range<=SK_LV_9_RATE) { //Lv 9
            return 9;
        }
        else if(range>SK_LV_9_RATE && range<=SK_LV_10_RATE) { //Lv 10
            return 10;
        }
        
        return 1;
    }
    
    public static void resetDevList() {
        thisWeekDevList.clear();
        thisWeekDevList = null;
    }

    public static ArrayList<Developer> getThisWeekDevList() {
        return thisWeekDevList;
    }    
}
