package me.matty.variety.commands;

import me.matty.variety.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RemoveDropper implements CommandExecutor {
    private final Main main;

    public RemoveDropper(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player && ((Player)sender).isOp()) {
            if(PlaceDropper.getDropperPlaced()) {
                World w = ((Player)sender).getWorld();
                w.setType(PlaceDropper.getDropperLocation(), Material.AIR);
                PlaceDropper.setDropperPlaced(false);

                PersistentDataContainer p = w.getPersistentDataContainer();
                NamespacedKey n = new NamespacedKey(Main.getPlugin(), "dropper_placed");
                p.set(n, PersistentDataType.INTEGER, 0);

                ((Player)sender).sendMessage("The dropper has been removed.");
                return true;
            } else {
                ((Player)sender).sendMessage("The dropper hasn't been placed yet.");
                return true;
            }
        }
        return false;
    }
}
