/*
 * @author Aoi Mizu
 */

package model.effect;

import java.util.ArrayList;
import java.util.Random;
import model.Developer;
import model.player.Player;

public class HappinessRemoval {
    
    private static int total_Rate = 100;
    private static int leave_Rate = 10;
    
    private HappinessRemoval(){
    }
    
    public static void applyEffect() {
        Player player = Player.getInstance();
        ArrayList<String> removed = new ArrayList<String>();
        
        for(Developer d: player.getDevelopers().values()) {
            if(d.getEmotion()<=Developer.UNHAPPY) {
                // Randomly check if unhappy developers leave
                Random r = new Random();

                int i = r.nextInt(total_Rate + 1);
                if(i < leave_Rate) {
                    removed.add(d.getName());
                }
            }
        }
        
        // Remove developers
        for(String s: removed) {
            System.out.println("Leave: Developer: "+s);
            player.removeDeveloper(s);
        }
    }
}
