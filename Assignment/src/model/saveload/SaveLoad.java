/*
 * @author Aoi Mizu
 */

package model.saveload;

import java.io.*;
import model.player.Player;
import model.player.PlayerRank;

public class SaveLoad {
    
    // File paths to save data. Save1, save2, & save3 are the 3 manual saves.
    public static final String SAVE_PATH_1 = "src/data/save1.bin";
    public static final String SAVE_PATH_2 = "src/data/save2.bin";
    public static final String SAVE_PATH_3 = "src/data/save3.bin";
    public static final String AUTOSAVE_PATH = "src/data/autosave.bin";
    public static final String SAVE_RANK_PATH = "src/data/playerRank.bin";
    
    private SaveLoad() {
    }
    
    /**
     * Save the game process
     * @param p contain all data of the current game
     * @param path the path to the save file
     * @return -true if saving success
     *         -false if error occurred during saving
     */
    public static boolean save(Player p, String path) {
        try {
            // Open the file
            File file = new File(path);
            file.createNewFile();
            if (file.exists()) {
                FileOutputStream out = new FileOutputStream(file);
                ObjectOutputStream data_out = new ObjectOutputStream(out);

                // Write the object to the file
                data_out.writeObject(p);
                data_out.close();
                return true;
            }
        } catch (IOException ex) {
            // Error while saving data
            ex.printStackTrace();
            System.out.println("ERROR WHILE SAVING\n IOException");
        }
        return false;
    }
    
    /**
     * Load the last game process form file
     * @param path the path to the save file
     * @return player, which contains all data of the game process
     */
    public static Player load(String path) {
        try {
            // Open the file
            File file = new File(path);

            // Check if the file exist
            if (file.exists()) {
                try {
                    FileInputStream in = new FileInputStream(file);
                    ObjectInputStream data_in = new ObjectInputStream(in);

                    // Return object writen in the file
                    return (Player) data_in.readObject();
                } catch (Exception e) {
                    // Error in reading file
                    e.printStackTrace();
                    System.out.println("ERROR LOADING SAVED FILE");
                    return null;
                }
            }
        }
        catch (Exception ex) {
            
        }
        // Error in reading file
        return null;
    }
    
    /**
     * Delete the save in a particular path.
     * @param path the path to the save file
     * @return 
     */
    public static boolean deleteSave(String path) {
        File f = new File(path);
        boolean delSuccess = f.delete();
        
        System.out.println("Delete file " + delSuccess);
        
        Player.reinitInstance();
        return delSuccess;
    }
    
    /**
     * Save player rank
     * @param rank contain the rank of all players
     * @return -true if saving success
     *         -false if error occurred during saving
     */
    public static boolean saveRank(PlayerRank rank) {
        try {
            // Open the file
            File file = new File(SAVE_RANK_PATH);
            file.createNewFile();
            if (file.exists()) {
                FileOutputStream out = new FileOutputStream(file);
                ObjectOutputStream data_out = new ObjectOutputStream(out);

                // Write the object to the file
                data_out.writeObject(rank);
                data_out.close();
                return true;
            }
        } catch (IOException ex) {
            // Error while saving data
            ex.printStackTrace();
            System.out.println("ERROR WHILE SAVING RANK\n IOException");
        }
        return false;
    }
    
    /**
     * Load the rank of all players from file
     * @return rank, which contains rank of all players
     */
    public static PlayerRank loadRank() {
        try {
            // Open the file
            File file = new File(SAVE_RANK_PATH);

            // Check if the file exist
            if (file.exists()) {
                try {
                    FileInputStream in = new FileInputStream(file);
                    ObjectInputStream data_in = new ObjectInputStream(in);
                    // Return object writen in the file
                    return (PlayerRank) data_in.readObject();
                } catch (Exception e) {
                    // Error in reading file
                    e.printStackTrace();
                    System.out.println("ERROR LOADING SAVED FILE");
                    return null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        // Error in reading file
        return null;
    }
}
