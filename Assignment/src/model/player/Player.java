/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.player;

import generator.CompGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import model.Developer;
import model.area.Area;
import model.project.Project;
import model.room.*;
import model.saveload.SaveLoad;

/**
 * The singleton player.
 * @author Hung
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 8205070534567417638L;
    
    public static final int MAX_DEV_NUM = 16;
    
    private static int autoincreProjID = 0; //The autoincrement project ID which 
                                  //increases by 1 whenever a project is accepted
    private static Player player;
    
    private String name; //Player's name.
    private LinkedHashMap<String, Developer> developers; //The list of developers
                                              //this player is currently hiring.
    private LinkedHashMap<String, Project> projects; //The list of accepted projects.
    
    // The list of computers the player has bought.
    private LinkedHashMap<String, Hardware> computers;
    private LinkedHashMap<String, Hardware> tableComputers;
    private LinkedHashMap<Integer, RoomSet> roomSet;
//    private Room room;
    private int money; //The current number of money.
//    private int currTime;
    private int day = 1;
    private int week = 1;
    private int month = 1;
    private int year = 1;
    private int weekNum = 0;
    private int curRoomSetIdx;
    private int curRoomIdx;
    private int autoincreRoomSetId;
    private int autoincreRoomId;
    
    private Player() {
        init();
    }

    private void init() {
        money = 1000;
        developers = new LinkedHashMap<String, Developer>();
        projects = new LinkedHashMap<String, Project>();
        computers = new LinkedHashMap<String, Hardware>();
        tableComputers = new LinkedHashMap<String, Hardware>();
        roomSet = new LinkedHashMap<Integer, RoomSet>();
        autoincreRoomSetId = 0;
        autoincreRoomId = 0;
        
//        RoomSet.resetID();
//        Room.resetID();        
        
//        curRoomSetId = rs.getId();
//        curRoomId = r.getRoomNo();
    }
    
    public void initComputers() {
        Hardware comp = CompGenerator.generateCompList().get(0);
        for(int i = 0; i < 7; i++) {
            Hardware computer = comp.cloneHardware();
            computers.put(computer.getSerial(), computer);
        }
    }
    
    /**
     * Hire a new developer.
     * @param developer The new developer to hire.
     */
    public void hireDeveloper(Developer developer) {
//        if(developers.size() >= MAX_DEV_NUM) {
//            System.out.println("SIZE : " + developers.size());
//            return;
//        }
        
        developers.put(developer.getName(), developer);
    }

    /**
     * Assign the developer <code>dev</code> to the project <code>proj</code>.
     * @param dev The assigned developer.
     * @param proj The project that needs the developer.
     */
    public void assignDeveloper(Developer dev, Project proj) {
        proj.addDeveloper(dev);
    }
    
    /**
     * Assign the developer, whose name is <code>devName</code> to the project
     * <br/>whose ID is <code>projId</code>.
     * @param devName The assigned developer's name.
     * @param projId The ID of the project that needs the developer.
     */
//    public void assignDeveloper(String devName, String projId) {
//        Developer dev = developers.get(devName);
//        Project proj = projects.get(projId);
//        proj.addDeveloper(dev);
//    }
    
    /**
     * Assign multiple developers from <code>dev</code> list to the project<br/>
     * <code>proj</code>.
     * @param devs The list of assigned developers.
     * @param area The project that needs the developers.
     */
    public void assignDeveloper(ArrayList<Developer> devs, Area area) {
        for(Developer d : devs) {
            area.addDeveloper(d);
        }        
    }
        
    /**
     * Accept a new project. The player will receive 25% of the capital of <br/>
     * this new project. The project ID is auto-increment.
     * @param project The new accepted project.
     */
    public void acceptProject(Project project) {
        project.setId(autoincreProjID++);
        projects.put("" + project.getID(), project);
        money += 0.25 * project.getCapital();
    }

    /**
     * Cancel the chosen project.
     * @param projectID The project ID of the canceled project.
     */
    public void cancelProject(String projectID) {
        Project project = projects.remove(projectID);
        
//        try {
            project.cancel();

            //There are probably some events here.

            //Pay back the money to the customer due to contract breaking.
            money -= (int)(0.25 * project.getCapital());
//        } catch(Exception ex) {
//            System.out.println("THERE IS A NULL POINTER EXCEPTION");
//            if(project == null) {
//                for(String k : projects.keySet()) {
//                    System.out.print(k + "\t");
//                }
//                System.out.println();
//            }
//        }
    }
    
    /**
     * Receive the remaining cash from the completed projects & then remove<br/>
     * them.
     */
    public void removeFinishedProject() {
        ArrayList<String> projNames = new ArrayList<String>(projects.keySet());
        for(int i=0; i<projNames.size(); i++) {
            Project p = projects.get(projNames.get(i));
            if(p.isFinished()) {
                money += (int)(0.75 * p.getCapital());
                projects.remove(p.getID()+"");
                p.cancel();
            }
        }
    }
    
    /**
     * Buy a new developer.
     * @param computer The computer to be bought.
     */
    public void buyComputer(Hardware computer) {
        computers.put(computer.getSerial(), computer);
    }
    
    public boolean buyComputer(Hardware computer, int quantity) {
        int totalPayment = computer.getPrice() * quantity;
        if(totalPayment <= money) {
            for(int i = 0; i < quantity; i++) {
                buyComputer(computer.cloneHardware());
            }
            money -= totalPayment;
            return true;
        }
        return false;
    }
    
    /**
     * Sell the given computer.
     * @param computer The computer to be sold.
     * @return True if removing successful, False otherwise.
     */
    public boolean sellComputer(Hardware computer) {
        if(computer.getTable() != null){
            computer.getTable().removeComputer(computer);
            money += computer.getPrice()/2;
            return true;
        } else if(computers.remove(computer.getSerial()) != null){
            money += computer.getPrice()/2;
            return true;
        }
        return false;
    }
    
    /**
     * Upgrade the given computer.
     * @param computer The computer to be upgraded.
     * @return True if upgrade successful, False otherwise.
     */
    public boolean upgradeComputer(Hardware computer) {
        if(!computer.isEquipped()) {
            computer.setEquipPoint(Hardware.UPGRADED_PRO);
            money -= 1000;
            return true;
        }
        return false;
    }
    
    /**
     * Remove the given developer. Return true if removing succeeds. <br/>
     * Otherwise, return false.
     * @param dev The developer to be removed.
     * @return True if removing successful.
     */
    public boolean removeDeveloper(Developer dev) {
        removeDevSeat(dev);
        return developers.remove(dev.getName()) != null;
    }
    
    /**
     * Remove the developer whose name is <code>devName</code>. Return true if 
     * <br/>removing succeeds. Otherwise, return false.
     * @param devName The name of the developer to be removed.
     * @return True if removing successful.
     */
    public boolean removeDeveloper(String devName) {
        Developer dev = developers.get(devName);
        removeDevSeat(dev);
        return developers.remove(devName) != null;
    }
    
    private void removeDevSeat(Developer dev) {
        try {
//            System.out.println("removeDevSeat TRY");
//            dev.getCurrProj().removeDeveloper(dev.getName());
            dev.getCurrArea().removeDev(dev);
        } catch(Exception ex) {
//            ex.printStackTrace();
//            System.out.println("removeDevSeat EXCEPTION");
        }
        
//        Room room = ((RoomView)(MainFrame.getInstance().getCurrPanel()))
//                    .getModel();
        Object[] ar = roomSet.values().toArray();
        RoomSet rs = (RoomSet)ar[roomSet.size()-1];
        
        for(Room room : rs.getRooms().values()) {
            boolean delSuccess = false;        
            for(WorkingTable tab : room.getTables()) {
                for(Seat seat : tab.getSeats()) {
                    if(seat.getCurrDev()!=null && 
                            seat.getCurrDev().getName().equals(dev.getName())) {
                        seat.setCurrDev(null);
                        delSuccess = true;
                        break;
                    }
                }
                if(delSuccess) {
                    break;
                }
            }
        }
        
//        room.updateGUI();
    }
    
    public ArrayList<Developer> getAvailableDev() {
        ArrayList<Developer> avaiDev = new ArrayList<Developer>();
        Iterator it = developers.keySet().iterator();
        
        while(it.hasNext()) {
            Developer dev = developers.get((String)(it.next()));
            if(dev.isAvailable()) {
                avaiDev.add(dev);
            }
        }
        
        return avaiDev;
    }
    
    public void paySalary() {
        for(Developer d : developers.values()) {
            money -= d.getSalary();
        }
    }
    
    /**
     * Add new RoomSet to this Player
     * @param rs The new RoomSet
     * @return True if the adding is successful. False otherwise
     */
    public boolean addRoomSet(RoomSet rs) {
        if(roomSet.containsKey(rs.getId())) {
            return false;
        }        
        roomSet.put(rs.getId(), rs);
        return true;
    }
    
    public boolean addRoom(Room r) {       
//        for(RoomSet s : roomSet.values()) {
//            if(s.getRooms().containsKey(r.getRoomNo())) {
//                return false;
//            }
//        }
        if(money < 1000) {
            return false;
        }
//        
        Object[] ar = roomSet.values().toArray();
        RoomSet rs = (RoomSet)ar[roomSet.size()-1];
//        
//        if(rs.size() < RoomSet.MAX_SIZE) {
//            rs.addRoom(r);
//        } else {
//            RoomSet newRs = new RoomSet();
//            newRs.addRoom(r);
//            addRoomSet(newRs);
//        }
        
        if(rs.size()>=RoomSet.MAX_SIZE 
                || rs.getRooms().containsKey(r.getRoomNo())) {
            return false;
        }
        rs.addRoom(r);
        
        money -= 1000;
        
        return true;
    }
    
    /**
     * Get the singleton Player instance. If the instance is null, load it <br/>
     * from auto save. If it's still null, initialize it.
     * @return The singleton Player instance.
     */
    public static Player getInstance() {
        // Check if player has already initialized
//        if(player==null) {
//            // Load game process in file
//            player = SaveLoad.load(SaveLoad.AUTOSAVE_PATH);
            if(player == null) {
//                player = new Player();
                reinitInstance();
            }
//        }
        return player;
    }
    
    /**
     * Get the singleton Player instance. If the instance is null, load it <br/>
     * from <code>savePath</code>.
     * @param savePath The path of a particular save.
     * @return The singleton Player instance.
     */
    public static Player getInstance(String savePath) {
        // Check if player has already initialized
        if(player==null) {
            // Load game process in file
            player = SaveLoad.load(savePath);
            if(player==null) {
//                player = new Player();
                reinitInstance();
            }
        }
        return player;
    }
    
    /**
     * Load Player instance from a save file with a given save path.
     * @param savePath the directory containing the save file
     * @return True if the instance is loaded successfully. False otherwise.
     */
    public static boolean loadPlayer(String savePath) {
        player = SaveLoad.load(savePath);
        return player != null;
    }
    
    public static boolean isNull(){
        if(player==null) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public void increaseWeekNum() {
        weekNum++;
    }
    
    public static void reinitInstance() {
        player = null;
        player = new Player();
        
        RoomSet rs = new RoomSet();
        Room r = new Room();
        
        rs.addRoom(r);
        rs.addRoom(new Room());
//        rs.addRoom(new Room());
//        rs.addRoom(new Room());
//        rs.addRoom(new Room());
//        rs.addRoom(new Room());
//        rs.addRoom(new Room());
//        rs.addRoom(new Room());
//        rs.addRoom(new Room());
        
        player.addRoomSet(rs);
        player.initComputers();
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public LinkedHashMap<String, Developer> getDevelopers() {
        return developers;
    }

    public LinkedHashMap<String, Project> getProjects() {
        return projects;
    }
    
    public LinkedHashMap<String, Hardware> getComputers(){
        return computers;
    }
    
    public LinkedHashMap<String, Hardware> getTableComputers() {
        return tableComputers;
    }

    public Room getRoom(int setIdx, int roomIdx) {
//        return roomSet.get(setIdx).getRooms().get(roomIdx);
        RoomSet rs = (RoomSet)(roomSet.values().toArray()[setIdx]);
        return rs.getRoom(roomIdx);
    }
    
    public Room getCurrRoom() {
//        if(roomSet == null) {
//            System.out.println("rooms NULL");
//        }
//        if(roomSet.get(curRoomSetIdx) == null) {
//            System.out.println("RoomSet NULL");
//        }
        
//        return roomSet.get(curRoomSetIdx).getRooms().get(curRoomIdx);
        RoomSet rs = (RoomSet)(roomSet.values().toArray()[curRoomSetIdx]);
        return rs.getRoom(curRoomIdx);
    }
    
    public int getNextRoomSetId() {
        int id = autoincreRoomSetId++;
        return id;
    }
    
    public int getNextRoomId() {
        int id = autoincreRoomId++;
        return id;
    }
    
    public boolean setCurrRoom(int setIdx, int roomIdx) {
        RoomSet rs = (RoomSet)(roomSet.values().toArray()[curRoomSetIdx]);
        if(setIdx>=roomSet.size() || roomIdx>=rs.size()) {
            return false;
        }
        
        curRoomSetIdx = setIdx;
        curRoomIdx = roomIdx;
        return true;
    }

    public LinkedHashMap<Integer, RoomSet> getRooms() {
        return roomSet;
    }

//    public void setRoom(Room room) {
//        this.room = room;
//    }
    
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

//    public synchronized int getDay() {
//        return day;
//    }
//
//    public synchronized void setDay(int day) {
//        this.day = day;
//    }
//    
//    public synchronized int getWeek() {
//        return week;
//    }
//
//    public synchronized void setWeek(int week) {
//        this.week = week;
//    }
//
//    public synchronized int getMonth() {
//        return month;
//    }
//
//    public synchronized void setMonth(int month) {
//        this.month = month;
//    }
//
//    public synchronized int getYear() {
//        return year;
//    }
//
//    public synchronized void setYear(int year) {
//        this.year = year;
//    }
    
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    
    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }
    
    public boolean isLost() {
        return (player.getMoney()<=0 || player.getDevelopers().isEmpty());
    }

    public static int getAutoincreProjID() {
        return autoincreProjID;
    }

    public int getCurRoomSetId() {
        return curRoomSetIdx;
    }

    public int getCurRoomId() {
        return curRoomIdx;
    }

    public int getAutoincreRoomSetId() {
        return autoincreRoomSetId;
    }

    public int getAutoincreRoomId() {
        return autoincreRoomId;
    }
    
    /*
     * Reset player, for testing purpose only
     */
    public void reset() {
        init();
    }
    
}
