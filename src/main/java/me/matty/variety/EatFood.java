package me.matty.variety;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EatFood implements Listener {
    @EventHandler
    public void onPlayerFoodLevelChangeEvent(FoodLevelChangeEvent f) {
        Entity e = f.getEntity();
        if(e instanceof Player && f.getItem().getType().isEdible()) {
            f.setCancelled(true);
            World w = e.getWorld();
            w.playSound(e, Sound.ENTITY_PLAYER_BURP, 3, 5);
        }
    }
}