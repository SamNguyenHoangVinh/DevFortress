/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.room.Hardware;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.SoundUtilities;
import view.ViewUtilities;
import view.detailview.button.DtvButton;
import view.menubar.ListMenuBtn;
import view.roomproperties.RoomView;

/**
 *
 * @author Benjamin
 */
public class NewCompDetail extends DetailsPanel implements ViewUtilities {

    public static final int ACCEPT_X = 530;
    
    private static final Image PLUS = new ImageIcon(PLUS_IMG_PATH).getImage();
    private static final Image MINUS = new ImageIcon(MINUS_IMG_PATH).getImage();
    
    private Hardware comp;
    private Image content;
    private DtvButton buyPanel;
    private JPanel plusPanel;
    private JPanel minusPanel;
    private boolean plusHover;
    private boolean minusHover;
    private int quantity = 1;

    public NewCompDetail(ListMenuBtn fromBtn,
            SwingRepaintTimeline repaintTL) {
        super(fromBtn, repaintTL);
        final RoomView r = (RoomView)(MainFrame.getInstance().getCurrPanel());
        comp = (Hardware) (this.fromBtn.getModel());
        super.name = "";
        namePanel = new ImageIcon().getImage();
        
        buyPanel = new DtvButton("Buy", ACCEPT_X) {
            @Override
            protected void onClick() {
                MainFrame.getInstance().toBuyHardwarePopup("Your total payment is " 
                                    + quantity * comp.getPrice()
                                    + " for " + quantity + " " + comp.getName(),
                                    "Do you want to continue?", comp, quantity);
                setHover(false);
            }
        };
        
        plusPanel = new JPanel();
        minusPanel = new JPanel();

        plusPanel.setOpaque(false);
        minusPanel.setOpaque(false);

        ButtonListener listener = new ButtonListener();
        plusPanel.addMouseListener(listener);
        minusPanel.addMouseListener(listener);
        r.add(buyPanel.getPanel());
        r.add(minusPanel);
        r.add(plusPanel);
    }

    @Override
    protected void drawContent(Graphics g) {
        if (comp.getImgIdx() == PX64RX_IDX) {
            content = new ImageIcon(PX64RX_DETAIL_IMG_PATH).getImage();
        } else if (comp.getImgIdx() == IPAC_IDX) {
            content = new ImageIcon(IPAC3G_DETAIL_IMG_PATH).getImage();
        } else if (comp.getImgIdx() == V5LX_IDX) {
            content = new ImageIcon(V5LX_DETAIL_IMG_PATH).getImage();
        } else if (comp.getImgIdx() == TERMINUS2_IDX) {
            content = new ImageIcon(TERMINUSII_DETAIL_IMG_PATH).getImage();
        } else {
            content = new ImageIcon(MONITOR_DETAIL_IMG_PATH).getImage();
        }
        g.drawImage(content, 0, 0, null);
        buyPanel.drawButton(g, x);
        drawPlusButton(g);
        drawQuantityString(g);
        drawMinusButton(g);
    }

    private void drawPlusButton(Graphics g) {
        g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);
        if (plusHover) {
            g.setColor(Color.WHITE);
            g.fillRect(ACCEPT_X - 50, BUTTON_Y, BUTTON_HEIGHT + 1, BUTTON_HEIGHT);
            g.setColor(Color.BLACK);
        }
        plusPanel.setBounds(ACCEPT_X - 50 + x, BUTTON_Y,
                BUTTON_HEIGHT + 1, BUTTON_HEIGHT);
        g.drawRect(ACCEPT_X - 50, BUTTON_Y, BUTTON_HEIGHT + 1, BUTTON_HEIGHT);
        g.drawImage(PLUS, ACCEPT_X - 50 + 5, BUTTON_Y + 5, null);
    }

    private void drawQuantityString(Graphics g) {
        g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);

        g.drawRect(ACCEPT_X - 200 + 50 - BUTTON_HEIGHT + BUTTON_HEIGHT, BUTTON_Y, CANCEL_WIDTH, BUTTON_HEIGHT);
        g.drawString(quantity + "", ACCEPT_X - 200 + 50 - BUTTON_HEIGHT + BUTTON_HEIGHT + 45, BUTTON_Y + 21);
    }

    private void drawMinusButton(Graphics g) {
        g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);
        if (minusHover) {
            g.setColor(Color.WHITE);
            g.fillRect(ACCEPT_X - 200 + 50 - BUTTON_HEIGHT - 1, BUTTON_Y, BUTTON_HEIGHT + 1, BUTTON_HEIGHT);
            g.setColor(Color.BLACK);
        }
        minusPanel.setBounds(ACCEPT_X - 200 + 50 - BUTTON_HEIGHT - 1 + x, BUTTON_Y,
                BUTTON_HEIGHT + 1, BUTTON_HEIGHT);
        g.drawRect(ACCEPT_X - 200 + 50 - BUTTON_HEIGHT - 1, BUTTON_Y, BUTTON_HEIGHT + 1, BUTTON_HEIGHT);
        g.drawImage(MINUS, ACCEPT_X - 200 + 50 - BUTTON_HEIGHT + 5 - 1, BUTTON_Y + 5, null);
    }

    @Override
    public void addBackBtns() {
        JPanel r = MainFrame.getInstance().getCurrPanel();
        r.add(buyPanel.getPanel());
        r.add(cancelBtn.getPanel());
    }

    public Hardware getComp() {
        return comp;
    }

    @Override
    public void setFromBtn(ListMenuBtn fromBtn) {
        super.setFromBtn(fromBtn);
        comp = (Hardware) (this.fromBtn.getModel());
        name = comp.getName();
    }

    private class ButtonListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            JPanel r = MainFrame.getInstance().getCurrPanel();
            SoundUtilities.Click4();
            if (e.getSource() == plusPanel) {
                if (quantity < 100) {
                    quantity++;
                }
            } else if (e.getSource() == minusPanel) {
                if (quantity > 1) {
                    quantity--;
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JPanel r = MainFrame.getInstance().getCurrPanel();
            if (e.getSource() == plusPanel) {
                plusHover = true;
            } else if (e.getSource() == minusPanel) {
                minusHover = true;
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel r = MainFrame.getInstance().getCurrPanel();
            if (e.getSource() == plusPanel) {
                plusHover = false;
            } else if (e.getSource() == minusPanel) {
                minusHover = false;
            }
        }
    }
}