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
import model.room.Hardware;
import model.room.Seat;
import model.room.WorkingTable;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.ViewUtilities;
import view.menubar.AbstractNormalMenu;
import view.menubar.ListMenuBar;
import view.menubar.ListMenuBtn;
import view.roomproperties.RoomView;

/**
 *
 * @author benjamin
 */
public class RoomDetailPanel extends DetailsPanel implements Observer, ViewUtilities {

    private final int NONE = -1;
    private final String MONITOR = "monitor";
    private final int SHADOW = 4;
    private final int LENGTH = 4;
    private int targetIndex = NONE;
    private Hardware comp;
    private WorkingTable[] tables;
    private Image blueprint;
    private Image tableImg;
    private JPanel[] tablePanels;
    private final RoomView r = (RoomView) (MainFrame.getInstance().getCurrPanel());

    public RoomDetailPanel(WorkingTable[] tables, Hardware comp, SwingRepaintTimeline repaintTL) {
        super(null, repaintTL);
        this.tables = tables;
        this.comp = comp;
        init();
    }

    public RoomDetailPanel(WorkingTable[] tables, SwingRepaintTimeline repaintTL) {
        super(null, repaintTL);
        this.tables = tables;
        init();
    }

    private void init() {
        anotherIndex = NONE;
        super.name = "";
        blueprint = new ImageIcon(ROOM_BLUEPRINT_IMG_PATH).getImage();
        tableImg = new ImageIcon(TABLE_BLUEPRINT_IMG_PATH).getImage();
        namePanel = new ImageIcon().getImage();
        tablePanels = new JPanel[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            tablePanels[i] = new JPanel();
            tablePanels[i].setPreferredSize(new Dimension(368, 247));
            final int index = i;
            tablePanels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    targetIndex = index;
                    onClick();
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
            tablePanels[i].setOpaque(false);
            drawTablePanels(index);
        }
    }

    public void onClick() {
        if (comp == null) {
            r.openYourTableDetail(((ListMenuBtn) ((AbstractNormalMenu) r.getMenu()).
                    getButtons().
                    get(((RoomDetailPanel) r.getDetailPanel()).
                    getTargetIndex())));
        } else {
            r.openYourTableDetail(((ListMenuBtn) ((AbstractNormalMenu) r.getMenu()).
                    getButtons().
                    get(((RoomDetailPanel) r.getDetailPanel()).
                    getTargetIndex())), comp);
        }
    }

    @Override
    protected void drawContent(Graphics g) {
        g.drawImage(blueprint, 0, 0, null);
        drawRoom(g);
        if (comp != null) {
            drawGuide(g);
        }
    }

    private void drawGuide(Graphics g) {
        int xG = 260, yG = 250;
        Graphics2D g2d = (Graphics2D) (g.create());
        int rule = AlphaComposite.SRC_OVER;
        Composite composite = AlphaComposite.getInstance(rule, 0.5f);
        g2d.setComposite(composite);
        g2d.fillRoundRect(xG, yG, 270, 50, 10, 10);
        g.setFont(FontUtilities.ROBOTO_THIN_30);
        g.setColor(Color.white);
        g.drawString("Choose a block", xG + 35, yG + 35);
    }

    private void drawRoom(Graphics g) {
        for (int i = 0; i < LENGTH; i++) {
            drawTable(i, g);
        }
    }

    public void addTablePanels() {
        for (int i = 0; i < LENGTH; i++) {
            r.add(tablePanels[i]);
        }
    }

    private void drawTablePanels(int index) {
        int originalX, originalY;
        switch (index) {
            case 0:
                originalX = 183 - 22;
                originalY = 31;
                drawTablePanel(originalX, originalY, index);
                break;
            case 1:
                originalX = 183 - 22 + 382;
                originalY = 31;
                drawTablePanel(originalX, originalY, index);
                break;
            case 2:
                originalX = 183 - 22;
                originalY = 31 + 262;
                drawTablePanel(originalX, originalY, index);
                break;
            case 3:
                originalX = 183 - 22 + 382;
                originalY = 31 + 262;
                drawTablePanel(originalX, originalY, index);
                break;
        }
    }

    private void drawTablePanel(int originalX, int originalY, int index) {
        tablePanels[index].setBounds(originalX - 149 + 197, originalY - 32 + 20, 368, 247);
    }

    private void drawTable(int index, Graphics g) {
        int originalX, originalY;
        switch (index) {
            case 0:
                originalX = x - 22;
                originalY = 31;
                if (anotherIndex == 0) {
                    drawSpecificTable(originalX, originalY, g);
                    drawChosenHardware(originalX, originalY, index, g);
                } else {
                    drawHardware(originalX, originalY, index, g);
                }
                break;
            case 1:
                originalX = x - 22 + 382;
                originalY = 31;
                if (anotherIndex == 1) {
                    drawSpecificTable(originalX, originalY, g);
                    drawChosenHardware(originalX, originalY, index, g);
                } else {
                    drawHardware(originalX, originalY, index, g);
                }
                break;
            case 2:
                originalX = x - 22;
                originalY = 31 + 262;
                if (anotherIndex == 2) {
                    drawSpecificTable(originalX, originalY, g);
                    drawChosenHardware(originalX, originalY, index, g);
                } else {
                    drawHardware(originalX, originalY, index, g);
                }
                break;
            case 3:
                originalX = x - 22 + 382;
                originalY = 31 + 262;
                if (anotherIndex == 3) {
                    drawSpecificTable(originalX, originalY, g);
                    drawChosenHardware(originalX, originalY, index, g);
                } else {
                    drawHardware(originalX, originalY, index, g);
                }
                break;
        }
    }

    private void drawSpecificTable(int originalX, int originalY, Graphics g) {
        g.drawImage(tableImg, originalX - 149, originalY - 32, null);
    }

    private void drawStringName(int originalX, int originalY,
            int index, String name, Graphics g) {
        switch (index) {
            case 0:
                g.setFont(FontUtilities.SEGOE_UI_14);
                g.drawString(name, originalX - 83, originalY + 17);
                break;
            case 1:
                g.setFont(FontUtilities.SEGOE_UI_14);
                g.drawString(name, originalX + 92, originalY + 17);
                break;
            case 2:
                g.setFont(FontUtilities.SEGOE_UI_14);
                g.drawString(name, originalX - 83, originalY + 217);
                break;
            case 3:
                g.setFont(FontUtilities.SEGOE_UI_14);
                g.drawString(name, originalX + 92, originalY + 217);
                break;
        }
    }

    private void drawHardware(int originalX, int originalY,
            int index, Graphics g) {
        ArrayList<Hardware> comps = tables[index].getComputers();
        Seat[] seats = tables[index].getSeats();
        for (int i = 0; i < LENGTH; i++) {
            if (!seats[i].isAvailable()) {
                drawStringName(originalX, originalY, i, seats[i].getCurrDev().getName(), g);
            }
            for (int j = 0; j < comps.size(); j++) {
                if (comps.get(j).getPosition() == i) {
                    drawBlueprint(originalX, originalY, i, comps.get(j), g);
                }
            }
        }
    }

    private void drawChosenHardware(int originalX, int originalY,
            int index, Graphics g) {
        ArrayList<Hardware> comps = tables[index].getComputers();
        Seat[] seats = tables[index].getSeats();
        for (int i = 0; i < LENGTH; i++) {
            if (!seats[i].isAvailable()) {
                drawStringName(originalX, originalY, i, seats[i].getCurrDev().getName(), g);
            }
            for (int j = 0; j < comps.size(); j++) {
                if (comps.get(j).getPosition() == i) {
                    drawChosenBlueprint(originalX, originalY, i, comps.get(j), g);
                }
            }
        }
    }

    private void drawBlueprint(int originalX, int originalY,
            int index, Hardware comp, Graphics g) {
        Graphics2D g2d = (Graphics2D) (g.create());
//        int rule = AlphaComposite.SRC_OVER;
//        Composite composite = AlphaComposite.getInstance(rule, alpha);
//        g2d.setComposite(composite);
        Image print;
        switch (index) {
            case 0:
                if (comp.isEquipped()) {
                    print = getBlueprint(MONITOR);
                    g2d.drawImage(print, originalX, originalY, null);
                }
                print = getBlueprint(comp.getName());
                g2d.rotate(Math.toRadians(180),
                        originalX - 128 + print.getWidth(null) / 2,
                        originalY + 56 + print.getHeight(null) / 2);
                g2d.drawImage(print, originalX - 128, originalY + 56, null);
                break;
            case 1:
                if (comp.isEquipped()) {
                    print = getBlueprint(MONITOR);
                    g2d.drawImage(print, originalX + 177, originalY, null);
                }
                print = getBlueprint(comp.getName());
                g2d.rotate(Math.toRadians(180),
                        originalX + 47 + print.getWidth(null) / 2,
                        originalY + 56 + print.getHeight(null) / 2);
                g2d.drawImage(print, originalX + 47, originalY + 56, null);
                break;
            case 2:
                if (comp.isEquipped()) {
                    print = getBlueprint(MONITOR);
                    g2d.drawImage(print, originalX, originalY + 122, null);
                }
                print = getBlueprint(comp.getName());
                g2d.drawImage(print, originalX - 128, originalY + 117, null);
                break;
            case 3:
                if (comp.isEquipped()) {
                    print = getBlueprint(MONITOR);
                    g2d.drawImage(print, originalX + 177, originalY + 122, null);
                }
                print = getBlueprint(comp.getName());
                g2d.drawImage(print, originalX + 47, originalY + 117, null);
                break;
        }
    }

    private void drawChosenBlueprint(int originalX, int originalY,
            int index, Hardware comp, Graphics g) {
        int newX = originalX - SHADOW;
        int newY = originalY - SHADOW;

        Graphics2D g2d = (Graphics2D) (g.create());
//        int rule = AlphaComposite.SRC_OVER;
//        Composite composite = AlphaComposite.getInstance(rule, alpha);
//        g2d.setComposite(composite);
        Image print;
        switch (index) {
            case 0:
                if (comp.isEquipped()) {
                    print = getChosenBlueprint(MONITOR);
                    g2d.drawImage(print, newX, newY, null);
                }
                print = getChosenBlueprint(comp.getName());
                g2d.rotate(Math.toRadians(180),
                        newX - 128 + print.getWidth(null) / 2,
                        newY + 56 + print.getHeight(null) / 2);
                g2d.drawImage(print, newX - 128, newY + 56, null);
                break;
            case 1:
                if (comp.isEquipped()) {
                    print = getChosenBlueprint(MONITOR);
                    g2d.drawImage(print, newX + 177, newY, null);
                }
                print = getChosenBlueprint(comp.getName());
                g2d.rotate(Math.toRadians(180),
                        newX + 47 + print.getWidth(null) / 2,
                        newY + 56 + print.getHeight(null) / 2);
                g2d.drawImage(print, newX + 47, newY + 56, null);
                break;
            case 2:
                if (comp.isEquipped()) {
                    print = getChosenBlueprint(MONITOR);
                    g2d.drawImage(print, newX, newY + 122, null);
                }
                print = getChosenBlueprint(comp.getName());
                g2d.drawImage(print, newX - 128, newY + 117, null);
                break;
            case 3:
                if (comp.isEquipped()) {
                    print = getChosenBlueprint(MONITOR);
                    g2d.drawImage(print, newX + 177, newY + 122, null);
                }
                print = getChosenBlueprint(comp.getName());
                g2d.drawImage(print, newX + 47, newY + 117, null);
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
        for (int i = 0; i < LENGTH; i++) {
            r.remove(tablePanels[i]);
        }
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    @Override
    public void addBackBtns() {
    }

    @Override
    public void setFromBtn(ListMenuBtn fromBtn) {
        anotherIndex = NONE;
    }

    @Override
    public void update(Observable o, Object arg) {
        anotherIndex = ((ListMenuBar) o).getAnotherIndex();
    }
}
