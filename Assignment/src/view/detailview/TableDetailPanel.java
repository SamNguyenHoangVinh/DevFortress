/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.player.Player;
import model.room.Hardware;
import model.room.Seat;
import model.room.WorkingTable;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.ViewUtilities;
import view.detailview.button.DtvButton;
import view.menubar.AbstractNormalMenu;
import view.menubar.ListMenuBar;
import view.menubar.ListMenuBtn;
import view.popup.InformPopup;
import view.popup.YesNoPopup;
import view.roomproperties.RoomView;

/**
 *
 * @author benjamin
 */
public class TableDetailPanel extends DetailsPanel implements Observer, ViewUtilities {

    public static final int ACCEPT_X = 530;
    private final int NONE = -1;
    private final String MONITOR = "monitor";
    private final int SHADOW = 4;
    private final int LENGTH = 4;
    private Hardware comp;
    private WorkingTable table;
    private ArrayList<Hardware> comps;
    private Seat[] seats;
    private Image blueprint;
    private DtvButton detail;
    private DtvButton remove;
    private JPanel[] compPanels;
    private final RoomView r = (RoomView) (MainFrame.getInstance().getCurrPanel());

    public TableDetailPanel(ListMenuBtn fromBtn, Hardware comp, SwingRepaintTimeline repaintTL) {
        super(fromBtn, repaintTL);
        this.comp = comp;
        init();
    }

    public TableDetailPanel(ListMenuBtn fromBtn, SwingRepaintTimeline repaintTL) {
        super(fromBtn, repaintTL);
        init();
    }

    private void init() {
        table = (WorkingTable) (this.fromBtn.getModel());
        comps = table.getComputers();
        seats = table.getSeats();
        currentIndex = NONE;
        anotherIndex = NONE;
        super.name = "";
        namePanel = new ImageIcon().getImage();
        blueprint = new ImageIcon(BLUEPRINT_IMG_PATH).getImage();

        if (comp == null) {
            detail = new DtvButton("Details", ACCEPT_X - 120) {
                @Override
                protected void onClick() {
                    setHover(false);
                    getTablePanel().onClick();
                }
            };
            remove = new DtvButton("Remove", ACCEPT_X) {
                @Override
                protected void onClick() {
                    setHover(false);
                    YesNoPopup popup = new YesNoPopup("Product "
                            + comps.get(getIndex(currentIndex)).getName() + " ID "
                            + comps.get(getIndex(currentIndex)).getSerial()
                            + " will be removed from this block",
                            "Do you want to continue?",
                            YesNoPopup.BUY_HARDWARE_MSG_X) {
                        @Override
                        public void onYesClicked() {
                            MainFrame.getInstance().switchBack();
                            int index = getIndex(currentIndex);
                            Hardware comp = comps.get(index);
                            comp.getTable().removeComputer(comp);
                            Player.getInstance().getComputers().put(comp.getSerial(), comp);
                            InformPopup ok = new InformPopup("Product "
                                    + comp.getName() + " ID " + comp.getSerial()
                                    + " removed!",
                                    YesNoPopup.BUY_HARDWARE_MSG_X, index);
                            currentIndex = NONE;
                            MainFrame.getInstance().switchPanel(ok);
                        }
                    };
                    MainFrame.getInstance().switchPanel(popup);
                }
            };
        }

        compPanels = new JPanel[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            compPanels[i] = new JPanel();
            compPanels[i].setPreferredSize(new Dimension(145, 55));
            final int index = i;
            if (comp == null) {
                if (getIndex(i) != NONE) {
                    compPanels[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            currentIndex = index;
                            r.add(remove.getPanel());
                            r.add(detail.getPanel());
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            anotherIndex = index;
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            anotherIndex = NONE;
                        }
                    });
                }
            } else {
                if (getIndex(i) == NONE) {
                    compPanels[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            currentIndex = index;
                            YesNoPopup popup = new YesNoPopup("Product "
                                    + comp.getName()
                                    + " ID " + comp.getSerial()
                                    + " will be added to the position "
                                    + (currentIndex + 1) + " of this block!",
                                    "Do you want to continue?",
                                    YesNoPopup.BUY_HARDWARE_MSG_X) {
                                @Override
                                public void onYesClicked() {
                                    MainFrame.getInstance().switchBack();
                                    table.putComputer(comp, currentIndex);
                                    Player.getInstance().getComputers().remove(comp.getSerial());
                                    InformPopup ok = new InformPopup("Product "
                                            + comp.getName()
                                            + " ID " + comp.getSerial() + " added!",
                                            YesNoPopup.BUY_HARDWARE_MSG_X) {
                                        @Override
                                        public void onClick() {
                                            r.openYourTableDetail(fromBtn, null);
                                        }
                                    };
                                    MainFrame.getInstance().switchPanel(ok);
                                }
                            };
                            MainFrame.getInstance().switchPanel(popup);
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            anotherIndex = index;
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            anotherIndex = NONE;
                        }
                    });
                }
            }
            compPanels[i].setOpaque(false);
            drawPanels(index);
        }
    }

    public void onClick() {
        r.openYourCompDetail(((ListMenuBtn) ((AbstractNormalMenu) r.getSubmenu())
                .getButtons()
                .get(getIndex(((TableDetailPanel) r.getDetailPanel())
                .getCurrentIndex()))), fromBtn);
    }

    private int getIndex(int position) {
        for (int i = 0; i < comps.size(); i++) {
            if (comps.get(i).getPosition() == position) {
                return i;
            }
        }
        return NONE;
    }

    private int getPosition(int index) {
        if (index > NONE && index < comps.size()) {
            return comps.get(index).getPosition();
        }
        return NONE;
    }

    public void addPanels() {
        if (comp == null) {
            for (int i = 0; i < LENGTH; i++) {
                if (getIndex(i) != NONE) {
                    System.out.println("i " + i);
                    r.add(compPanels[i]);
                }
            }
        } else {
            for (int i = 0; i < LENGTH; i++) {
                if (getIndex(i) == NONE) {
                    r.add(compPanels[i]);
                }
            }
        }
    }

    private void drawPanels(int index) {
        switch (index) {
            case 0:
                drawPanel(310 - SHADOW + 197, 209 - SHADOW, index);
                break;
            case 1:
                drawPanel(485 - SHADOW + 197, 209 - SHADOW, index);
                break;
            case 2:
                drawPanel(310 - SHADOW + 197, 270 - SHADOW, index);
                break;
            case 3:
                drawPanel(485 - SHADOW + 197, 270 - SHADOW, index);
                break;
        }
    }

    private void drawPanel(int originalX, int originalY, int index) {
        compPanels[index].setBounds(originalX, originalY, 145, 55);
    }

    @Override
    protected void drawContent(Graphics g) {
        g.drawImage(blueprint, 0, 0, null);
        if (currentIndex != NONE && comp == null) {
            remove.drawButton(g, x);
            detail.drawButton(g, x);
        }
        drawDeveloper(g);
        drawHardware(g);
        if (comp != null) {
            drawGuide(g);
        }
    }

    private void drawGuide(Graphics g) {
        int xG = 330, yG = 0;
        Graphics2D g2d = (Graphics2D) (g.create());
        int rule = AlphaComposite.SRC_OVER;
        Composite composite = AlphaComposite.getInstance(rule, 0.5f);
        g2d.setComposite(composite);
        g2d.fillRoundRect(xG, yG, 300, 50, 10, 10);
        g.setFont(FontUtilities.ROBOTO_THIN_30);
        g.setColor(Color.white);
        g.drawString("Choose a position", xG + 33, yG + 35);
        g.setColor(Color.black);
    }

    private void drawDeveloper(Graphics g) {
        for (int i = 0; i < seats.length; i++) {
            if (!seats[i].isAvailable()) {
                drawStringName(g, i, seats[i].getCurrDev().getName());
            }
        }
    }

    private void drawStringName(Graphics g, int index, String name) {
        switch (index) {
            case 0:
                g.setFont(FontUtilities.SEGOE_UI_14);
                g.drawString(name, 355, 170);
                break;
            case 1:
                g.setFont(FontUtilities.SEGOE_UI_14);
                g.drawString(name, 530, 170);
                break;
            case 2:
                g.setFont(FontUtilities.SEGOE_UI_14);
                g.drawString(name, 355, 370);
                break;
            case 3:
                g.setFont(FontUtilities.SEGOE_UI_14);
                g.drawString(name, 530, 370);
                break;
        }
    }

    private void drawHardware(Graphics g) {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < comps.size(); j++) {
                if (comps.get(j).getPosition() == i) {
                    drawBlueprint(g, i, j);
                    break;
                }
            }
            if (comp != null) {
                if (getIndex(i) == NONE) {
                    drawBlueprint(g, i, NONE);
                    if (currentIndex != NONE && i == currentIndex) {
                        drawChosenBlueprint(g, currentIndex, NONE);
                    }
                    if (anotherIndex != NONE && i == anotherIndex) {
                        drawChosenBlueprint(g, anotherIndex, NONE);
                    }
                }
            }
        }
        for (int i = 0; i < comps.size(); i++) {
            if (currentIndex != NONE && comps.get(i).getPosition() == currentIndex) {
                drawChosenBlueprint(g, currentIndex, i);
            }
            if (anotherIndex != NONE && comps.get(i).getPosition() == anotherIndex) {
                drawChosenBlueprint(g, anotherIndex, i);
            }
        }
    }

    private void drawBlueprint(Graphics g, int position, int index) {
        Graphics2D g2d = (Graphics2D) (g.create());
//        int rule = AlphaComposite.SRC_OVER;
//        Composite composite = AlphaComposite.getInstance(rule, alpha);
//        g2d.setComposite(composite);
        Hardware computer;
        if (index == NONE) {
            computer = comp;
            int rule = AlphaComposite.SRC_OVER;
            Composite composite = AlphaComposite.getInstance(rule, 0.1f);
            g2d.setComposite(composite);
        } else {
            computer = comps.get(index);
        }
        Image print;
        switch (position) {
            case 0:
                if (computer.isEquipped()) {
                    print = getBlueprint(MONITOR);
                    g2d.drawImage(print, 438, 153, null);
                }
                print = getBlueprint(computer.getName());
                g2d.rotate(Math.toRadians(180),
                        310 + print.getWidth(null) / 2,
                        209 + print.getHeight(null) / 2);
                g2d.drawImage(print, 310, 209, null);
                break;
            case 1:
                if (computer.isEquipped()) {
                    print = getBlueprint(MONITOR);
                    g2d.drawImage(print, 615, 153, null);
                }
                print = getBlueprint(computer.getName());
                g2d.rotate(Math.toRadians(180),
                        485 + print.getWidth(null) / 2,
                        209 + print.getHeight(null) / 2);
                g2d.drawImage(print, 485, 209, null);
                break;
            case 2:
                if (computer.isEquipped()) {
                    print = getBlueprint(MONITOR);
                    g2d.drawImage(print, 438, 275, null);
                }
                print = getBlueprint(computer.getName());
                g2d.drawImage(print, 310, 270, null);
                break;
            case 3:
                if (computer.isEquipped()) {
                    print = getBlueprint(MONITOR);
                    g2d.drawImage(print, 615, 275, null);
                }
                print = getBlueprint(computer.getName());
                g2d.drawImage(print, 485, 270, null);
                break;
        }
    }

    private void drawChosenBlueprint(Graphics g, int position, int index) {
        Graphics2D g2d = (Graphics2D) (g.create());
//        int rule = AlphaComposite.SRC_OVER;
//        Composite composite = AlphaComposite.getInstance(rule, alpha);
//        g2d.setComposite(composite);
        Hardware computer;
        if (index == NONE) {
            computer = comp;
            int rule = AlphaComposite.SRC_OVER;
            Composite composite = AlphaComposite.getInstance(rule, 0.5f);
            g2d.setComposite(composite);
        } else {
            computer = comps.get(index);
        }
        Image print;
        switch (position) {
            case 0:
                if (computer.isEquipped()) {
                    print = getChosenBlueprint(MONITOR);
                    g2d.drawImage(print, 438 - SHADOW, 153 - SHADOW, null);
                }
                print = getChosenBlueprint(computer.getName());
                g2d.rotate(Math.toRadians(180),
                        310 - SHADOW + print.getWidth(null) / 2,
                        209 - SHADOW + print.getHeight(null) / 2);
                g2d.drawImage(print, 310 - SHADOW, 209 - SHADOW, null);
                break;
            case 1:
                if (computer.isEquipped()) {
                    print = getChosenBlueprint(MONITOR);
                    g2d.drawImage(print, 615 - SHADOW, 153 - SHADOW, null);
                }
                print = getChosenBlueprint(computer.getName());
                g2d.rotate(Math.toRadians(180),
                        485 - SHADOW + print.getWidth(null) / 2,
                        209 - SHADOW + print.getHeight(null) / 2);
                g2d.drawImage(print, 485 - SHADOW, 209 - SHADOW, null);
                break;
            case 2:
                if (computer.isEquipped()) {
                    print = getChosenBlueprint(MONITOR);
                    g2d.drawImage(print, 438 - SHADOW, 275 - SHADOW, null);
                }
                print = getChosenBlueprint(computer.getName());
                g2d.drawImage(print, 310 - SHADOW, 270 - SHADOW, null);
                break;
            case 3:
                if (computer.isEquipped()) {
                    print = getChosenBlueprint(MONITOR);
                    g2d.drawImage(print, 615 - SHADOW, 275 - SHADOW, null);
                }
                print = getChosenBlueprint(computer.getName());
                g2d.drawImage(print, 485 - SHADOW, 270 - SHADOW, null);
                break;
        }
    }

    private Image getBlueprint(String name) {
        switch (name.charAt(0)) {
            case 'P':
                return new ImageIcon(P_BLUEPRINT_IMG_PATH).getImage();
            case 'i':
                return new ImageIcon(I_BLUEPRINT_IMG_PATH).getImage();
            case 'V':
                return new ImageIcon(V_BLUEPRINT_IMG_PATH).getImage();
            case 'T':
                return new ImageIcon(T_BLUEPRINT_IMG_PATH).getImage();
            default:
                return new ImageIcon(S_BLUEPRINT_IMG_PATH).getImage();
        }
    }

    private Image getChosenBlueprint(String name) {
        switch (name.charAt(0)) {
            case 'P':
                return new ImageIcon(P_BLUEPRINT_S_IMG_PATH).getImage();
            case 'i':
                return new ImageIcon(I_BLUEPRINT_S_IMG_PATH).getImage();
            case 'V':
                return new ImageIcon(V_BLUEPRINT_S_IMG_PATH).getImage();
            case 'T':
                return new ImageIcon(T_BLUEPRINT_S_IMG_PATH).getImage();
            default:
                return new ImageIcon(S_BLUEPRINT_S_IMG_PATH).getImage();
        }
    }

    public void removeButtons() {
        if (comp == null) {
            r.remove(detail.getPanel());
            r.remove(remove.getPanel());
        }
        r.remove(cancelBtn.getPanel());
        for (int i = 0; i < LENGTH; i++) {
            r.remove(compPanels[i]);
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public TableDetailPanel getTablePanel() {
        return this;
    }
    
    public Hardware getComputer() {
        return comp;
    }

    @Override
    public void addBackBtns() {
        r.add(cancelBtn.getPanel());
    }

    @Override
    public void setFromBtn(ListMenuBtn fromBtn) {
        super.setFromBtn(fromBtn);
        table = (WorkingTable) (this.fromBtn.getModel());
        comps = table.getComputers();
        name = table.getName();
        seats = table.getSeats();
        currentIndex = NONE;
    }

    @Override
    public void update(Observable o, Object arg) {
        currentIndex = getPosition(((ListMenuBar) o).getCurrentIndex());
        anotherIndex = getPosition(((ListMenuBar) o).getAnotherIndex());
        if (comp == null) {
            if (currentIndex != NONE) {
                r.add(remove.getPanel());
                r.add(detail.getPanel());
            } else {
                r.remove(remove.getPanel());
                r.remove(detail.getPanel());
            }
        }
    }
}
