/*
 * @author Aoi Mizu
 */

package model.skill;

public class SkillTime2 implements SkillInterface {

    @Override
    public int getCost(int startPoint, int level) {
        if(level < 1 || level > 10) {
            return 0;
        }
        
        // Calculate the cost by the method cost(n) = cost(n-1) * 2
        return (int) ((int) startPoint * Math.pow(2, level - 1));
    }

}
