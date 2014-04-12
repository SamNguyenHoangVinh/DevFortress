/*
 * @author Aoi Mizu
 */

package model.effect;

import model.Developer;

public class TeamFPModification implements EffectInterface {
    
    private int fp;
    private Developer d;
    private EffectInterface e;
    
    public TeamFPModification(int fp, Developer d, EffectInterface e) {
        this.fp = fp;
        this.d = d;
        this.e = e;
    }

    @Override
    public void applyEffect() {
        // Set the FPs done in this week for all developers
        for(Developer dev: d.getCurrProj().getDevelopers().values()) {
            dev.setCurrFpOutput(fp);
            dev.setFpAffected(true);
        }
        if(e!=null) {
            e.applyEffect();
        }
    }
}
