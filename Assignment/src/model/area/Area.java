/*
 * @author Aoi Mizu
 */
package model.area;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Developer;
import model.calculator.FBCalculator;
import model.project.Project;
import model.skill.Skill;

/**
 * This class represents a functional area of a project.
 *
 * @author Aoi Mizu
 */
public class Area implements Serializable {

    //Types of area
    public static final String ALGORITHM = "Algorithms";
    public static final String ANALYSIS = "Analysis";
    public static final String ARCHITECTURE = "Architecture";
    public static final String BUSINESS_MODELING = "Business Modeling";
    public static final String CHANGE_MANAGEMENT = "Change Management";
    public static final String COMMUNICATION = "Communication";
    public static final String CLASS_DIAGRAMS = "Class Diagrams";
    public static final String CODING = "Coding";
    public static final String CONFIGURATION = "Configuration";
    public static final String CONSISTENCY = "Consistency";
    public static final String DATABASE = "Database";
    public static final String DEBUGGING = "Debugging";
    public static final String DEPLOYMENT = "Deployment";
    public static final String DESIGN_PATTERNS = "Design Patterns";
    public static final String DESIGNING = "Designing";
    public static final String DOCUMENTATION = "Documentation";
    public static final String ENVIRONMENT = "Environment";
    public static final String GENERALIZATION = "Generalization";
    public static final String IMPLEMENTATION = "Implementation";
    public static final String INTEGRATION = "Integration";
    public static final String INTERNATIONALIZATION = "Internationalization";
    public static final String OPTIMIZATION = "Optimization";
    public static final String PLANNING = "Planning";
    public static final String PROJ_MANAGEMENT = "Project Management";
    public static final String REFACTORING = "Refactoring";
    public static final String RELIABILITY = "Reliability";
    public static final String RISK_MANAGEMENT = "Risk Management";
    public static final String SCALABILITY = "Scalability";
    public static final String SECURITY = "Security";
    public static final String SOFTWARE_QUALITY = "Software Quality";
    public static final String TEAM_WORK = "Team Work";
    public static final String TECHNIQUES = "Techniques";
    public static final String TESTING = "Testing";
    public static final String USABILITY = "Usability";
    public static final String USE_CASES = "Use Cases";
    private String name; //Area name
    private int fp; //Function points of this area
    private int done; //The number of fps which are done
    private boolean finish; //True if this area is finished.
    private boolean known;
    private Project project;
    private LinkedHashMap<String, Developer> devs;

    /**
     * Construct an Area object with a given
     * <code>name</code>.
     *
     * @param name The name of this area.
     */
    public Area(String name) {
        fp = 0;
        init(name);
    }

    /**
     * Construct an Area object with a given
     * <code>name</code> <br/> and
     * <code>fp</code>
     *
     * @param name The name of this area.
     * @param fp The number of function points of this area.
     */
    public Area(String name, int fp) {
        this.fp = fp;
        init(name);
    }

    /**
     * Construct an Area object & copy all data from another Area object <br/>
     * to this Area object.
     *
     * @param a The source Area object.
     */
    public Area(Area a) {
        this.name = a.getName();
        this.fp = a.getFP();
        done = a.getDone();
        finish = a.isFinished();
        known = a.isKnown();
        devs = a.getDevs();
    }

    private void init(String name) {
        this.name = name;
        done = 0;
        finish = false;
        known = true;
        devs = new LinkedHashMap<String, Developer>();
    }

    public String getName() {
        return name;
    }

    public int getFP() {
        return fp;
    }

    public int getDone() {
        return done;
    }

    public boolean isFinished() {
        return finish;
    }

    public boolean isKnown() {
        return known;
    }

    public void setFP(int fp) {
        this.fp = fp;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public LinkedHashMap<String, Developer> getDevs() {
        return devs;
    }

    /**
     * Set an area to be completely finished without any work (Use to remove
     * area by random events) All developers are free to be assigned to other
     * areas
     */
    public void setFinished() {
//        for(Developer d: devs.values()) {
//            project.removeDeveloper(d.getName());
//            d.setCurrProj(null);
//        }
//        devs.clear();
        removeAllDev();
        done = fp;
        finish = true;
    }

    /**
     * Assign a developer to an area
     *
     * @param d developer to be assigned
     * @return True if the adding is successful. False otherwise.
     */
    public boolean addDeveloper(Developer d) {
        if (finish || devs.containsKey(d.getName())
                || (!d.isAvailable()
                && ((d.getCurrProj() != project)
                || (d.getCurrProj() == project && d.getCurrArea() != null)))) {
            return false;
        }

        devs.put(d.getName(), d);
        project.addDeveloper(d);
        d.setCurrArea(this);
//        System.out.println("Add Developer to Area SUCCESSFUL");

        return true;
    }

    /**
     * Calculate FBs done each week
     *
     * @param mainProSkill main skill of the project
     * @param number number of developers in the team
     */
    public void nextWeek(Skill mainProSkill) {
        if (!finish) {
            int point = 0;
            ArrayList<Developer> ds = new ArrayList<Developer>(devs.values());
            // Calculate the sum of FBs done by each developer
            for (int i = 0; i < devs.size(); i++) {
                Developer d = ds.get(i);
                // Evens happens
                if (d.getComputer() != null) {
                    d.receiveWeeklyEvent();

                    point += (d.isFpAffected()) ? d.getCurrFpOutput() * d.getComputer().getProductivity()
                            : FBCalculator.calculate(mainProSkill, this, devs.size(), d) * d.getComputer().getProductivity();
                    if (!devs.containsKey(d.getName())) {
                        ds.remove(ds.get(i));
                    }
                }
            }

            done += point;

            if (done >= fp) {
                // Set the project is finished
                setFinished();
            }
        }
    }

    /**
     * Remove all developers from this area
     */
    public void removeAllDev() {
        ArrayList<Developer> ds = new ArrayList<Developer>(devs.values());
        while (!devs.isEmpty()) {
            removeDev(ds.get(0));
            ds.remove(ds.get(0));
        }
        devs.clear();
    }

    public void removeDev(Developer dev) {
        dev.setCurrArea(null);
        devs.remove(dev.getName());
        project.removeDeveloper(dev.getName());
    }

    /**
     *
     */
    public void print() {
        if (known) {
            System.out.printf("Name: %20s | FPs : %3d | Done: %2d", name, fp, done);

            if (finish) {
                System.out.print(" | Finished\n");
            } else {
                System.out.print(" | Not finished\n");
            }
        } else {
            System.out.print("Name: ???????????????????? | ");
            System.out.print("FPs : ??? | ");
            System.out.printf("Done: %2d", done);

            if (finish) {
                System.out.print(" | Finished\t");
            } else {
                System.out.print(" | Not finished\t");
            }
            System.out.printf("Name: %20s | FPs: %3d | Done: %2d\n", name, fp, done);
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
