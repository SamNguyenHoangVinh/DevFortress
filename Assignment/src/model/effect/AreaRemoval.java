/*
 * @author Aoi Mizu
 */

package model.effect;

import model.Developer;

public class AreaRemoval implements EffectInterface {
    
    private Developer d;
    private EffectInterface e;
    
    public AreaRemoval(Developer d, EffectInterface e) {
        this.d = d;
        this.e = e;
    }

    @Override
    public void applyEffect() {
        // Remove the area
        d.getCurrArea().setFinished();
        if(e!=null) {
            e.applyEffect();
        }
    }
}
