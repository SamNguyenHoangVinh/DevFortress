/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

/**
 *
 * @author HungHandsome
 */
public abstract class AnimationUtils {
    
    private static final ToMetroAnimation metroAnim = new ToMetroAnimation();

    public static ToMetroAnimation getMetroAnim(int startY) {
        metroAnim.setStartY(startY);
        metroAnim.reset();
        return metroAnim;
    }
}
