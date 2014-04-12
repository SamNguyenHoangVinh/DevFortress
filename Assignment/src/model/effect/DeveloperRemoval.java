/*
 * @author Aoi Mizu
 */

package model.effect;

import model.Developer;
import model.player.Player;

public class DeveloperRemoval implements EffectInterface {
    private Developer d;
    private EffectInterface e;
    
    public DeveloperRemoval(Developer d, EffectInterface e) {
        this.d = d;
        this.e = e;
    }

    @Override
    public void applyEffect() {
        // Completely remove developer
        Player.getInstance().removeDeveloper(d);
        
        if(e!=null) {
            e.applyEffect();
        }
    }
}
