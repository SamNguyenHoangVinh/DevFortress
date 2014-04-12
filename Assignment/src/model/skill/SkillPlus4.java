/*
 * @author Aoi Mizu
 */

package model.skill;

public class SkillPlus4 implements SkillInterface {

    @Override
    public int getCost(int startPoint, int level) {
        if(level < 1 || level > 10) {
            return 0;
        }
        
        // Calculate the cost by the method cost(n) = cost(n-1) + 4
        return startPoint + level + level + level + level - 4;
    }

}
