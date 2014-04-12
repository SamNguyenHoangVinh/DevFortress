/*
 * @author Aoi Mizu
 */

package model.effect;

import model.area.Area;

public class AreaModification implements EffectInterface {
    
    private Area area;
    private int fp;
    private EffectInterface e;
    
    public AreaModification(int fp, Area a, EffectInterface e) {
        this.area = a;
        this.fp = fp;
        this.e = e;
    }

    @Override
    public void applyEffect() {
        if(area == null) { //If the developer is currently free.
            return;
        }
        
        // Add FPs to an area
        int i = area.getFP() + fp;

        if(i<=0 || i<=area.getDone()) {
            // If FPs reduced to zero or all FPs are done, the area will be finished
            area.isFinished();
        }
        else {
            // Set the new FPs of the area
            area.setFP(area.getFP()+fp);
        }
        if(e!=null) {
            e.applyEffect();
        }
    }
}
