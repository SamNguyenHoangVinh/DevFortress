/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menubar;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.ImageIcon;
import model.Developer;
import model.project.Project;
import model.room.Hardware;
import model.room.WorkingTable;
import view.MainFrame;
import view.MouseState;
import view.SoundUtilities;
import view.ViewUtilities;
import view.detailview.YourCompDetail;
import view.listview.ListComponent;
import view.roomproperties.RoomView;

/**
 *
 * @author s3342128
 */
public class ListMenuBar extends AbstractNormalMenu {
    
    public static final int BASE_BUTTON_Y = 240;
    public static final int MAX_ITEM_NUM = 7;
    public static final int BACKWARD_X = 0;
    public static final int NAV_Y = 515;
    public static final int FORWARD_X = 125;
    
    public static final int MENU = 0;
    public static final int SUBMENU = 1;
    public static final int STORAGE = 2;
    
    public static final int NONE = -1;

    private ArrayList items;
    private ListComponent backward;
    private ListComponent forward;
    private int totalItem;
    private int pageNum;
    private int from;
    private int to;
    private int currentIndex = NONE;
    private int anotherIndex = NONE;
    private int type = MENU;
    
    public ListMenuBar(RoomView r, String menuTitleImgPath) {
        super(r, menuTitleImgPath, LIST_MENU);       
    }
    
    public ListMenuBar(RoomView r, String menuTitleImgPath, Collection c, int type) {
        super(r, menuTitleImgPath, LIST_MENU);
        this.type = type;
        init(c);
        timeLine.addPropertyToInterpolate("alpha", 1.0f, 0.0f);
    }
    
    public ListMenuBar(RoomView r, String menuTitleImgPath, Collection c) {
        super(r, menuTitleImgPath,LIST_MENU);       
        init(c);
        timeLine.addPropertyToInterpolate("alpha", 1.0f, 0.0f);
    }
    
    private void init(Collection c) {
        items = new ArrayList(c);
        buttons = new ArrayList<AbstractBtn>();
        x = -AbstractNormalMenu.MENU_BAR_WIDTH;
        totalItem = items.size();
        pageNum = (totalItem%MAX_ITEM_NUM == 0) 
                                            ? totalItem/MAX_ITEM_NUM 
                                            : totalItem/MAX_ITEM_NUM + 1;
        
        initBackButton(ViewUtilities.BACK_BTN_IMG);
        initButtons();
        
        initBackward();
        initForward();
        
        calculateTo();
    }
    
    private void initBackward() {
        int navX = 0;
        if(type == SUBMENU || type == STORAGE) {
            navX = MENU_BAR_WIDTH;
        }
        backward = new ListComponent(BACKWARD_X, NAV_Y, navX,
            new ImageIcon(ViewUtilities.NAVIGATION_IMG_PATH).getImage(), 
            new ImageIcon(ViewUtilities.LEFT_HOVER_IMG_PATH).getImage(),
            new ImageIcon(ViewUtilities.LEFT_PRESS_IMG_PATH).getImage(), room, true) {

            @Override
            protected void onClick() {                
                setFrom(from - MAX_ITEM_NUM);                
            }                
        };
    }
    
    private void initForward() {
        int navX = 0;
        if(type == SUBMENU || type == STORAGE) {
            navX = MENU_BAR_WIDTH;
        }
        forward = new ListComponent(FORWARD_X, NAV_Y, navX,
            null, 
            new ImageIcon(ViewUtilities.RIGHT_HOVER_IMG_PATH).getImage(),
            new ImageIcon(ViewUtilities.RIGHT_PRESS_IMG_PATH).getImage(), room, true) {

            @Override
            protected void onClick() {
                setFrom(from + MAX_ITEM_NUM);
            }                
        };
    }
    
    @Override
    protected void drawMenuProperties(final Graphics g) {       
        for(AbstractBtn btn : buttons) {
//            final AbstractBtn b = btn;
//            b.getButtonPanel().addMouseListener(new MouseAdapter() {
//
//                @Override
//                public void mousePressed(MouseEvent e) {
//                    for(AbstractBtn b : buttons){
//                        b.setPressed(false, g);
//                    }
//                    b.setPressed(true, g);
//                }
//            
//            });
            btn.drawButton(g);
        }
        
//        backward.drawComponent(g, BACKWARD_X+x, NAV_Y);
//        forward.drawComponent(g, FORWARD_X+x, NAV_Y);
        
        backward.drawLeft(g);
        forward.drawRight(g);
    } 

    @Override
    protected void backBtnClicked() {
        if(menuDisabled) {
            return;
        }
        
//        System.out.println("Back button clicked");
        if(submenu) {
            submenuClosed();
            submenu = false;
        }
        
        timeLine.replay();
        SoundUtilities.Slide();
        afterAnimateReactor.start();
        
        if(MainFrame.getCurrState() == MainFrame.VIEW_DETAIL_STATE) {
            room.closeDetailPanel();
        }

        for (int j = 0; j < buttons.size(); j++) {
            room.remove(buttons.get(j).getButtonPanel());
        }
        room.remove(backBtnPanel);
    }

    @Override
    protected void actAfterAnimation() {
        if(x==-MENU_BAR_WIDTH) {
            room.backToNormalMenu();
            afterAnimateReactor.stop();
        }
    }
    
    @Override
    public void submenuClosed() {
        if(menuDisabled) {
            return;
        }
        
//        timeLine.replayReverse();
        timeLine.replay();
        SoundUtilities.Slide();
        
//        afterSubmenuClosed.start();
//        timeLine.replay();
        
//        if(MainFrame.getCurrState() == MainFrame.VIEW_DETAIL_STATE) {
//            room.closeDetailPanel();
//        }

        room.remove(backward.getPanel());
        room.remove(forward.getPanel());
        for (int j = 0; j < buttons.size(); j++) {
            room.remove(buttons.get(j).getButtonPanel());
        }
        room.remove(backBtnPanel);
    }
    
    @Override
    public void afterSubmenuClosed() {
//        timeLine.replay();
//        timeLine.replayReverse();
        afterSubmenuClosed.stop();
    }
    
    private void initButtons() {
        if(items.isEmpty()) {
            return;
        }
        
        if(items.get(0) instanceof Developer) {            
            for(int i=0; i<MAX_ITEM_NUM && i<items.size(); i++) {
                initDevBtn(i);
            }
        }
        else if(items.get(0) instanceof Project) {
            for(int i=0; i<MAX_ITEM_NUM && i<items.size(); i++) {
                initProjBtn(i);
            }
        }
        else if(items.get(0) instanceof Hardware) {
            for(int i=0; i<MAX_ITEM_NUM && i<items.size(); i++) {
                initCompBtn(i);
            }
        }
        else if(items.get(0) instanceof WorkingTable) {
            for(int i=0; i<MAX_ITEM_NUM && i<items.size(); i++) {
                initTableBtn(i);
            }
            initStorage(items.size());
        }
        
        System.out.println("ITEMS SIZE : " + items.size());
        System.out.println("BUTTONS SIZE : " + buttons.size());
    }
    
    private void initDevBtn(final int i) {
        final Developer dev = (Developer) (items.get(i));
        String imgPath = (dev.isFemale()) ? ViewUtilities.FEMALE_IMG_PATH
                                          : ViewUtilities.MALE_IMG_PATH;
        final ListMenuBtn button = new ListMenuBtn(dev.getName(), dev,
                new ImageIcon(imgPath).getImage()) {
            @Override
            protected void onBtnClick(MouseEvent e) {
                if(menuDisabled) {
                    return;
                }
        
                int mouseX = e.getX();

                if (RoomView.getCurrState() == RoomView.HIRE_DEV_STATE
                        && mouseX > ListMenuBtn.IMG_X
                        && MouseState.getCurrState()
                        != MouseState.DRAG_NEW_DEV) {
                    MouseState.setCurrState(MouseState.DRAG_NEW_DEV);
                    MouseState.setCurrDraggedObj(this);

                    room.setCursor(MouseState
                            .getCustomMouseCursor(getImg()));
                    room.displaysAvaiSeats();

                    if (MainFrame.getCurrState()
                            == MainFrame.VIEW_DETAIL_STATE) {
                        System.out.println("Closing panel");
                        room.closeDetailPanel();
                    }

                    System.out.println("DRAG NEW DEV");
                } else {
                    System.out.println("123456");
                    if (MouseState.getCurrState()
                            != MouseState.DRAG_NEW_DEV) {
                        System.out.println("OPEN 12345678");
                        room.openDevDetail(this);
//                        room.openDetailView(
//                                new DevDetailPanel(button, repaintTimeline));
                    }
                }
            }                    
            @Override
            protected void onBtnHover(MouseEvent e) {
            }
            @Override
            protected void onBtnExit(MouseEvent e) {
            }
        };

        buttons.add(button);
//        buttons.get(i).getButtonPanel()
//                .addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if(menuDisabled) {
//                    return;
//                }
//        
//                int mouseX = e.getX();
//
//                if (RoomView.getCurrState() == RoomView.HIRE_DEV_STATE
//                        && mouseX > ListMenuBtn.IMG_X
//                        && MouseState.getCurrState()
//                        != MouseState.DRAG_NEW_DEV) {
//                    MouseState.setCurrState(MouseState.DRAG_NEW_DEV);
//                    MouseState.setCurrDraggedObj(button);
//
//                    room.setCursor(MouseState
//                            .getCustomMouseCursor(button.getImg()));
//                    room.displaysAvaiSeats();
//
//                    if (MainFrame.getCurrState()
//                            == MainFrame.VIEW_DETAIL_STATE) {
//                        System.out.println("Closing panel");
//                        room.closeDetailPanel();
//                    }
//
//                    System.out.println("DRAG NEW DEV");
//                } else {
//                    System.out.println("123456");
//                    if (MouseState.getCurrState()
//                            != MouseState.DRAG_NEW_DEV) {
//                        System.out.println("OPEN 12345678");
//                        room.openDevDetail(button);
////                        room.openDetailView(
////                                new DevDetailPanel(button, repaintTimeline));
//                    }
//                }
//            }
//        });

        initBtn(i);
    }
    
    private void initProjBtn(final int i) {
        final Project proj = (Project) (items.get(i));
        final ListMenuBtn button = new ListMenuBtn(proj.getName(), proj, null) {
            @Override
            protected void onBtnClick(MouseEvent e) {
                if(menuDisabled) {
                    return;
                }
        
                if (RoomView.getCurrState() == RoomView.AVAI_PROJ_STATE) {
                    room.openNewProjDetail(this);
                } else if (RoomView.getCurrState() == RoomView.YOUR_PROJ_STATE) {
                    room.openYourProjDetail(this);
                }
            }
            @Override
            protected void onBtnHover(MouseEvent e) {
            }
            @Override
            protected void onBtnExit(MouseEvent e) {
            }
        };

        buttons.add(button);
//        buttons.get(i).getButtonPanel()
//                .addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if(menuDisabled) {
//                    return;
//                }
//        
//                if (RoomView.getCurrState() == RoomView.AVAI_PROJ_STATE) {
//                    room.openNewProjDetail(button);
//                } else if (RoomView.getCurrState() == RoomView.YOUR_PROJ_STATE) {
//                    room.openYourProjDetail(button);
//                }                
//            }
//        });

        initBtn(i);
    }
    
    private void initCompBtn(final int i) {
        final Hardware comp = (Hardware) (items.get(i));
        String name;
        
        if (RoomView.getCurrState() == RoomView.NEW_COMP_STATE) {
            name = comp.getName();
        } else {
            name = comp.getSerial();
        }
        
        final ListMenuBtn button = new ListMenuBtn(name, comp, null) {
            @Override
            protected void onBtnClick(MouseEvent e) {
                if (menuDisabled) {
                    return;
                }

                if (RoomView.getCurrState() == RoomView.NEW_COMP_STATE) {
                    room.openNewCompDetail(this);
                } else if (RoomView.getCurrState() == RoomView.YOUR_COMP_STATE && type == STORAGE) {
                    room.openYourCompDetail(this);
                } else if (room.getDetailPanel() instanceof YourCompDetail) {
                    room.openYourCompDetail(this);
                } else if (RoomView.getCurrState() == RoomView.YOUR_COMP_STATE) {
                    currentIndex = i;
                    setChanged();
                    notifyObservers();
                }
            }
            @Override
            protected void onBtnHover(MouseEvent e) {
                if (menuDisabled) {
                    return;
                }
                if (RoomView.getCurrState() == RoomView.YOUR_COMP_STATE) {
                    anotherIndex = i;
                    setChanged();
                    notifyObservers();
                }
            }
            @Override
            protected void onBtnExit(MouseEvent e) {
                if (menuDisabled) {
                    return;
                }
                if (RoomView.getCurrState() == RoomView.YOUR_COMP_STATE) {
                    anotherIndex = NONE;
                    setChanged();
                    notifyObservers();
                }
            }
        };
        
        buttons.add(button);
        
        initBtn(i);
    }
    
    private void initTableBtn(final int i){
        final WorkingTable table = (WorkingTable) (items.get(i));
        final ListMenuBtn button = new ListMenuBtn(table.getName(), table, null) {
            @Override
            protected void onBtnClick(MouseEvent e) {
                if (menuDisabled){
                    return;
                }
                
                if(RoomView.getCurrState() == RoomView.YOUR_COMP_STATE) {
                    room.openYourTableDetail(this);
                }
            }

            @Override
            protected void onBtnHover(MouseEvent e) {
                if (menuDisabled) {
                    return;
                }
                if (RoomView.getCurrState() == RoomView.YOUR_COMP_STATE) {
                    anotherIndex = i;
                    setChanged();
                    notifyObservers();
                }
            }

            @Override
            protected void onBtnExit(MouseEvent e) {
                if (menuDisabled) {
                    return;
                }
                if (RoomView.getCurrState() == RoomView.YOUR_COMP_STATE) {
                    anotherIndex = NONE;
                    setChanged();
                    notifyObservers();
                }
            }
        };
        
        buttons.add(button);
        
        initBtn(i);
    }
    
    private void initStorage(final int i) {
        System.out.println("init storage");
        final ListMenuBtn button = new ListMenuBtn("Storage", "Storage", null) {
            @Override
            protected void onBtnClick(MouseEvent e) {
                if (menuDisabled){
                    return;
                }
                
                if(RoomView.getCurrState() == RoomView.YOUR_COMP_STATE) {
                    room.openYourStorageDetail(this);
                }
            }
            @Override
            protected void onBtnHover(MouseEvent e) {
            }
            @Override
            protected void onBtnExit(MouseEvent e) {
            }
        };
        
        buttons.add(button);
        
        initBtn(i);
    }
    
    private void initBtn(int i) {
//        buttons.get(i).getButtonPanel().addMouseListener(new ButtonMouseListener());
        buttons.get(i).setY(BASE_BUTTON_Y + i * (buttons.get(i).getHeight() + 20));
        room.add(buttons.get(i).getButtonPanel());
    }

    /**
     * Remove one item & its model
     * @param item The item to be removed
     */
    public void removeItem(ListMenuBtn item) {
        items.remove(item.getModel());
        room.remove(item.getButtonPanel());
        buttons.remove(item);
//        room.repaint(item.getButtonPanel().getBounds());
    }
    
    /**
     * Rearrange the items on the menu
     */
    public void rearrangeItems() {
        for(int i=0; i<buttons.size(); i++) {
            buttons.get(i)
                   .setY(BASE_BUTTON_Y + i * (buttons.get(i).getHeight() + 20));
        }
    }
    
    private void calculateTo() {
        to = (from+MAX_ITEM_NUM-1 < totalItem) ? from+MAX_ITEM_NUM-1 
                                    : from + (totalItem-from-1);
        System.out.println("TO : " + to);
        System.out.println("calculateTo ITEMS SIZE : " + items.size());
        System.out.println("calculateTo BUTTONS SIZE : " + buttons.size());
        
        int i;
        int j;        
        
        try {        
            for (i = 0, j = from; j <= to; i++, j++) {
                ((ListMenuBtn) (buttons.get(i))).setModel(items.get(j));
            }

            if((to-from+1) < MAX_ITEM_NUM) {
                for (; i<MAX_ITEM_NUM && i<items.size(); i++) {
                    ((ListMenuBtn) (buttons.get(i))).setModel(null);
                }
            }
        } catch(Exception ex) {
            System.out.println("Exception ITEMS SIZE : " + items.size());
            System.out.println("Exception BUTTONS SIZE : " + buttons.size());
        }
        
//        room.repaint();
    }
    
    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        if(from < 0) {
            System.out.println("From is Zero");
            this.from = 0;
        }
        else if(from >= totalItem) {            
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
    
    public ArrayList getItems() {
        return items;
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }
    
    public int getAnotherIndex() {
        return anotherIndex;
    }
    
    public int getType() {
        return type;
    }

    public void setItems(Collection c) {
        items = new ArrayList(c);
        buttons = new ArrayList<AbstractBtn>();
        x = -AbstractNormalMenu.MENU_BAR_WIDTH;
        totalItem = items.size();
        pageNum = (totalItem%MAX_ITEM_NUM == 0) 
                                            ? totalItem/MAX_ITEM_NUM 
                                            : totalItem/MAX_ITEM_NUM + 1;
        initButtons();
        
        initBackward();
        initForward();
        
        calculateTo();
    }
    
}
