/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.room;

import java.io.Serializable;
import java.util.Observable;
import java.util.Random;
import model.player.Player;
import view.ViewUtilities;

/**
 *
 * @author s3342128
 */
public class Hardware extends Observable implements Serializable {

    // Computer's identity
    public static final String PX64RX = "PX-64RX";
    public static final String IPAC_3G = "iPac 3G";
    public static final String V5LX = "V5-LX";
    public static final String TERMINUS_II = "Terminus II";
    // Computer's price
    public static final int PX64RX_PRICE = 1000;
    public static final int IPAC_PRICE = 3000;
    public static final int V5LX_PRICE = 8000;
    public static final int TERMINUS_PRICE = 20000;
    // Computer's productivity
    public static final float PX64RX_PRO = 1.5f;
    public static final float IPAC_PRO = 2.0f;
    public static final float V5LX_PRO = 3.0f;
    public static final float TERMINYS_PRO = 5.0f;
    public static final float UPGRADED_PRO = 2.0f;
    private Room room; //The room containing this table
    private WorkingTable table; //The table which this hardware is put on.
    private int imgIdx; //The image path of the hardware
    private String name; //The name of the hardware
    private int price; // Price of the hardware
    // This affects the final function points generated
    private float productivity;
    // This affects the final productivity of the hardware
    private float equipPoint;
    private boolean equip;
    private int x; //The x coordinate to draw the hardware
    private int y; //The y coordinate to draw the hardware
    private boolean faceUp; //If true, the hardware face is turn up.
    //Used for computers only.
    private final String DEFAULT_SERIAL = "001";
    private String serial = DEFAULT_SERIAL;
    private int position;

    public Hardware(int imgIdx, String name, int price, float productivity) {
        init(imgIdx, name, price, productivity);
    }

    public Hardware(int imgIdx, boolean faceUp,
            String name, int price, float productivity) {
        init(imgIdx, name, price, productivity);
        this.faceUp = faceUp;
    }

    private void init(int imgIdx, String name, int price, float productivity) {
        this.imgIdx = imgIdx;
        this.name = name;
        this.price = price;
        this.productivity = productivity;
//        generateSerial();
//        name = imgPath.split(".")[0];
//        name = imgPath;
    }

    public Hardware cloneHardware() {
        return new Hardware(imgIdx, name, price, productivity).generateSerial();
    }

    public boolean isMonitor() {
        return imgIdx == ViewUtilities.MONITOR_IDX;
    }

    public void setCoordinates(int hx, int hy) {
        x = hx;
        y = hy;
    }

    public int getImgIdx() {
        return imgIdx;
    }

    public void setEquipPoint(float equipPoint) {
        this.equipPoint = equipPoint;
        if (this.equipPoint != 0.0f) {
            equip = true;
        } else {
            equip = false;
        }
    }

    /**
     * Generate productivity to calculate final function points output
     *
     * @return productivity
     */
    public float getProductivity() {
        if (equipPoint != 0) {
            return productivity * equipPoint;
        }
        return productivity;
    }

    public int getPrice() {
        return price;
    }

    public float getEquipPoint() {
        return equipPoint;
    }

    public boolean isEquipped() {
        return equip;
    }

    public void setImgIdx(int imgIdx) {
        this.imgIdx = imgIdx;
//        setChanged();
//        notifyObservers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
//        setChanged();
//        notifyObservers();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
//        setChanged();
//        notifyObservers();
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceDown) {
        this.faceUp = faceDown;
//        setChanged();
//        notifyObservers();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public WorkingTable getTable() {
        return table;
    }

    public void setTable(WorkingTable table, int position) {
        this.table = table;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setSerial(String serial) {
        if (this.serial.equals(DEFAULT_SERIAL)) {
            this.serial = serial;
        }
    }

    public String getSerial() {
        return serial;
    }

    public String generateSerialInit() {
        Random generator = new Random();
        return this.generatePrefix() + generator.nextInt(10000);
    }

    private Hardware generateSerial() {
        do {
            serial = generateSerialInit();
        } while (Player.getInstance().getComputers().containsKey(serial)
                && Player.getInstance().getTableComputers().containsKey(serial));
        return this;
    }

    private String generatePrefix() {
        return "#" + name.toUpperCase().charAt(0);
    }
}
