/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JComponent;

/**
 *
 * @author Benjamin
 */
public class FontUtilities extends FontMetrics {
    
    // Font Paths
    public static final String PATH = "Fonts/";
    public static final String SEGOE_UI_LIGHT_PATH = PATH + "segoeuil.ttf";
    public static final String SEGOE_UI_ITALIC_PATH = PATH + "segoeuii.ttf";
    public static final String SEGOE_UI_PATH = PATH + "segoeui.ttf";
    public static final String SEGOE_UI_BOLD_PATH = PATH + "segoeuib.ttf";
    public static final String SEGOE_UI_SEMILIGHT_PATH = PATH + "segoeuisl.ttf";
    public static final String SEGOE_UI_SEMIBOLD_PATH = PATH + "seguisb.ttf";
    public static final String ROBOTO_THIN_PATH = PATH + "Roboto-Thin.ttf";
    
    // Fonts
    public static final float FONT_SIZE_STANDARD = 18;
    public static final float FONT_SIZE_22 = 22;
    public static final float FONT_SIZE_17 = 17;
    public static final float FONT_SIZE_12 = 12;
    public static final float FONT_SIZE_14 = 14;
    public static final float FONT_SIZE_110 = 110;
    public static final float FONT_SIZE_48 = 48;
    public static final float FONT_SIZE_30 = 30;
    public static final float FONT_SIZE_24 = 24;
    
    public static final Font SEGOE_UI_LIGHT = 
            FontUtilities.loadFont(SEGOE_UI_LIGHT_PATH, FONT_SIZE_STANDARD);
    public static final Font SEGOE_UI_ITALIC = 
            FontUtilities.loadFont(SEGOE_UI_ITALIC_PATH, FONT_SIZE_STANDARD);
    public static final Font SEGOE_UI = 
            FontUtilities.loadFont(SEGOE_UI_PATH, FONT_SIZE_STANDARD);
    public static final Font SEGOE_UI_BOLD = 
            FontUtilities.loadFont(SEGOE_UI_BOLD_PATH, FONT_SIZE_STANDARD);
    public static final Font SEGOE_UI_SEMILIGHT = 
            FontUtilities.loadFont(SEGOE_UI_SEMILIGHT_PATH, FONT_SIZE_STANDARD);
    public static final Font SEGOE_UI_SEMIBOLD = 
            FontUtilities.loadFont(SEGOE_UI_SEMIBOLD_PATH, FONT_SIZE_STANDARD);
    public static final Font ROBOTO_THIN = 
            FontUtilities.loadFont(ROBOTO_THIN_PATH, FONT_SIZE_STANDARD);
    
    public static final Font SEGOE_UI_17 = 
            FontUtilities.loadFont(SEGOE_UI_PATH, FONT_SIZE_17);
    public static final Font SEGOE_UI_BOLD_17 = 
            FontUtilities.loadFont(SEGOE_UI_BOLD_PATH, FONT_SIZE_17);
    public static final Font SEGOE_UI_12 = 
            FontUtilities.loadFont(SEGOE_UI_PATH, FONT_SIZE_12);
    public static final Font SEGOE_UI_BOLD_12 = 
            FontUtilities.loadFont(SEGOE_UI_BOLD_PATH, FONT_SIZE_12);
    public static final Font SEGOE_UI_SEMIBOLD_22 = 
            FontUtilities.loadFont(SEGOE_UI_SEMIBOLD_PATH, FONT_SIZE_22);
    public static final Font SEGOE_UI_SEMIBOLD_17 = 
            FontUtilities.loadFont(SEGOE_UI_SEMIBOLD_PATH, FONT_SIZE_17);
    public static final Font SEGOE_UI_14 = 
            FontUtilities.loadFont(SEGOE_UI_PATH, FONT_SIZE_14);
    public static final Font SEGOE_UI_BOLD_14 = 
            FontUtilities.loadFont(SEGOE_UI_BOLD_PATH, FONT_SIZE_14);
    public static final Font ROBOTO_THIN_12 =
            FontUtilities.loadFont(ROBOTO_THIN_PATH, FONT_SIZE_12);
    public static final Font ROBOTO_THIN_30 =
            FontUtilities.loadFont(ROBOTO_THIN_PATH, FONT_SIZE_30);
    public static final Font ROBOTO_THIN_48 =
            FontUtilities.loadFont(ROBOTO_THIN_PATH, FONT_SIZE_48);
    public static final Font ROBOTO_THIN_110 =
            FontUtilities.loadFont(ROBOTO_THIN_PATH, FONT_SIZE_110);
    public static final Font SEGOE_UI_LIGHT_24 =
            FontUtilities.loadFont(SEGOE_UI_LIGHT_PATH, FONT_SIZE_24);
    public static final Font SEGOE_UI_BOLD_24 = 
            FontUtilities.loadFont(SEGOE_UI_BOLD_PATH, FONT_SIZE_24);

    private FontUtilities() {
        super(null);
    }
    
    public static Font loadFont(String fontFileName, float size) {
        Font ttfBase = null;
        Font ttfReal = null;
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream(fontFileName));
            ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            ttfReal = ttfBase.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(fontFileName + " not loaded.");
        }
        return ttfReal;
    }
    
    public static Graphics2D renderer(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        return g2;
    }
    
    public static int stringWidth(String str, Font f, JComponent component) {
        Graphics g = component.getGraphics().create();
        g.setFont(FontUtilities.SEGOE_UI_SEMIBOLD_17);
        return g.getFontMetrics().stringWidth(str);
    }
}
