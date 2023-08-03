package me.matty.variety.commands;

import me.matty.variety.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GetDroplet implements CommandExecutor {
    private final Main main;

    public  GetDroplet(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player && ((Player)sender).isOp()) {
            int c;
            if(args.length == 1) {
                try {
                    c = Integer.parseInt(args[0]);
                } catch(final NumberFormatException e) {
                    sender.sendMessage("\"" + args[0] + "\" is not a number");
                    return true;
                }
            } else { c = 1;}

            for(int i = 0; i < c; i++) {
                ItemStack d = new ItemStack(Material.NETHER_STAR);
                ItemMeta m = (ItemMeta) d.getItemMeta();
                m.setDisplayName("Droplet");
                ArrayList<String> l = new ArrayList<String>();
                l.add("Dropper secretion.");
                m.setLore(l);
                d.setItemMeta(m);
                ((Player)sender).getInventory().addItem(d);
            }
            return true;
        }
        return false;
    }
}
