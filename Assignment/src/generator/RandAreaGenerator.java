/*
 * @author Aoi Mizu
 */

package generator;

import java.util.Random;
import model.area.Area;
import model.area.AreasList;

public class RandAreaGenerator {
    private static Random r = new Random();
    
    private RandAreaGenerator() {
    }
    
    /**
     * Generate a list of default areas
     * @return list of default areas
     */
    private static AreasList getModel() {
        // Create model list of default areas
        AreasList model = new AreasList();
        model.add(new Area(Area.ALGORITHM));
        model.add(new Area(Area.ANALYSIS));
        model.add(new Area(Area.ARCHITECTURE));
        model.add(new Area(Area.BUSINESS_MODELING));
        model.add(new Area(Area.CHANGE_MANAGEMENT));
        
        model.add(new Area(Area.COMMUNICATION));
        model.add(new Area(Area.CLASS_DIAGRAMS));
        model.add(new Area(Area.CODING));
        model.add(new Area(Area.CONFIGURATION));
        model.add(new Area(Area.CONSISTENCY));
        
        model.add(new Area(Area.DATABASE));
        model.add(new Area(Area.DEBUGGING));
        model.add(new Area(Area.DEPLOYMENT));
        model.add(new Area(Area.DESIGN_PATTERNS));
        model.add(new Area(Area.DESIGNING));
        
        model.add(new Area(Area.DOCUMENTATION));
        model.add(new Area(Area.ENVIRONMENT));
        model.add(new Area(Area.GENERALIZATION));
        model.add(new Area(Area.IMPLEMENTATION));
        model.add(new Area(Area.INTEGRATION));
        
        model.add(new Area(Area.INTERNATIONALIZATION));
        model.add(new Area(Area.OPTIMIZATION));
        model.add(new Area(Area.PLANNING));
        model.add(new Area(Area.PROJ_MANAGEMENT));
        model.add(new Area(Area.REFACTORING));
        
        model.add(new Area(Area.RELIABILITY));
        model.add(new Area(Area.RISK_MANAGEMENT));
        model.add(new Area(Area.SCALABILITY));
        model.add(new Area(Area.SECURITY));
        model.add(new Area(Area.SOFTWARE_QUALITY));
        
        model.add(new Area(Area.TEAM_WORK));
        model.add(new Area(Area.TECHNIQUES));
        model.add(new Area(Area.TESTING));
        model.add(new Area(Area.USABILITY));
        model.add(new Area(Area.USE_CASES));
        return model;
    }
    
    /**
     * Generate random areas list
     * @param total number of areas
     * @param unknowns number of unknown areas
     * @param fb total function points
     * @return a randomly created areas list
     */
    public static AreasList generateAreasList(int total, int unknowns, int fp) 
                                                              throws Exception {
        AreasList remain = getModel();
        AreasList result = new AreasList();
        if(total>remain.size()) {
            throw new Exception("Invalid SIZE(MAX:"+remain.size()+"): "+total);
        }
        for(int i=0; i<total; i++) {
            int j = r.nextInt(remain.size());
            result.add(new Area(remain.get(j)));
            remain.remove(j);
        }
        shareFP(result, fp);
        shareUnknown(result, unknowns);
        return result;
    }
    
    /**
     * Add function points(FPs) to each area in the list
     * @param list the list of areas
     * @param fp total FBs of the list
     */
    private static void shareFP(AreasList list, int fp) {
        int total = fp;
        for(int i=0; i<list.size(); i++) {
            // The final area will get the remain FBs
            if(i==list.size()-1) {
                list.get(i).setFP(total*RandProjGenerator.FP_RATE);
            }
            else {
                // If FBs equals number of areas, each area will get 1 FB
                if(total-list.size()+i==0) {
                    list.get(i).setFP(1*RandProjGenerator.FP_RATE);
                    total = total - 1;
                }
                else {
                    // Generate a number from 1 to (remain FBs-remain areas)
                    int point = r.nextInt(total-list.size()+i+1)+1;
                    list.get(i).setFP(point*RandProjGenerator.FP_RATE);
                    total = total - point;
                }
            }
        }
    }
    
    /**
     * Set unknown areas in the list
     * @param list the list of areas
     * @param unknown total unknown areas of the list
     */
    private static void shareUnknown(AreasList list, int unknown) {
        if(unknown>=list.size()) {
            for(Area a: list.getList()) {
                a.setKnown(false);
            }
        }
        else {
            int total = unknown;
            AreasList remain = new AreasList(list);
            while(total>0) {
                // Get a random area in the remain list
                int i = r.nextInt(remain.size());
                
                // Set the status of the area with same name from the main list
                String name = remain.get(i).getName();
                list.get(name).setKnown(false);
                
                // Remove the area from the remain list
                remain.remove(i);
                total--;
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        generateAreasList(4, 3, 10).print();
        generateAreasList(4, 3, 10).print();
        generateAreasList(4, 3, 10).print();
        generateAreasList(4, 3, 10).print();
        generateAreasList(4, 3, 10).print();
        generateAreasList(4, 3, 10).print();
//        generateAreasList(2, 1, 3).print();
//        generateAreasList(2, 1, 3).print();
//        generateAreasList(2, 1, 3).print();
//        generateAreasList(2, 1, 3).print();
        
//        generateAreasList(2, 1, 2).print();
//        generateAreasList(2, 1, 2).print();
//        generateAreasList(2, 1, 2).print();
//        generateAreasList(2, 1, 2).print();
//        generateAreasList(2, 1, 2).print();
    }
}
