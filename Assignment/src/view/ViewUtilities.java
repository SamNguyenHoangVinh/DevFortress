/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Benjamin
 */
public interface ViewUtilities {
//    // Font Paths
//    public static final String PATH = "Fonts/";
//    public static final String SEGOE_UI_LIGHT_PATH = PATH + "segoeuil.ttf";
//    public static final String SEGOE_UI_ITALIC_PATH = PATH + "segoeuii.ttf";
//    public static final String SEGOE_UI_PATH = PATH + "segoeui.ttf";
//    public static final String SEGOE_UI_BOLD_PATH = PATH + "segoeuib.ttf";
//    public static final String SEGOE_UI_SEMILIGHT_PATH = PATH + "segoeuisl.ttf";
//    public static final String SEGOE_UI_SEMIBOLD_PATH = PATH + "seguisb.ttf";
//    public static final String ROBOTO_THIN_PATH = PATH + "Roboto-Thin.ttf";
//    
//    // Fonts
//    public static final float FONT_SIZE_STANDARD = 18;
//    public static final float FONT_SIZE_22 = 22;
//    public static final float FONT_SIZE_17 = 17;
//    public static final float FONT_SIZE_12 = 12;
//    public static final float FONT_SIZE_14 = 14;
//    
//    public static final Font SEGOE_UI_LIGHT = FontUtilities.loadFont(SEGOE_UI_LIGHT_PATH, FONT_SIZE_STANDARD);
//    public static final Font SEGOE_UI_ITALIC = FontUtilities.loadFont(SEGOE_UI_ITALIC_PATH, FONT_SIZE_STANDARD);
//    public static final Font SEGOE_UI = FontUtilities.loadFont(SEGOE_UI_PATH, FONT_SIZE_STANDARD);
//    public static final Font SEGOE_UI_BOLD = FontUtilities.loadFont(SEGOE_UI_BOLD_PATH, FONT_SIZE_STANDARD);
//    public static final Font SEGOE_UI_SEMILIGHT = FontUtilities.loadFont(SEGOE_UI_SEMILIGHT_PATH, FONT_SIZE_STANDARD);
//    public static final Font SEGOE_UI_SEMIBOLD = FontUtilities.loadFont(SEGOE_UI_SEMIBOLD_PATH, FONT_SIZE_STANDARD);
//    public static final Font ROBOTO_THIN = FontUtilities.loadFont(ROBOTO_THIN_PATH, FONT_SIZE_STANDARD);
//    
//    public static final Font SEGOE_UI_17 = FontUtilities.loadFont(SEGOE_UI_PATH, FONT_SIZE_17);
//    public static final Font SEGOE_UI_BOLD_17 = FontUtilities.loadFont(SEGOE_UI_BOLD_PATH, FONT_SIZE_17);
//    public static final Font SEGOE_UI_12 = FontUtilities.loadFont(SEGOE_UI_PATH, FONT_SIZE_12);
//    public static final Font SEGOE_UI_BOLD_12 = FontUtilities.loadFont(SEGOE_UI_BOLD_PATH, FONT_SIZE_12);
//    public static final Font SEGOE_UI_SEMIBOLD_22 = FontUtilities.loadFont(SEGOE_UI_SEMIBOLD_PATH, FONT_SIZE_22);
//    public static final Font SEGOE_UI_SEMIBOLD_17 = FontUtilities.loadFont(SEGOE_UI_SEMIBOLD_PATH, FONT_SIZE_17);
//    public static final Font SEGOE_UI_14 = FontUtilities.loadFont(SEGOE_UI_PATH, FONT_SIZE_14);
//    public static final Font SEGOE_UI_BOLD_14 = FontUtilities.loadFont(SEGOE_UI_BOLD_PATH, FONT_SIZE_14);
    
    // Paths
    public static final String IMG_PATH = "Images/";
    public static final String MAIN_MENU_PATH = IMG_PATH + "MainMenuImages/";
    public static final String INTER_OBJ_PATH = IMG_PATH + "InteractiveObjects/";
    public static final String MENU_BAR_PATH = IMG_PATH + "MenuBarImage/";
    public static final String MENU_TITLE_PATH = IMG_PATH + "MenuTitles/";
    public static final String DEV_DETAIL_PATH = IMG_PATH + "DevDetails/";
    public static final String DEV_LIST_MENU_PATH = IMG_PATH + "DevListMenu/";
    public static final String PROJ_DETAIL_PATH = IMG_PATH + "ProjDetails/";
    public static final String POPUP_PATH = IMG_PATH + "Popup/";
    public static final String COMP_DETAIL_PATH = IMG_PATH + "CompDetails/";
    public static final String TABLE_DETAIL_PATH = IMG_PATH + "TableDetails/";
    public static final String REPORT_PATH = IMG_PATH + "Report/";
    public static final String DEV_ANIM_PATH = IMG_PATH + "DevAnim/";
    public static final String F_EMOTICON_PATH = IMG_PATH + "Emoticons/female/";
    public static final String M_EMOTICON_PATH = IMG_PATH + "Emoticons/male/";
    public static final String HIGHSCORE_PATH = IMG_PATH + "HighScore/";
    public static final String NAVIGATION_PATH = IMG_PATH + "Navigation/";
    public static final String INTRO_PATH = IMG_PATH + "Intro/";
    public static final String METRO_PATH = IMG_PATH + "Metro/";
    public static final String SMALL_MENU_PATH = IMG_PATH + "SmallMenu/";
    
    // Intro source images
    public static final String CYBERTECH_IMG_PATH = INTRO_PATH + "CyberTech.png";
    public static final String ENTERTAIN_IMG_PATH = INTRO_PATH + "Entertainment.png";
    public static final String PROUDLY_IMG_PATH = INTRO_PATH + "Proudly.png";
    public static final String PRESENT_IMG_PATH = INTRO_PATH + "Present.png";
    public static final String DEVELOP_IMG_PATH = INTRO_PATH + "Dev.png";
    public static final String SEPERATE_IMG_PATH = INTRO_PATH + "L.png";
    public static final String FORTRESS_IMG_PATH = INTRO_PATH + "Fortress.png";
    public static final String LAYOUT_IMG_PATH = INTRO_PATH + "Layout.png";
    
    // Main menu source images
    public static final String START_GAME_BTN = MAIN_MENU_PATH + "StartGame.png";
    public static final String OPTIONS_BTN = MAIN_MENU_PATH + "Options.png";
    public static final String EXTRA_BTN = MAIN_MENU_PATH + "Extra.png";
    public static final String ABOUT_BTN = MAIN_MENU_PATH + "About.png";
    public static final String EXIT_BTN = MAIN_MENU_PATH + "Exit.png";
    
    public static final String NEW_GAME_BTN = MAIN_MENU_PATH + "Start New Game.png";
    public static final String CONTINUE_BTN = MAIN_MENU_PATH + "Continue.png";
    public static final String LOAD1_BTN = MAIN_MENU_PATH + "Save Game 1.png";
    public static final String LOAD2_BTN = MAIN_MENU_PATH + "Save Game 2.png";
    public static final String LOAD3_BTN = MAIN_MENU_PATH + "Save Game 3.png";
    public static final String BACK_MENU_BTN = MAIN_MENU_PATH + "Back.png";
    public static final String BGM1 = MAIN_MENU_PATH + "BM1.png";
    public static final String BGM2 = MAIN_MENU_PATH + "BM2.png";
    public static final String BGM3 = MAIN_MENU_PATH + "BM3.png";
    public static final String BGM4 = MAIN_MENU_PATH + "BM4.png";
    public static final String BGM5 = MAIN_MENU_PATH + "BM5.png";
    public static final String ABOUT_DES = MAIN_MENU_PATH + "Description.png";
    
    public static final String DEV_LOGO = MAIN_MENU_PATH + "Dev.png";
    public static final String SEPERATOR_LOGO = MAIN_MENU_PATH + "l.png";
    public static final String FORTRESS_LOGO = MAIN_MENU_PATH + "Fortress.png";
    
    public static final String BACKGROUND_IMG = MAIN_MENU_PATH + "mainmenu.png";
    
    // Interactive Object images
    public static final String TABLE_IMG_PATH = INTER_OBJ_PATH + "Table.png";
    // Hardware image path
    public static final String IPAC_IMG_PATH = INTER_OBJ_PATH + "iPac.png";
    public static final String PX64RX_IMG_PATH = INTER_OBJ_PATH + "PX64RX.png";
    public static final String MONITOR_IMG_PATH = INTER_OBJ_PATH + "Monitor.png";
    public static final String TERMINUS2_IMG_PATH = INTER_OBJ_PATH + "Terminus2.png";
    public static final String V5LX_IMG_PATH = INTER_OBJ_PATH + "V5LX.png";
    
    public static final String DEV_IMG_PATH = INTER_OBJ_PATH + "developer.png";
    public static final String DISK_IMG_PATH = INTER_OBJ_PATH + "Disk.png";
    public static final String FLOOR_IMG_PATH = INTER_OBJ_PATH + "Floor.png";
    
    // Menu source image paths
    public static final String MENU_TITLE_IMG_PATH = MENU_BAR_PATH + "Menu title.png";
    
    public static final String PROJ_IMG_PATH = MENU_BAR_PATH + "Project.png";
    public static final String NEW_PROJ_IMG_PATH = MENU_BAR_PATH + "New Projects.png";
    public static final String YOUR_PROJ_IMG_PATH = MENU_BAR_PATH + "Your Projects.png";
    
    public static final String DEVELOPER_IMG_PATH = MENU_BAR_PATH + "Developer.png";
    public static final String HIRE_DEV_IMG_PATH = MENU_BAR_PATH + "Hire Developers.png";
    public static final String YOUR_DEV_IMG_PATH = MENU_BAR_PATH + "Your Developers.png";
    
    public static final String HARDWARE_IMG_PATH = MENU_BAR_PATH + "Hardware.png";
    public static final String NEW_COMP_IMG_PATH = MENU_BAR_PATH + "New Computer.png";
    public static final String YOUR_COMP_IMG_PATH = MENU_BAR_PATH + "Your Computers.png";
    
//    public static final String FOOD_IMG_PATH = MENU_BAR_PATH + "Food.png";
//    public static final String BUY_FOOD_IMG_PATH = MENU_BAR_PATH + "Buy Food.png";
    public static final String STORAGE_IMG_PATH = MENU_BAR_PATH + "Storage.png";
    
    public static final String MENU_BAR_IMG_PATH = MENU_BAR_PATH + "menubar.png";
    public static final String SUB_MENU_IMG_PATH = MENU_BAR_PATH + "submenu.png";
    public static final String PLAY_IMG_PATH = MENU_BAR_PATH + "Play.png";
    public static final String PLAY_HOVER_IMG_PATH = MENU_BAR_PATH + "PlayHover.png";
    public static final String PLAY_PRESS_IMG_PATH = MENU_BAR_PATH + "PlayPressed.png";
    
    public static final String GAMEPLAYMENU_IMG_PATH = MENU_BAR_PATH + "GamePlayMenu.png";
    public static final String MESSAGE_IMG_PATH = MENU_BAR_PATH + "Message.png";
    public static final String MESSAGE2_IMG_PATH = MENU_BAR_PATH + "Message2.png";
    
    // Turn Summary image paths
    public static final String REPORT_BG_IMG_PATH = REPORT_PATH + "ReportLayout.png";
    
    // DevDetails image paths
    public static final String DETAIL_PANEL_IMG_PATH = DEV_DETAIL_PATH + "DetailPanel.png";
    public static final String NAME_PANEL_IMG_PATH = DEV_DETAIL_PATH + "NamePanel.png";
    
    // DevListMenu image paths
//    public static final String MALE_IMG_PATH = DEV_LIST_MENU_PATH + "mmenu.gif";
//    public static final String FEMALE_IMG_PATH = DEV_LIST_MENU_PATH + "fmenu.gif";
//    public static final String MALE_DRAG_IMG_PATH = DEV_LIST_MENU_PATH + "mmenu.gif";
//    public static final String FEMALE_DRAG_IMG_PATH = DEV_LIST_MENU_PATH + "fmenu.gif";
    public static final String MALE_IMG_PATH = DEV_LIST_MENU_PATH + "male.png";
    public static final String FEMALE_IMG_PATH = DEV_LIST_MENU_PATH + "female.png";
    public static final String MALE_DRAG_IMG_PATH = DEV_LIST_MENU_PATH + "male2.png";
    public static final String FEMALE_DRAG_IMG_PATH = DEV_LIST_MENU_PATH + "female2.png";
    
    // ProjDetails image paths
    public static final String NAME_PANEL_PATH = PROJ_DETAIL_PATH + "NamePanel.png";
    public static final String PLUS_IMG_PATH = PROJ_DETAIL_PATH + "Plus.png";
    public static final String MINUS_IMG_PATH = PROJ_DETAIL_PATH + "Minus.png";
    
    // Menu title image paths
    public static final String BACK_IMG_PATH = MENU_TITLE_PATH + "Back.png";
    public static final String BACK_HOVER_IMG_PATH = MENU_TITLE_PATH + "BackHover.png";
    public static final String BACK_PRESS_IMG_PATH = MENU_TITLE_PATH + "BackPressed.png";
    
    public static final String NEW_PROJ_TITLE_IMG_PATH = MENU_TITLE_PATH + "New Projects.png";
    public static final String YOUR_PROJ_TITLE_IMG_PATH = MENU_TITLE_PATH + "Your Projects.png";
    
    public static final String HIRE_DEV_TITLE_IMG_PATH = MENU_TITLE_PATH + "New Developers.png";
    public static final String YOUR_DEV_TITLE_IMG_PATH = MENU_TITLE_PATH + "Your Developers.png";
    
    public static final String NEW_COMP_TITLE_IMG_PATH = MENU_TITLE_PATH + "New Computers.png";
    public static final String YOUR_COMP_TITLE_IMG_PATH = MENU_TITLE_PATH + "Your Computers.png";
    
    // Popup image paths
    public static final String BACK_SMALL_IMG_PATH = POPUP_PATH + "PopBack.png";
    public static final String BACK_SMALL_HOVER_IMG_PATH = POPUP_PATH + "PopBackHover.png";
    public static final String NEXT_IMG_PATH = POPUP_PATH + "PopNext.png";
    public static final String NEXT_HOVER_IMG_PATH = POPUP_PATH + "PopNextHover.png";
    public static final String OK_IMG_PATH = POPUP_PATH + "PopOKHover.png";
    public static final String SELECTED_IMG_PATH = POPUP_PATH + "PopSelected.png";
    public static final String POPUP_LAYOUT_IMG_PATH = POPUP_PATH + "PopLayout.png";
    public static final String POPUP_BG_IMG_PATH = POPUP_PATH + "Popup.png";
    
    // Navigation image paths
    public static final String NAVIGATION_IMG_PATH = NAVIGATION_PATH + "Navigation.png";
    public static final String LEFT_HOVER_IMG_PATH = NAVIGATION_PATH + "LeftHover.png";
    public static final String LEFT_PRESS_IMG_PATH = NAVIGATION_PATH + "LeftPressed.png";
    public static final String RIGHT_HOVER_IMG_PATH = NAVIGATION_PATH + "RightHover.png";
    public static final String RIGHT_PRESS_IMG_PATH = NAVIGATION_PATH + "RightPressed.png";
    
    // Computer Details image paths
    public static final String PX64RX_DETAIL_IMG_PATH = COMP_DETAIL_PATH + "PX64RX.png";
    public static final String IPAC3G_DETAIL_IMG_PATH = COMP_DETAIL_PATH + "iPac3G.png";
    public static final String V5LX_DETAIL_IMG_PATH = COMP_DETAIL_PATH + "V5LX.png";
    public static final String TERMINUSII_DETAIL_IMG_PATH = COMP_DETAIL_PATH + "TerminusII.png";
    public static final String MONITOR_DETAIL_IMG_PATH = COMP_DETAIL_PATH + "S15.png";
    public static final String PX64RX_BLUEPRINT_IMG_PATH = COMP_DETAIL_PATH + "PX64RXB.png";
    public static final String IPAC3G_BLUEPRINT_IMG_PATH = COMP_DETAIL_PATH + "iPac3GB.png";
    public static final String V5LX_BLUEPRINT_IMG_PATH = COMP_DETAIL_PATH + "V5LXB.png";
    public static final String TERMINUSII_BLUEPRINT_IMG_PATH = COMP_DETAIL_PATH + "TerminusIIB.png";
    public static final String MONITOR_BLUEPRINT_IMG_PATH = COMP_DETAIL_PATH + "S15B.png";
    
    // Table Details image paths
    public static final String BLUEPRINT_IMG_PATH = TABLE_DETAIL_PATH + "Blueprint.png";
    public static final String P_BLUEPRINT_IMG_PATH = TABLE_DETAIL_PATH + "Pblueprint.png";
    public static final String I_BLUEPRINT_IMG_PATH = TABLE_DETAIL_PATH + "Iblueprint.png";
    public static final String V_BLUEPRINT_IMG_PATH = TABLE_DETAIL_PATH + "Vblueprint.png";
    public static final String T_BLUEPRINT_IMG_PATH = TABLE_DETAIL_PATH + "Tblueprint.png";
    public static final String S_BLUEPRINT_IMG_PATH = TABLE_DETAIL_PATH + "Sblueprint.png";
    public static final String P_BLUEPRINT_S_IMG_PATH = TABLE_DETAIL_PATH + "PblueprintS.png";
    public static final String I_BLUEPRINT_S_IMG_PATH = TABLE_DETAIL_PATH + "IblueprintS.png";
    public static final String V_BLUEPRINT_S_IMG_PATH = TABLE_DETAIL_PATH + "VblueprintS.png";
    public static final String T_BLUEPRINT_S_IMG_PATH = TABLE_DETAIL_PATH + "TblueprintS.png";
    public static final String S_BLUEPRINT_S_IMG_PATH = TABLE_DETAIL_PATH + "SblueprintS.png";
    public static final String ROOM_BLUEPRINT_IMG_PATH = TABLE_DETAIL_PATH + "Hardware.png";
    public static final String TABLE_BLUEPRINT_IMG_PATH = TABLE_DETAIL_PATH + "Table.png";
    
    // High score image paths
    public static final String HIGHSCORE_PANEL_PATH = HIGHSCORE_PATH + "HighscoreLayout.png";
    
    // Hardware animation path
    public static final String COM_ANIM_PATH = IMG_PATH + "ComAnim/";
    
    // Image of a room view
    public static final Image FLOOR_IMG = 
                         new ImageIcon(ViewUtilities.FLOOR_IMG_PATH).getImage();
    // Image of a table view
    public static final Image TABLE_IMG = 
                         new ImageIcon(TABLE_IMG_PATH).getImage();
    
    // Developer animation images
//    public static final Image DEV_IMG = new ImageIcon(DEV_IMG_PATH).getImage();
//    public static final Image DEV_ANIM_IMG[] = {
//        new ImageIcon(DEV_ANIM_PATH + "D0.png").getImage(),
//        new ImageIcon(DEV_ANIM_PATH + "D1.png").getImage(),
//        new ImageIcon(DEV_ANIM_PATH + "D2.png").getImage()
//    };
    public static final Image DISH = new ImageIcon(DISK_IMG_PATH).getImage();
    
    // Menu images
    public static final Image MENU_BAR_IMG = new ImageIcon(MENU_BAR_IMG_PATH)
                                                .getImage();
    public static final Image SUB_MENU_IMG = new ImageIcon(SUB_MENU_IMG_PATH)
                                                .getImage();
    public static final Image GAMEPLAYMENU_IMG = new ImageIcon(GAMEPLAYMENU_IMG_PATH)
                                                .getImage();
    public static final Image PLAY_BTN_IMG = new ImageIcon(PLAY_IMG_PATH)
                                                .getImage();
    public static final Image PLAY_HOVER_BTN_IMG = new ImageIcon(PLAY_HOVER_IMG_PATH)
                                                .getImage();
    public static final Image PLAY_PRESS_BTN_IMG = new ImageIcon(PLAY_PRESS_IMG_PATH)
                                                .getImage();
    public static final Image BACK_BTN_IMG = new ImageIcon(BACK_IMG_PATH)
                                                .getImage();
    public static final Image BACK_HOVER_BTN_IMG = new ImageIcon(BACK_HOVER_IMG_PATH)
                                                .getImage();
    public static final Image BACK_PRESS_BTN_IMG = new ImageIcon(BACK_PRESS_IMG_PATH)
                                                .getImage();
    
    // Dialog image
    public static final Image DIALOG_IMG = new ImageIcon(POPUP_BG_IMG_PATH)
                                                .getImage();
    
    // Hardware animation images
    public static final int IPAC_IDX = 0;
    public static final int PX64RX_IDX = 1;    
    public static final int TERMINUS2_IDX = 2;
    public static final int V5LX_IDX = 3;
    public static final int MONITOR_IDX = 4;
    
    public static final Image COM_IMG[] = {
        new ImageIcon(IPAC_IMG_PATH).getImage(),
        new ImageIcon(PX64RX_IMG_PATH).getImage(),
        new ImageIcon(TERMINUS2_IMG_PATH).getImage(),
        new ImageIcon(V5LX_IMG_PATH).getImage(),
        new ImageIcon(MONITOR_IMG_PATH).getImage()
    };
    
    public static final Image COM_ANIM_IMG[][] = {
        new Image[] { // IPAC_IMG_PATH
            new ImageIcon(COM_ANIM_PATH + "I0.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "I1.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "I2.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "I3.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "I4.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "I5.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "I6.png").getImage()
        },
        new Image[] { // PX64RX_IMG_PATH
            new ImageIcon(COM_ANIM_PATH + "P0.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "P1.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "P2.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "P3.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "P4.png").getImage()
        },
        new Image[] { // TERMINUS2_IMG_PATH
            new ImageIcon(COM_ANIM_PATH + "T0.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T1.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T2.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T3.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T4.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T5.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T6.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T7.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T8.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T9.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "T10.png").getImage()
        },
        new Image[] { // V5LX_IMG_PATH
            new ImageIcon(COM_ANIM_PATH + "V0.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "V1.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "V2.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "V3.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "V4.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "V5.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "V6.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "V7.png").getImage()
        },
        new Image[] { // MONITOR_IMG_PATH
            new ImageIcon(COM_ANIM_PATH + "M0.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "M1.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "M2.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "M3.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "M4.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "M5.png").getImage(),
            new ImageIcon(COM_ANIM_PATH + "M6.png").getImage()
        }
    };
    
    // Emoticons
    public static final Image F_GRADUATE = 
            new ImageIcon(F_EMOTICON_PATH + "complete training.gif").getImage();
    public static final Image F_TRAINING = 
            new ImageIcon(F_EMOTICON_PATH + "training.gif").getImage();
    public static final Image F_WORKING = 
            new ImageIcon(F_EMOTICON_PATH + "working.gif").getImage();
    public static final Image F_BACKUP_FAIL = 
            new ImageIcon(F_EMOTICON_PATH + "backup failed.gif").getImage();
    public static final Image F_HACK = 
            new ImageIcon(F_EMOTICON_PATH + "hacked.gif").getImage();
    public static final Image F_HOLIDAY = 
            new ImageIcon(F_EMOTICON_PATH + "holiday.gif").getImage();
    public static final Image F_SICK = 
            new ImageIcon(F_EMOTICON_PATH + "sick.gif").getImage();
    public static final Image FEMALE = 
            new ImageIcon(F_EMOTICON_PATH + "nothing to do.gif").getImage();
    
    public static final Image M_GRADUATE = 
            new ImageIcon(M_EMOTICON_PATH + "complete training.gif").getImage();
    public static final Image M_TRAINING = 
            new ImageIcon(M_EMOTICON_PATH + "training.gif").getImage();
    public static final Image M_WORKING = 
            new ImageIcon(M_EMOTICON_PATH + "working.gif").getImage();
    public static final Image M_BACKUP_FAIL = 
            new ImageIcon(M_EMOTICON_PATH + "backup failed.gif").getImage();
    public static final Image M_HACK = 
            new ImageIcon(M_EMOTICON_PATH + "hacked.gif").getImage();
    public static final Image M_HOLIDAY = 
            new ImageIcon(M_EMOTICON_PATH + "holiday.gif").getImage();
    public static final Image M_SICK = 
            new ImageIcon(M_EMOTICON_PATH + "sick.gif").getImage();
    public static final Image MALE = 
            new ImageIcon(M_EMOTICON_PATH + "nothing to do.gif").getImage();
    
    // High score images
    public static final Image HIGHSCORE_PANEL = 
            new ImageIcon(HIGHSCORE_PANEL_PATH).getImage();
    
    // Metro images
    public static final Image EMPTY_ROOM = 
            new ImageIcon(METRO_PATH + "empty.png").getImage();
    public static final Image EMPTY_ROOM2 = 
            new ImageIcon(METRO_PATH + "layout.png").getImage();
    public static final Image OUR_ROOM = 
            new ImageIcon(METRO_PATH + "room.png").getImage();
    
    // Small menu images
    public static final Image SAVE_BTN = 
            new ImageIcon(SMALL_MENU_PATH + "Save.png").getImage();
    public static final Image QUIT_BTN = 
            new ImageIcon(SMALL_MENU_PATH + "Quit.png").getImage();
    public static final Image RETURN_BTN = 
            new ImageIcon(SMALL_MENU_PATH + "Return.png").getImage();
}