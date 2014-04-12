/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.util.ArrayList;
import java.util.List;
import model.room.Hardware;
import view.ViewUtilities;

/**
 * Construct the list of available computers for the system
 * @author Benjamin
 */
public class CompGenerator implements ViewUtilities{
    private static List<Hardware> compList;
    
    private CompGenerator(){
    }
    
    /**
     * Generate the list of available computers for the system
     * @return list of available computers
     */
    public static ArrayList<Hardware> generateCompList() {
        compList = new ArrayList<Hardware>();
        
        compList.add(new Hardware(PX64RX_IDX, Hardware.PX64RX,
                Hardware.PX64RX_PRICE, Hardware.PX64RX_PRO));
        compList.add(new Hardware(IPAC_IDX, Hardware.IPAC_3G,
                Hardware.IPAC_PRICE, Hardware.IPAC_PRO));
        compList.add(new Hardware(V5LX_IDX, Hardware.V5LX,
                Hardware.V5LX_PRICE, Hardware.V5LX_PRO));
        compList.add(new Hardware(TERMINUS2_IDX, Hardware.TERMINUS_II,
                Hardware.TERMINUS_PRICE, Hardware.TERMINYS_PRO));
        
        return new ArrayList<Hardware>(compList);
    }
}
