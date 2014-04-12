/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.MainFrame;
import view.roomproperties.RoomSetView;

/**
 *
 * @author HungHandsome
 */
public class ToMetroAnimation extends JPanel {

    private Timeline timeline;
    private Timeline timeline2;
    private SwingRepaintTimeline repaintTimeline;
    private Image img;
    private boolean releasing;
    private int startY;
    private float scale = 1.0f;
    private int px;
    private int py;
    
    public ToMetroAnimation() {
        img = ScreenCapturer.captureScreen(MainFrame.getInstance());
//        this.startY = sY;
        
        repaintTimeline = new SwingRepaintTimeline(this);
        repaintTimeline.setAutoRepaintMode(true);
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);
        
        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                int my = e.getY();
//                
//                if(my > 30) {
//                    return;
//                }
//                
//                startY = my;
////                dragging = true;
//            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                if(!dragging) {
//                    return;
//                }
                releasing = true;
                int my = e.getY();
                
                if(my < 400) {
//                    dragging = false;
                    reinitTimeline();
                    timeline.replay();

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(250);
                            } catch (Exception ex) {
                            }
                            releasing = false;
                            MainFrame.getInstance().switchBack();
                        }
                    }.start();
                    return;
                }
                
                replayTimeline2();
            }
        });
        
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int my = e.getY();
//                if(!dragging || my<=startY) {
//                    return;
//                }
                if(my <= startY) {
                    return;
                }
                
                onMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(releasing) {
                    return;
                }
                
                int mx = e.getX();
                int my = e.getY();
                
                if(!(mx>=550 && mx<=600 && my>=0 && my<=50)) {
                    System.out.println("Mouse Exit");
                    MainFrame.getInstance().switchBack();
                }
            }
        });
        
        setPreferredSize(
                new Dimension(MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT));
    }
    
    private void reinitTimeline() {
        timeline = new Timeline(this);
        timeline.addPropertyToInterpolate("scale", scale, 1.0f);
        timeline.addPropertyToInterpolate("px", px, 0);
        timeline.addPropertyToInterpolate("py", py, 0);
        timeline.setDuration(200);
    }
    
    private void replayTimeline2() {
        final MainFrame mf = MainFrame.getInstance();
        timeline2 = new Timeline(this);        
        timeline2.setDuration(300);
        timeline2.addPropertyToInterpolate("py", py, mf.getHeight());
        timeline2.replay();        
        
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(350);
                } catch (Exception ex) {
                }
                releasing = false;
                RoomSetView rsv = new RoomSetView();
                mf.switchPanel(rsv);
//                mf.setCurrPanel(rsv);
                mf.resetTemp();
                
                rsv.playGridAnim();
            }            
        }.start();
    }
    
    private void onMouseDragged(MouseEvent e) {
        int my = e.getY();
        py = my-startY;
        float realHeight = MainFrame.PANEL_HEIGHT - py;        
        scale = realHeight / MainFrame.PANEL_HEIGHT;
        int realWidth = (int)(MainFrame.PANEL_WIDTH * scale);
        px = (MainFrame.PANEL_WIDTH - realWidth) / 2;
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)(g.create());
        g.fillRect(0, 0, MainFrame.getInstance().getWidth(), 
                         MainFrame.getInstance().getHeight());
        
        g.setColor(Color.red);
        g.drawLine(0, 400, MainFrame.getInstance().getWidth(), 400);
        
//        g2d.setColor(Color.blue);
        g2d.translate(px, py);
        g2d.scale(scale, scale);
//        g2d.fillRect(0, 0, MainFrame.PANEL_WIDTH, MainFrame.PANEL_HEIGHT);
        g2d.drawImage(img, 0, 0, null);
    }

    public void reset() {
        scale = 1.0f;
        px = 0;
        py = 0;
        img = ScreenCapturer.captureScreen(MainFrame.getInstance());
    }
    
    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }
    
}