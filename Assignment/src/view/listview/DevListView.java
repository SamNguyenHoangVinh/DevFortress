/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.listview;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import model.Developer;
import model.area.Area;
import view.FontUtilities;
import view.ViewUtilities;
import view.detailview.YourProjDetail;
import view.menubar.AbstractNormalMenu;
import view.roomproperties.RoomView;

/**
 *
 * @author s3342128
 */
public abstract class DevListView {
    
    public static final int HEADER_SIZE = 20;
    public static final int CONTENT_SIZE = 15;
    public static final int X = 200;
    public static final int Y = 70;
    public static final int MARGIN_X = X + 20;
    public static final int MARGIN_Y = Y + 35;
    public static final int BASE_CONTENT_Y = MARGIN_Y + 45;
    public static final int WIDTH = 334;
    public static final int HEIGHT = 323;
    public static final int MAX_ITEM_NUM = 6;
    public static final int BACK_X = X - 58;
    public static final int NAV_Y = Y + (HEIGHT - 33) / 2;
    public static final int NEXT_X = X + WIDTH;
    public static final int OK_Y = Y + HEIGHT - 46;
    
    protected ArrayList<Developer> selectedItems;
    protected YourProjDetail currDetailPanel;
    protected Area area;
    
    private RoomView room;    
    private ArrayList<Developer> avaiDevs;
    private ListComponent back;
    private ListComponent next;
    private ListComponent ok;
    private ListComponent contents[];    
//    private int mx; //The x of this menu.
//    private int my; //The x of this menu.
    private int totalAvaiDev;
    private int pageNum;
    private int from;
    private int to;

    public DevListView(RoomView room, YourProjDetail currDetailPanel, Area a, 
                                           ArrayList<Developer> availableDevs) {
        this.room = room;
        this.currDetailPanel = currDetailPanel;
        area = a;
        selectedItems = new ArrayList<Developer>(MAX_ITEM_NUM);
        avaiDevs = availableDevs;
        totalAvaiDev = avaiDevs.size();
        pageNum = (totalAvaiDev%MAX_ITEM_NUM == 0) 
                  ? totalAvaiDev/MAX_ITEM_NUM : totalAvaiDev/MAX_ITEM_NUM + 1;
        
//        System.out.println("Construct DevListView");
        this.room.getMenu().setMenuDisabled(true);
        this.currDetailPanel.setBtnsDisabled(true);
        
        initBack();
        initNext();
        initOk();
        initContents();
        
        calculateTo();
        
    }

    private void initBack() {
        back = new ListComponent(BACK_X, NAV_Y,  AbstractNormalMenu.MENU_BAR_WIDTH,
//        back = new ListComponent(BACK_X, NAV_Y,
            new ImageIcon(ViewUtilities.BACK_SMALL_IMG_PATH).getImage(), 
            new ImageIcon(ViewUtilities.BACK_SMALL_HOVER_IMG_PATH).getImage(), room) {

            @Override
            protected void onClick() {
                for(int i=0; i<MAX_ITEM_NUM; i++) {
                    contents[i].setHover(false);
                }
                selectedItems.clear();
                setFrom(from - MAX_ITEM_NUM);
            }                
        };
    }
    
    private void initNext() {
        next = new ListComponent(NEXT_X, NAV_Y, AbstractNormalMenu.MENU_BAR_WIDTH,
//        next = new ListComponent(NEXT_X, NAV_Y,
            new ImageIcon(ViewUtilities.NEXT_IMG_PATH).getImage(), 
            new ImageIcon(ViewUtilities.NEXT_HOVER_IMG_PATH).getImage(), room) {

            @Override
            protected void onClick() {
                for(int i=0; i<MAX_ITEM_NUM; i++) {
                    contents[i].setHover(false);
                }
                selectedItems.clear();
                setFrom(from + MAX_ITEM_NUM);
            }                
        };
    }
    
    private void initOk() {
        Image okImg = new ImageIcon(ViewUtilities.OK_IMG_PATH).getImage();
//        ok = new ListComponent(X, HEIGHT-okImg.getHeight(null), 
//                transX, okImg, okImg, room) {
        ok = new ListComponent(X, OK_Y, 
                        AbstractNormalMenu.MENU_BAR_WIDTH, okImg, okImg, room) {                
            @Override
            protected void onClick() {
//                if(!selectedItems.isEmpty()) {
//                    Project proj = currDetailPanel.getProj();
//                    
////                    Player.getInstance().acceptProject(proj);
//                    Player.getInstance().assignDeveloper(selectedItems, proj);
//                    
//                    for(Developer d : selectedItems) {
//                        proj.getAreas().get(area.getName()).addDeveloper(d);
//                    }                    
//                }
//                
//                removeComponents();
//                currDetailPanel.backToNormalState();
                room.getMenu().setMenuDisabled(false);
                currDetailPanel.setBtnsDisabled(false);
                okClicked();
            }                
        };
    }
    
    public abstract void okClicked();
    
    private void initContents() {
        contents = new ListComponent[MAX_ITEM_NUM];
        
        for(int i=0, cy=BASE_CONTENT_Y-CONTENT_SIZE-7; i<MAX_ITEM_NUM; i++) {
            final int idx = i;
            contents[i] = new ListComponent(X+2, cy, 
                    AbstractNormalMenu.MENU_BAR_WIDTH,
                    new ImageIcon(ViewUtilities.SELECTED_IMG_PATH)
                                                        .getImage(), room) {
                @Override
                protected void onClick() {
                    if(idx >= totalAvaiDev) {
                        return;
                    }
                    System.out.println(idx);
                    contents[idx].setHover(!contents[idx].isHover());
                    
                    if(contents[idx].isHover()) {
                        selectedItems.add(avaiDevs.get(from+idx));
                    }
                    else {
                        selectedItems.remove(avaiDevs.get(from+idx));
                    }
                    
//                    room.repaint();
                }
            };
//            contents[i].getPanel().setOpaque(true);
            cy += 33;
        }
    }
    
    public void drawList(Graphics g) {
        back.drawComponent(g);
        next.drawComponent(g);
        
        g.drawImage(
                new ImageIcon(ViewUtilities.POPUP_LAYOUT_IMG_PATH).getImage(), 
                X, Y, room);
        
        ok.drawComponent(g);
        
//        g.setFont(new Font("", Font.PLAIN, CONTENT_SIZE));
        g.setFont(FontUtilities.SEGOE_UI_LIGHT);
        for(int i=from, strY=BASE_CONTENT_Y; i<=to; i++) {
            Developer d = avaiDevs.get(i);
            g.drawString(d.getName(), MARGIN_X, strY);
            strY += 33;
            
            contents[i-from].drawComponent(g);
        }
    }
    
    private void calculateTo() {
        to = (from+MAX_ITEM_NUM-1 < totalAvaiDev) ? from+MAX_ITEM_NUM-1 
                                    : from + (totalAvaiDev-from-1);
        System.out.println("TO : " + to);
//        room.repaint();
    }
    
    protected void removeComponents() {
        room.remove(back.getPanel());
        room.remove(next.getPanel());
        room.remove(ok.getPanel());
        
        for(ListComponent c : contents) {
            room.remove(c.getPanel());
        }
    }
    
    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        if(from < 0) {
            System.out.println("From is Zero");
            this.from = 0;
        }
        else if(from >= totalAvaiDev) {            
            this.from = (pageNum - 1) * MAX_ITEM_NUM;
            System.out.println("FROM 1: " + from + " - FROM 2: " + this.from);
        }
        else {
            this.from = from;
            System.out.println("FROM : " + from);
        }        
        
        calculateTo();        
    }

    public int getTo() {
        return to;
    }
    
}
