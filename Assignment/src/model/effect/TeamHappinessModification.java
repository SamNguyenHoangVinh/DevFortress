/*
 * @author Aoi Mizu
 */

package model.effect;

import model.Developer;

public class TeamHappinessModification implements EffectInterface {
    
    private int happy;
    private Developer d;
    private EffectInterface e;
    
    public TeamHappinessModification(int happy, Developer d, EffectInterface e) {
        this.happy = happy;
        this.d = d;
        this.e = e;
    }

    @Override
    synchronized public void applyEffect() {
        // Set the happiness values for all developers
        for(Developer dev: d.getCurrProj().getDevelopers().values()) {
            dev.setEmotion(happy);
        }
        if(e!=null) {
            e.applyEffect();
        }
    }
}
