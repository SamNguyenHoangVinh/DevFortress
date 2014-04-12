/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author s3342128
 */
public class MouseState {
    
    public static final int NORMAL = 0;
    public static final int DRAG_NEW_DEV = 1;
    
    private static Object currDraggedObj;
    private static int currState;
    
    public static Cursor getCustomMouseCursor(String iconPath) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = new ImageIcon(iconPath).getImage();
        return toolkit.createCustomCursor(image, new Point(5, 5), "img");
    }
    
    public static Cursor getCustomMouseCursor(Image icon) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.createCustomCursor(icon, new Point(5, 5), "img");
    }
    
    public static Cursor getNormalMouseCursor() {
        return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }

    public static int getCurrState() {
        return currState;
    }

    public static void setCurrState(int currState) {
        MouseState.currState = currState;
    }

    public static Object getCurrDraggedObj() {
        return currDraggedObj;
    }

    public static void setCurrDraggedObj(Object currDraggedObj) {
        MouseState.currDraggedObj = currDraggedObj;
    }
    
}
