/*
 * @author Aoi Mizu
 */

package model.effect;

import model.Developer;

public class FPModification implements EffectInterface {
    
    private int fp;
    private Developer d;
    private EffectInterface e;
    
    public FPModification(int fp, Developer d, EffectInterface e) {
        this.fp = fp;
        this.d = d;
        this.e = e;
    }

    @Override
    public void applyEffect() {
        // Set FPs output of developer in a week
        d.setCurrFpOutput(fp);
        d.setFpAffected(true);
        if(e!=null) {
            e.applyEffect();
        }
    }
}
