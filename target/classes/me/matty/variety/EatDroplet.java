package me.matty.variety;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EatDroplet implements Listener {
    @EventHandler
    public void onDropletRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        if(
            (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
            e.getItem().getItemMeta().getLore().get(0).equals("Dropper secretion.")
        ) {
            if (p.getFoodLevel() == 20) {
                p.sendMessage("You should wait before having another...");
                w.playSound(p, Sound.ENTITY_ARMOR_STAND_BREAK, 3, 1);
            } else {
                e.getItem().setAmount(e.getItem().getAmount() - 1);
                w.playSound(p, Sound.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 3, 2);
                p.setFoodLevel(20);
                p.setSaturation(20);
            }
        }
    }
}
