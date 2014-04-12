/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menubar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import model.Developer;
import model.project.Project;
import view.FontUtilities;
import view.SoundUtilities;
import view.ViewUtilities;

/**
 *
 * @author s3342128
 */
public abstract class ListMenuBtn extends AbstractBtn {

    public static final int FONT_SIZE = 18;
    public static final int IMG_X = 153;
//    public static final int IMG_Y = 20;
    
    private String title;
    private Object model;
    
    public ListMenuBtn(String t, Object m, Image i) {
        title = t;
        model = m;
        img = i;
        x = 20;
        height = 18;
        
        buttonPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                if(model == null) {
                    return;
                }
                onBtnExit(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(model == null) {
                    return;
                }
                onBtnHover(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(model == null) {
                    return;
                }
SoundUtilities.Click3();                
                onBtnClick(e);
            }
        });
    }    

    protected abstract void onBtnClick(MouseEvent e);
    
    protected abstract void onBtnHover(MouseEvent e);
    
    protected abstract void onBtnExit(MouseEvent e);
    
//    @Override
//    public void drawButton(Graphics g) {
////        Graphics2D g2d = (Graphics2D)(g.create());
////        g2d.scale(1.0, 0.8);
//        
//        super.drawButton(g);
//    }

    @Override
    protected void drawProperties(Graphics g) {
//        Font f = new Font("", Font.PLAIN, FONT_SIZE);
//        Graphics2D g2 = FontUtilities.renderer(g);
        g.setFont(FontUtilities.SEGOE_UI_LIGHT);
        g.setColor(Color.WHITE);
        g.drawString(title, x, y+14);
        
        g.drawImage(img, IMG_X, y-5, 28, 28, null);
    }
    
    @Override
    protected void drawSmallProperties(Graphics g){
        g.setFont(FontUtilities.SEGOE_UI_14);
        g.setColor(Color.WHITE);
        g.drawString(title, x, ySmall+10);
        
        g.drawImage(img, IMG_X, ySmall-5, 28, 28, null);
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object m) {
        this.model = m;
        
        if(model == null) {
            title = "";
            img = null;
        }
        else if(model instanceof Developer) {
            Developer dev = (Developer)model;
            String imgPath = (dev.isFemale()) 
                                        ? ViewUtilities.FEMALE_DRAG_IMG_PATH
                                        : ViewUtilities.MALE_DRAG_IMG_PATH;
            title = dev.getName();
            img = new ImageIcon(imgPath).getImage();
        }
        else if(model instanceof Project) {
            Project proj = (Project)model;
            title = proj.getName();
        }
    }
    
}
