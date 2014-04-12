/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.roomproperties;

import view.MainFrame;

/**
 *
 * @author HungHandsome
 */
public class GetCurrRoomCommand {
    
    private static RoomView room;

    public static RoomView getRoom() {
        try {
            return (RoomView) (MainFrame.getInstance().getCurrPanel());
        } catch(Exception ex) {
            return room;
        }        
    }

    public static void setRoom(RoomView room) {
        GetCurrRoomCommand.room = room;
    }
    
}
