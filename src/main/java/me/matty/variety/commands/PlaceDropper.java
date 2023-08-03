package me.matty.variety.commands;

import me.matty.variety.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlaceDropper implements CommandExecutor {
    private final Main main;
    private static boolean dropperPlaced;
    private static Location dropperLocation;

    public PlaceDropper(Main main) {
        this.main = main;
    }

    public static void setDropperPlaced(boolean placed) {
        PlaceDropper.dropperPlaced = placed;
    }

    public static boolean getDropperPlaced() {
        return dropperPlaced;
    }

    public static Location getDropperLocation() {
        return dropperLocation;
    }

    public static void setDropperLocation(Location l) {
        dropperLocation = l;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player && ((Player)sender).isOp()) {
            if(dropperPlaced) {
                ((Player)sender).sendMessage("The dropper has already been placed.");
                return true;
            }
            Block b = ((Player) sender).getLocation().getBlock();
            dropperLocation = b.getLocation();

            World w = b.getWorld();
            PersistentDataContainer p = w.getPersistentDataContainer();
            NamespacedKey nx = new NamespacedKey(Main.getPlugin(), "dopper_location_block_x");
            NamespacedKey ny = new NamespacedKey(Main.getPlugin(), "dopper_location_block_y");
            NamespacedKey nz = new NamespacedKey(Main.getPlugin(), "dopper_location_block_z");

            p.set(nx, PersistentDataType.INTEGER, dropperLocation.getBlockX());
            p.set(ny, PersistentDataType.INTEGER, dropperLocation.getBlockY());
            p.set(nz, PersistentDataType.INTEGER, dropperLocation.getBlockZ());

            b.setType(Material.DROPPER);
            BlockData d = b.getBlockData();
            ((Directional)d).setFacing(BlockFace.DOWN);
            b.setBlockData(d);
            BlockState bl = b.getState();
            ((Dropper)bl).setLock("Secret Key");
            bl.update();
            dropperPlaced = true;
            NamespacedKey n = new NamespacedKey(Main.getPlugin(), "dropper_placed");

            p.set(n, PersistentDataType.INTEGER, 1);

            ((Player)sender).sendMessage("The dropper has been placed.");
            return true;
        }
        return false;
    }
}
