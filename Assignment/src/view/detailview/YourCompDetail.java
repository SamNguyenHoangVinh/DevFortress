/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import view.detailview.button.DtvButton;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import model.player.Player;
import model.room.Hardware;
import org.pushingpixels.trident.swing.SwingRepaintTimeline;
import view.FontUtilities;
import view.MainFrame;
import view.SoundUtilities;
import view.ViewUtilities;
import view.menubar.ListMenuBar;
import view.menubar.ListMenuBtn;
import view.popup.InformPopup;
import view.popup.YesNoPopup;
import view.roomproperties.RoomView;

/**
 *
 * @author Benjamin
 */
public class YourCompDetail extends DetailsPanel implements ViewUtilities {

    public static final int SELL_X = 530;
    public static final int MAIN_X = SELL_X - 120;
    private Hardware comp;
    private Image content;
    private DtvButton sellBtn;
    private DtvButton mainBtn;
    private final RoomView r = (RoomView) (MainFrame.getInstance().getCurrPanel());

    public YourCompDetail(ListMenuBtn fromBtn, ListMenuBtn parentFromBtn, ListMenuBtn ancestorFromBtn, SwingRepaintTimeline repaintTL) {
        super(fromBtn, parentFromBtn, ancestorFromBtn, repaintTL);
        init();
    }

    public YourCompDetail(ListMenuBtn fromBtn, ListMenuBtn parentFromBtn, SwingRepaintTimeline repaintTL) {
        super(fromBtn, parentFromBtn, repaintTL);
        init();
    }

    public YourCompDetail(ListMenuBtn fromBtn, SwingRepaintTimeline repaintTL) {
        super(fromBtn, repaintTL);
        init();
    }

    private void init() {
        if (fromBtn == null) {
            comp = null;
            super.name = "S15";
        } else {
            comp = (Hardware) (this.fromBtn.getModel());
            super.name = comp.getSerial();
        }
        namePanel = new ImageIcon().getImage();

        if (fromBtn == null) {
            mainBtn = new DtvButton("Apply", SELL_X) {
                @Override
                protected void onClick() {
                    setHover(false);
                    final Hardware comp = (Hardware) parentFromBtn.getModel();
                    YesNoPopup popup = new YesNoPopup("The cost for this upgrade is $1000!",
                            "Do you want to continue?",
                            YesNoPopup.BUY_HARDWARE_MSG_X) {
                        @Override
                        public void onYesClicked() {
                            MainFrame.getInstance().switchBack();
                            Player.getInstance().upgradeComputer(comp);
                            InformPopup ok = new InformPopup("Upgrade complete!",
                                    YesNoPopup.BUY_HARDWARE_MSG_X) {
                                @Override
                                public void onClick() {
                            SoundUtilities.Coin1();
                                    r.openSubDetailView(new YourCompDetail(parentFromBtn, ancestorFromBtn, repaintTimeline));
                                }
                            };
                            MainFrame.getInstance().switchPanel(ok);
                        }
                    };
                    MainFrame.getInstance().switchPanel(popup);
                }
            };
            r.add(mainBtn.getPanel());
        } else if (parentFromBtn != null) {
            if (!comp.isEquipped()) {
                mainBtn = new DtvButton("Upgrade", MAIN_X) {
                    @Override
                    protected void onClick() {
                        setHover(false);
                        r.openSubDetailView(new YourCompDetail(null, fromBtn, parentFromBtn, repaintTimeline));
                    }
                };
                r.add(mainBtn.getPanel());
            } else {
                mainBtn = new DtvButton("Upgraded", MAIN_X) {
                    @Override
                    protected void onClick() {
                    }
                };
            }
            if (((ListMenuBar) r.getSubmenu()).getType() == ListMenuBar.STORAGE) {
                sellBtn = new DtvButton("Sell", SELL_X) {
                    @Override
                    protected void onClick() {
                        setHover(false);
                        MainFrame.getInstance().toSellHardwarePopup("You are going to sell "
                                + comp.getSerial() + " and get $" + comp.getPrice() / 2 + " refund!",
                                "Do you want to continue?", comp, getInstance());
                    }
                };
                r.add(sellBtn.getPanel());
            } else {
                sellBtn = new DtvButton("Sell", SELL_X) {
                    @Override
                    protected void onClick() {
                        setHover(false);
                        MainFrame.getInstance().toSellHardwarePopup("You are going to sell "
                                + comp.getSerial() + " and get $" + comp.getPrice() / 2 + " refund!",
                                "Do you want to continue?", comp, getInstance());
                    }
                };
                r.add(sellBtn.getPanel());
            }
        } else {
            mainBtn = new DtvButton("Assign", MAIN_X) {
                @Override
                protected void onClick() {
                    setHover(false);
                    ((ListMenuBar) (r.getSubmenu())).submenuClosed();
                    r.openRoomDetail(comp);
                }
            };
            sellBtn = new DtvButton("Sell", SELL_X) {
                @Override
                protected void onClick() {
                    setHover(false);
                    MainFrame.getInstance().toSellHardwarePopup("You are going to sell "
                            + comp.getSerial() + " and receive $" + comp.getPrice() / 2 + " refund!",
                            "Do you want to continue?", comp, getInstance());
                }
            };
            r.add(sellBtn.getPanel());
            r.add(mainBtn.getPanel());
        }
    }

    @Override
    protected void drawContent(Graphics g) {
        if (comp == null) {
            content = new ImageIcon(MONITOR_BLUEPRINT_IMG_PATH).getImage();
        } else if (comp.getImgIdx() == PX64RX_IDX) {
            content = new ImageIcon(PX64RX_BLUEPRINT_IMG_PATH).getImage();
        } else if (comp.getImgIdx() == IPAC_IDX) {
            content = new ImageIcon(IPAC3G_BLUEPRINT_IMG_PATH).getImage();
        } else if (comp.getImgIdx() == V5LX_IDX) {
            content = new ImageIcon(V5LX_BLUEPRINT_IMG_PATH).getImage();
        } else if (comp.getImgIdx() == TERMINUS2_IDX) {
            content = new ImageIcon(TERMINUSII_BLUEPRINT_IMG_PATH).getImage();
        }
        g.drawImage(content, 0, 0, null);

        g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_22);
        g.drawString(name, 213, 53);

        if (fromBtn != null) {
            sellBtn.drawButton(g, x);
        }
        mainBtn.drawButton(g, x);
    }

    @Override
    public void addBackBtns() {
        MainFrame.getInstance().getCurrPanel().add(cancelBtn.getPanel());
    }

    public Hardware getComp() {
        return comp;
    }

    public void removeButtons() {
        if (sellBtn != null) {
            r.remove(sellBtn.getPanel());
        }
        r.remove(mainBtn.getPanel());
        r.remove(cancelBtn.getPanel());
    }

    @Override
    public void setFromBtn(final ListMenuBtn fromBtn) {
        super.setFromBtn(fromBtn);
        comp = (Hardware) (this.fromBtn.getModel());
        name = comp.getSerial();
        if (parentFromBtn != null) {
            r.remove(mainBtn.getPanel());
            if (!comp.isEquipped()) {
                mainBtn = new DtvButton("Upgrade", MAIN_X) {
                    @Override
                    protected void onClick() {
                        setHover(false);
                        r.openSubDetailView(new YourCompDetail(null, fromBtn, parentFromBtn, repaintTimeline));
                    }
                };
                r.add(mainBtn.getPanel());
            } else {
                mainBtn = new DtvButton("Upgraded", MAIN_X) {
                    @Override
                    protected void onClick() {
                    }
                };
            }
        }
    }
}
