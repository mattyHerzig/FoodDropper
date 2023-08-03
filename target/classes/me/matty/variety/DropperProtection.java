package me.matty.variety;

import me.matty.variety.commands.PlaceDropper;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class DropperProtection implements Listener {

    @EventHandler
    public void onDropperBreak(BlockBreakEvent b) {
        if(
            PlaceDropper.getDropperPlaced() &&
            PlaceDropper.getDropperLocation().equals(b.getBlock().getLocation())
        ) {
            b.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropperBlockExplosion(BlockExplodeEvent b) {
        if(
            PlaceDropper.getDropperPlaced() &&
            PlaceDropper.getDropperLocation().equals(b.getBlock().getLocation())
        ) {
            b.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropperExplosion(EntityExplodeEvent e) {
        for(Block b : e.blockList().toArray(new Block[e.blockList().size()])) {
            if(
                PlaceDropper.getDropperPlaced() &&
                PlaceDropper.getDropperLocation().equals(b.getLocation())
            ) {
                e.blockList().remove(b);
            }
        }
    }
}
