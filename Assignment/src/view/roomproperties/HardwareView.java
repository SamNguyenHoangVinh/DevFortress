/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.roomproperties;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.room.Hardware;
import view.ViewUtilities;

/**
 * This class represents the view of a hardware.
 * @author hung
 */
public class HardwareView implements Observer {
    
    private WorkingTableView table; //The table which this hardware is put on.
    private JPanel panel; //The panel of this hardware;
    private Hardware model;   
    private Image img;
    private Timer workingAnimator;
    
    public HardwareView(Hardware m, WorkingTableView t) {
        model = m;
        img = ViewUtilities.COM_IMG[model.getImgIdx()];
        table = t;
        panel = new JPanel();
        
        panel.setOpaque(false);
        panel.setPreferredSize(
                new Dimension(img.getWidth(null), img.getHeight(null)));     
//        panel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
////                System.out.println("CLICK TO REMOVE");
////                onClick();
//            }
//            
//            @Override
//            public void mouseEntered(MouseEvent e) {
////                System.out.println("MOUSE ENTERED");
//            }            
//
//            @Override
//            public void mouseExited(MouseEvent e) {
////                System.out.println("MOUSE EXITED");
//            }            
//        });
        
        workingAnimator = new Timer(500, new ActionListener() {
            private int i = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(i >= ViewUtilities.COM_ANIM_IMG[model.getImgIdx()].length) {
                    i = 0;
                }
                
                try {
                    img = ViewUtilities.COM_ANIM_IMG[model.getImgIdx()][i];
                } catch(Exception ex) {
                    i = 0;
                    img = ViewUtilities.COM_ANIM_IMG[model.getImgIdx()][i];
                }
                i++;
//                table.getRoom().repaint();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
//        table.getRoom().repaint();
    }
   
    /**
     * Draw this hardware using the current graphic context. If the <code>
     * faceup</code><br/> is true then the front of the hardware is up. <br/>
     * Otherwise, it's down.
     * @param g The current graphic context.
     */
    public void drawHardware(Graphics g) {
        int tx = 0;
        int ty = 0;
        
        if(table != null) {
            tx = table.getX();
            ty = table.getY();
        }
        
        if(model.isFaceUp()) {
            Graphics2D g2d = (Graphics2D)(g.create());
            g2d.rotate(Math.toRadians(180), 
                        tx + model.getX() + img.getWidth(null)/2, 
                        ty + model.getY() + img.getHeight(null)/2);
            g2d.drawImage(img, tx+model.getX(), ty+model.getY(), null);
        }
        else {           
            g.drawImage(img, tx+model.getX(), ty+model.getY(), null);
        }
        
        panel.setBounds(tx+model.getX(), ty+model.getY(), 
                        img.getWidth(null), img.getHeight(null));
    }

//    private void onClick() {
//        if(isMonitor()) {
//            table.removeMonitor(this);
//        }
//        else {
//            table.removeComputer(this);
//        }
//    }
    
    public void startHardwareAnim() {
//        if(isAvailable() || model.getCurrDev().isAvailable()) {
//            return;
//        }
        
        System.out.println("START WORKING");
        workingAnimator.start();
    }
    
    public void stopHardwareAnim() {
        workingAnimator.stop();
        img = ViewUtilities.COM_IMG[model.getImgIdx()];
//        table.getRoom().repaint();
    }
    
    public boolean isMonitor() {
        return model.isMonitor();
    }
    
    public RoomView getRoom() {
        return table.getRoom();
    }

    /**
     * Set (or put) this hardware to a room. If the hardware already has a <br/>
     * room, remove its panel from that room and then add it to the new room.
     * @param r The room to put this hardware.
     */
//    public void setRoom(RoomView r) {
//        if(table.getRoom() != null) {
//            table.getRoom().remove(panel);
//            model.setRoom(null);
//        }
//        table.getRoom() = r;
//        model.setRoom(room.getModel());
//        room.add(panel);
//        
////        room.addMouseMotionListener(new MouseAdapter() {
////            @Override
////            public void mouseMoved(MouseEvent e) {
////                setCoordinates(e.getX() - table.getX(), e.getY() - table.getY());
////                System.out.println(x + " - " + y);
////            }            
////        });
//    }
    
    public void setCoordinates(int hx, int hy) {
//        if(x<0 || x>table.getX()+WorkingTableView.TABLE_WIDTH) {
//            return;
//        }
//        if(y<0 || y>table.getY()+WorkingTableView.TABLE_HEIGHT) {
//            return;
//        }
        model.setCoordinates(hx, hy);
        
//        if(room != null) {
//            room.repaint();
//        }
    } 
    
    public int getWidth() {
        return img.getWidth(null);
    }
    
    public int getHeight() {
        return img.getHeight(null);
    }
    
    public WorkingTableView getTable() {
        return table;
    }

    public void setTable(WorkingTableView t) {
        table = t;
    }

    public JPanel getPanel() {
        return panel;
    }
    
    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public String getName() {
        return model.getName();
    }

    public void setName(String name) {
        model.setName(name);
    }   

    public int getX() {
        return model.getX();
    }

    public void setX(int x) {
//        if(x<0 || x>table.getX()+WorkingTableView.TABLE_WIDTH) {
//            return;
//        }
        
        model.setX(x);        
//        if(table.getRoom() != null) {
//            table.getRoom().repaint();
//        }
    }

    public int getY() {
        return model.getY();
    }

    public void setY(int y) {
//        if(y<0 || y>table.getY()+WorkingTableView.TABLE_HEIGHT) {
//            return;
//        }
        
        model.setY(y);
//        if(table.getRoom() != null) {
//            table.getRoom().repaint();
//        }
    }

    public boolean isFaceUp() {
        return model.isFaceUp();
    }

    public void setFaceUp(boolean faceUp) {
        model.setFaceUp(faceUp);
    }

    public Hardware getModel() {
        return model;
    }

    public void setModel(Hardware model) {
        this.model = model;
        img = ViewUtilities.COM_IMG[model.getImgIdx()];
        panel.setPreferredSize(
                new Dimension(img.getWidth(null), img.getHeight(null)));
//        table.getRoom().repaint();
    }
    
}