/*
 * @author Aoi Mizu
 */

package generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Developer;
import model.player.Player;
import model.area.AreasList;
import model.project.Project;
import model.skill.DefaultSkillList;
import model.skill.LoadSkills;
import model.skill.Skill;
import model.skill.SkillsList;

/**
 * This is a utility class that can generate a list of random projects. It will 
 * <br/> be used when the player wants to have more project.
 * @author Aoi Mizu
 */
public class RandProjGenerator {
    private static Random r = new Random();
    
    // The file contains projects names
    public static final String PROJECT_LIST_FILE = "projectlist.txt";
    
    // The minimum level required for a Project
    private static final int MIN_LVL = 1;
    // The maximum level required for a Project
    private static final int MAX_LVL = 5;
    
    // The rate to calculate Function Point
    public static final int FP_RATE = 10;
    
    // The minimum months required for LV1 Project
    private static final int L1_MIN_TIME = 1;
    // The maximum months required for LV1 Project
    private static final int L1_MAX_TIME = 4;
    // The minimum function points/month required for LV1 Project
    private static final int L1_MIN_FP = 4;
    // The maximum function points/month required for LV1 Project
    private static final int L1_MAX_FP = 10;
    // The minimum areas required for LV1 Project
    private static final int L1_MIN_AREAS = 1;
    // The maximum areas required for LV1 Project
    private static final int L1_MAX_AREAS = 4;
    // Number of unknown areas required for LV1 Project
    private static final int L1_UNKNOWN = 1;
    
    // The minimum months required for LV2 Project
    private static final int L2_MIN_TIME = 1;
    // The maximum months required for LV2 Project
    private static final int L2_MAX_TIME = 8;
    // The minimum function points/month required for LV2 Project
    private static final int L2_MIN_FP = 8;
    // The maximum function points/month required for LV2 Project
    private static final int L2_MAX_FP = 10;
    // The minimum areas required for LV2 Project
    private static final int L2_MIN_AREAS = 1;
    // The maximum areas required for LV2 Project
    private static final int L2_MAX_AREAS = 8;
    // Number of unknown areas required for LV2 Project
    private static final int L2_UNKNOWN = 2;
    
    // The minimum months required for LV3 Project
    private static final int L3_MIN_TIME = 6;
    // The maximum months required for LV3 Project
    private static final int L3_MAX_TIME = 12;
    // The minimum function points/month required for LV3 Project
    private static final int L3_MIN_FP = 12;
    // The maximum function points/month required for LV3 Project
    private static final int L3_MAX_FP = 20;
    // The minimum areas required for LV3 Project
    private static final int L3_MIN_AREAS = 6;
    // The maximum areas required for LV3 Project
    private static final int L3_MAX_AREAS = 12;
    // Number of unknown areas required for LV3 Project
    private static final int L3_UNKNOWN = 3;
    
    // The minimum months required for LV4 Project
    private static final int L4_MIN_TIME = 12;
    // The maximum months required for LV4 Project
    private static final int L4_MAX_TIME = 24;
    // The minimum function points/month required for LV4 Project
    private static final int L4_MIN_FP = 24;
    // The maximum function points/month required for LV4 Project
    private static final int L4_MAX_FP = 30;
    // The minimum areas required for LV4 Project
    private static final int L4_MIN_AREAS = 12;
    // The maximum areas required for LV4 Project
    private static final int L4_MAX_AREAS = 24;
    // Number of unknown areas required for LV4 Project
    private static final int L4_UNKNOWN = 4;
    
    // The minimum months required for LV5 Project
    private static final int L5_MIN_TIME = 1;
    // The maximum months required for LV5 Project
    private static final int L5_MAX_TIME = 24;
    // The minimum function points/month required for LV5 Project
    private static final int L5_MIN_FP = 30;
    // The maximum function points/month required for LV5 Project
    private static final int L5_MAX_FP = 40;
    // The minimum areas required for LV5 Project
    private static final int L5_MIN_AREAS = 5;
    // The maximum areas required for LV5 Project
    private static final int L5_MAX_AREAS = 30;
    
    private static List<String> projectNameList;
    private static ArrayList<Project> thisWeekProjList;
    
    private RandProjGenerator() {
    }
    
    /**
     * Generate random level
     * @return level from 1 to 5
     */
    private static int generateLevel() {
        return r.nextInt(MAX_LVL - MIN_LVL + 1) + MIN_LVL;
    }
    
    /**
     * Generate random main skill required for a project
     * @return the name of a random technical skill
     */
    private static Skill generateType(SkillsList list) {
        SkillsList techSkills = new SkillsList();
        for(Skill s: list.getList()) {
            if(s.getType().equals("Technical")) {
                techSkills.add(new Skill(s));
            }
        }
        int index = r.nextInt(techSkills.size());
        Skill result = new Skill(techSkills.getList().get(index));
        return result;
    }
    
    /**
     * Generate random months required for a project
     * @return number of months based on level
     */
    private static int generateTime(int level) throws Exception {
        if(level==1) {
            return r.nextInt(L1_MAX_TIME - L1_MIN_TIME + 1) + L1_MIN_TIME;
        }
        else if(level==2) {
            return r.nextInt(L2_MAX_TIME - L2_MIN_TIME + 1) + L2_MIN_TIME;
        }
        else if(level==3) {
            return r.nextInt(L3_MAX_TIME - L3_MIN_TIME + 1) + L3_MIN_TIME;
        }
        else if(level==4) {
            return r.nextInt(L4_MAX_TIME - L4_MIN_TIME + 1) + L4_MIN_TIME;
        }
        else if(level==5) {
            return r.nextInt(L5_MAX_TIME - L5_MIN_TIME + 1) + L5_MIN_TIME;
        }
        else {
            throw new Exception("Invalid level(1-5)");
        }
    }
    
    /**
     * Generate random FP/month required for a project
     * @return number of FP/month based on level
     */
    private static int generateFP(int level) throws Exception {
        if(level==1) {
            return (r.nextInt(L1_MAX_FP - L1_MIN_FP + 1) + L1_MIN_FP) * FP_RATE;
        }
        else if(level==2) {
            return (r.nextInt(L2_MAX_FP - L2_MIN_FP + 1) + L2_MIN_FP) * FP_RATE;
        }
        else if(level==3) {
            return (r.nextInt(L3_MAX_FP - L3_MIN_FP + 1) + L3_MIN_FP) * FP_RATE;
        }
        else if(level==4) {
            return (r.nextInt(L4_MAX_FP - L4_MIN_FP + 1) + L4_MIN_FP) * FP_RATE;
        }
        else if(level==5) {
            return (r.nextInt(L5_MAX_FP - L5_MIN_FP + 1) + L5_MIN_FP) * FP_RATE;
        }
        else {
            throw new Exception("Invalid level(1-5)");
        }
    }
    
    /**
     * Generate random areas required for a project
     * @return number of areas based on level
     */
    private static int generateAreas(int level) throws Exception {
        if(level==1) {
            return r.nextInt(L1_MAX_AREAS - L1_MIN_AREAS + 1) + L1_MIN_AREAS;
        }
        else if(level==2) {
            return r.nextInt(L2_MAX_AREAS - L2_MIN_AREAS + 1) + L2_MIN_AREAS;
        }
        else if(level==3) {
            return r.nextInt(L3_MAX_AREAS - L3_MIN_AREAS + 1) + L3_MIN_AREAS;
        }
        else if(level==4) {
            return r.nextInt(L4_MAX_AREAS - L4_MIN_AREAS + 1) + L4_MIN_AREAS;
        }
        else if(level==5) {
            return r.nextInt(L5_MAX_AREAS - L5_MIN_AREAS + 1) + L5_MIN_AREAS;
        }
        else {
            throw new Exception("Invalid level(1-5)");
        }
    }
    
    /**
     * Generate random unknown areas required for a project
     * @return number of unknown areas based on level
     */
    private static int generateUnknowns(int level, int areas) throws Exception {
        if(level==1) {
            return L1_UNKNOWN;
        }
        else if(level==2) {
            return L2_UNKNOWN;
        }
        else if(level==3) {
            return L3_UNKNOWN;
        }
        else if(level==4) {
            return L4_UNKNOWN;
        }
        else if(level==5) {
            return areas;
        }
        else {
            throw new Exception("Invalid level(1-5)");
        }
    }
    
    public static void loadProjectNameList(){
        projectNameList = new ArrayList<String>();
        File file = new File(PROJECT_LIST_FILE);
        Scanner scanner = null;
        
        try {
            scanner =  new Scanner(file);
            
            while(scanner.hasNext()) {
                String s = scanner.nextLine();
                projectNameList.add(s);
            }
            
        } catch(IOException ex) {
            
        }
        scanner.close();
    }
    
    private static String generateProjectName(){
        String name = null;
        Random random = new Random();
        Player player = Player.getInstance();
        
        ArrayList<Project> projects = player.getProjects().size() != 0
                ? new ArrayList<Project>(player.getProjects().values()) 
                : null;
        if(projects != null){
            for(Project p : projects){
                do{
                    name = projectNameList.get(random.nextInt(projectNameList.size()));
                } while(p.getName().equals(name));
            }
        } else {
            name = projectNameList.get(random.nextInt(projectNameList.size()));
        }
        return name;
    }
    
    /**
     * Generate random project
     * @return a randomly created project
     */
    private static Project generateProject(SkillsList list) throws Exception {
        int level = generateLevel();
        int time = generateTime(level);
        Skill skill = generateType(list);
        int fpRate = generateFP(level);
        int areas = generateAreas(level);
        int unknown = generateUnknowns(level, areas);
        String name = generateProjectName();
//        System.out.println("Level: "+level);
//        System.out.println("Areas: "+areas);
//        System.out.println("Unknown: "+unknown);
//        System.out.println("Rate: "+fpRate);
        AreasList a = RandAreaGenerator.generateAreasList(areas, unknown, 
                                                             (int)(fpRate*0.1));
        return new Project(level, time, skill, a, name);
    }
    
    public static ArrayList<Project> generateProjectList() {
        if(thisWeekProjList != null) {
            return thisWeekProjList;
        }
        
        thisWeekProjList = new ArrayList<Project>();
        
        try {
            for(int i=1; i<=6; i++) {
                thisWeekProjList.add(
                        generateProject(DefaultSkillList.getSkillsList()));
            }
            
        } catch (Exception ex) {
            System.out.println("Not Added");
            ex.printStackTrace();
            Logger.getLogger(RandProjGenerator.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return thisWeekProjList;
    }
    
    public static void main(String[] args) {
        SkillsList skillsModel = new SkillsList();
        try {
            LoadSkills a = new LoadSkills(skillsModel);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            generateProject(skillsModel).print();
            generateProject(skillsModel).print();
            generateProject(skillsModel).print();
            generateProject(skillsModel).print();
            generateProject(skillsModel).print();
            generateProject(skillsModel).print();
        } catch (Exception ex) {
            Logger.getLogger(RandProjGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void resetProjList() {
        thisWeekProjList.clear();
        thisWeekProjList = null;
    }

    public static ArrayList<Project> getThisWeekProjList() {
        return thisWeekProjList;
    }
}
