/*
 * @author Aoi Mizu
 */

package model.effect;

import java.util.ArrayList;
import model.player.Player;

public class MoneyModification implements EffectInterface {
    private static final ArrayList<Integer> moneyChange = 
                                                    new ArrayList<Integer>();
    private EffectInterface e;
    private int money;
    
    public MoneyModification(int money, EffectInterface e) {
        this.money = money;
        this.e = e;
    }

    @Override
    public void applyEffect() {
        // Add money to player
        Player p = Player.getInstance();
        moneyChange.add(money);
        p.setMoney(p.getMoney() + money);
        if(e!=null) {
            e.applyEffect();
        }
    }

    public static ArrayList<Integer> getMoneyChange() {
        return moneyChange;
    }
}
