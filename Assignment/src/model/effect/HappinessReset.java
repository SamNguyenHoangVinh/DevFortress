/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.effect;

import java.util.Collection;
import model.Developer;
import model.player.Player;

public class HappinessReset {
    private HappinessReset() {
    }
    
    public static void reset() {
        // Reset happiness for all developers
        Collection<Developer> devs = Player.getInstance().getDevelopers().values();
        for(Developer d: devs) {
            d.setEmotion(Developer.HAPPY);
        }
    }
}
