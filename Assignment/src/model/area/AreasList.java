/*
 * @author Aoi Mizu
 */

package model.area;

import java.io.Serializable;
import java.util.ArrayList;
import model.skill.Skill;

public class AreasList implements Serializable {
    
    private ArrayList<Area> list;
    
    public AreasList() {
        list = new ArrayList<Area>();
    }
    
    public AreasList(AreasList list) {
        this.list = new ArrayList<Area>();
        for(Area a: list.getList()) {
            this.list.add(new Area(a));
        }
    }
    
    public ArrayList<Area> getList() {
        return list;
    }
    
    /**
     * Get the area in the list by index
     * @param index the index of the area
     * @return the area in the index
     */
    public Area get(int index) {
        return list.get(index);
    }
    
    /**
     * Get the area in the list by name
     * @param index the name of the area
     * @return the area has that certain name
     */
    public Area get(String name) {
        for(Area a: list) {
            if(a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }
    
    /**
     * Get the size of the list
     * @return total areas in the list
     */
    public int size() {
        return list.size();
    }
    
    /**
     * Add an area to the list
     * @param a an area to added
     */
    public boolean add(Area a) {
        for(Area area: list) {
            if(area.getName().equals(a.getName())) {
                return false;
            }
        }
        
        list.add(a);
        return true;
    }
    
    /**
     * Remove an area from the list, using the index of the corresponding area
     * @param index the index of the removed area
     */
    public void remove(int index) {
        list.remove(index);
    }
    
    /**
     * Remove an area from the list, using the name of the corresponding area
     * @param areaName the name of the removed area
     */
//    public void remove(String areaName) {
//        Area a = get(areaName);
//        
//        if(a != null) {
//            list.remove(a);
//        }        
//    }
    
    /**
     * Calculate FBs done each week
     * @param mainProSkill main skill of the project
     * @param devs number of developers in the team
     */
    public void nextWeek(Skill mainProSkill) {
        for(Area a: list) {
            a.nextWeek(mainProSkill);
        }
    }
    
    /**
     * Remove all developers from these areas.
     */
    public void removeAllDev() {
        for(Area a : list) {
            a.removeAllDev();
        }
    }
    
    /**
     * Check if all areas are finished
     * @return -true: if all areas are finished
     *         -false: there are still unfinished areas
     */
    public boolean isFinished() {
        for(Area a: list) {
            if(a.isFinished() == false) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Get the cost of all areas
     * @return the cost of all areas based on the total of FPs
     */
    public int getCost() {
        int total = 0;
        for(Area a: list) {
            total = total + a.getFP()*10;
        }
        return total;
    }
    
    public void print() {
        System.out.println("---------------------LIST---------------------");
        for(Area a: list) {
            a.print();
            System.out.println("");
        }
    }
    
}
