/*
 * @author Aoi Mizu
 */

package model.effect;

import model.Developer;

public class HappinessModification implements EffectInterface {
    
    private int happy;
    private Developer d;
    private EffectInterface e;
    
    public HappinessModification(int happy, Developer d, EffectInterface e) {
        this.happy = happy;
        this.d = d;
        this.e = e;
    }

    @Override
    public void applyEffect() {
        // Set happiness of developer
        d.setEmotion(happy);
        if(e!=null) {
            e.applyEffect();
        }
    }
}
