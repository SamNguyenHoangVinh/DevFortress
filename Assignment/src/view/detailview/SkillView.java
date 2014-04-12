/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.detailview;

import java.awt.Graphics;
import model.Developer;
import model.skill.Skill;
import view.MainFrame;
import view.detailview.button.DtvButton;

/**
 *
 * @author Hung
 */
public class SkillView {
    
    public static final String TRAIN_TEXT = "Train";
    public static final String STOP_TRAIN_TEXT = "Stop training";
    public static final int NAME_X = 118;
    public static final int LEVEL_X = 340;
    public static final int BUTTON_X = 450;
    
    private Developer dev;
    private Skill model;
    private DtvButton trainBtn;
    private int y;

    public SkillView(Developer d, Skill m, int y) {
        dev = d;
        model = m;
        this.y = y;
        
        initButton();
    }
    
    public void initButton() {
        String text = (dev!=null && dev.getTrainingSkl()==model) ? 
                                                STOP_TRAIN_TEXT : TRAIN_TEXT;
        final SkillView sv = this;
        
        trainBtn = new DtvButton(text, BUTTON_X, y-15, y+1, 20) {
            @Override
            protected void onClick() {
                if(dev.getTrainingSkl() == model) {
                    stopTraining();
                    return;
                }
                
                MainFrame.getInstance().toTrainingPopup(
                        "Training to level " + (model.getLevel()+1) + 
                        " will take 1 turn and cost " + model.getCost(), 
                        "Are you ready to train?", sv, dev, model);
            }
        };
        MainFrame.getInstance().getCurrPanel().add(trainBtn.getPanel());
    }
    
    public void drawView(Graphics g) {
        if(dev == null) {
            return;
        }
        g.drawString(model.getName(), NAME_X, y);
        g.drawString("Level " + model.getLevel(), LEVEL_X, y);        
    }
    
    public void drawTrainButton(Graphics g, int transX) {
        if(dev == null) {
            return;
        }
        trainBtn.drawButton(g, transX);
    }
    
    public void startTraining() {
        trainBtn.setHover(false);
        trainBtn.setText(STOP_TRAIN_TEXT);
    }
    
    public void stopTraining() {
        dev.setTrainingSkl(null);
        trainBtn.setText(TRAIN_TEXT);
    }

    public Developer getDev() {
        return dev;
    }

    public void setDev(Developer dev) {
        this.dev = dev;
    }

    public Skill getModel() {
        return model;
    }

    public void setModel(Skill model) {
        this.model = model;
    }

    public DtvButton getTrainBtn() {
        return trainBtn;
    }
    
}
