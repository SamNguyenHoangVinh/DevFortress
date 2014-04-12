/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.saveload.SaveLoad;

/**
 *
 * @author HungHandsome
 */
public class PlayerRank implements Serializable {
    private static final long serialVersionUID = 8205070534567417943L;
    
    public static final int MAX_SIZE = 10;
    
    private static PlayerRank rank;
    
    private ArrayList<PlayerInfo> players;
    private PlayerComparator comparator;
    
    private PlayerRank() {
        players = new ArrayList<PlayerInfo>();
        comparator = new PlayerComparator();
    }
    
    public boolean addPlayer(Player p) {
        PlayerInfo pi = new PlayerInfo(p.getName(), p.getWeekNum());
        
        players.add(pi);
        Collections.sort(players, comparator);
        
        if(players.size() > MAX_SIZE) {
            for(int i=players.size()-1; i>MAX_SIZE-1; i--) {
                players.remove(i);
            }
        }
        
        return players.contains(pi);
    }
    
    public boolean checkOnTop(int duration) {
        if(players.size() < MAX_SIZE) {
            return true;
        }
        
        for(PlayerInfo p : players) {
            if(p.getWeekNum() < duration) {
                return true;
            }
        }
        return false;
    }
    
    public static PlayerRank getInstance() {
        rank = SaveLoad.loadRank();
        if(rank == null) {
            return new PlayerRank();
        }
        return rank;
    }

    public ArrayList<PlayerInfo> getPlayers() {
        return players;
    }    
    
    private class PlayerComparator implements Comparator, Serializable {
        private static final long serialVersionUID = 8205070534567417943L;
        
        @Override
        public int compare(Object o1, Object o2) {
            int c1 = ((PlayerInfo)o1).getWeekNum();
            int c2 = ((PlayerInfo)o2).getWeekNum();
            
            if(c1 > c2) {
                return -1;
            }
            else if(c1 == c2) {
                return 0;
            }
            else {
                return 1;
            }
        }
    }
    
    /*
     * Reset ranking, for testing purpose only
     */
    public void reset() {
        players = new ArrayList<PlayerInfo>();
        comparator = new PlayerComparator();
    }
}
