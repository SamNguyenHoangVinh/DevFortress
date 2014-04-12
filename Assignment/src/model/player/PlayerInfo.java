/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.player;

import java.io.Serializable;

/**
 * This class represent the data of Player instance which is placed in the
 * HighScore.
 * @author HungHandsome
 */
public class PlayerInfo implements Serializable {
    private static final long serialVersionUID = 8205070534567417943L;
    
    private String name;
//    private String duration;
    private int weekNum;

    public PlayerInfo(String name, int weekNum) {
        this.name = name;
        this.weekNum = weekNum;
//        duration = weekNum + " W";
    }
    
    public String getName() {
        return name;
    }

//    public String getDuration() {
//        return duration;
//    }

    public int getWeekNum() {
        return weekNum;
    }
}
