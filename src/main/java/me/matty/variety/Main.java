package me.matty.variety;

import me.matty.variety.commands.GetDroplet;
import me.matty.variety.commands.PlaceDropper;
import me.matty.variety.commands.RemoveDropper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public final class Main extends JavaPlugin {

    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getLogger().info("Enabling Dropper Plugin");
        getServer().getPluginManager().registerEvents(new EatFood(), this);
        getServer().getPluginManager().registerEvents(new EatDroplet(), this);
        getServer().getPluginManager().registerEvents(new DropperProtection(), this);
        getServer().getPluginCommand("dropperplace").setExecutor(new PlaceDropper(this));
        getServer().getPluginCommand("dropperremove").setExecutor(new RemoveDropper(this));
        getServer().getPluginCommand("droppergetdroplet").setExecutor(new GetDroplet(this));

        World w = getServer().getWorlds().get(0); //Assuming current world in position 0
        PersistentDataContainer p = w.getPersistentDataContainer();
        NamespacedKey n = new NamespacedKey(Main.getPlugin(), "dropper_placed");
        if(p.has(n, PersistentDataType.INTEGER)) {
            if(p.get(n, PersistentDataType.INTEGER) == 1) {
                PlaceDropper.setDropperPlaced(true);
            } else {
                PlaceDropper.setDropperPlaced(false);
            }
        }

        NamespacedKey nx = new NamespacedKey(Main.getPlugin(), "dopper_location_block_x");
        NamespacedKey ny = new NamespacedKey(Main.getPlugin(), "dopper_location_block_y");
        NamespacedKey nz = new NamespacedKey(Main.getPlugin(), "dopper_location_block_z");
        if(p.has(n, PersistentDataType.INTEGER)) {
            PlaceDropper.setDropperLocation(new Location(
                    w,
                    p.get(nx, PersistentDataType.INTEGER),
                    p.get(ny, PersistentDataType.INTEGER),
                    p.get(nz, PersistentDataType.INTEGER)
            ));
        }

        dropperFunctionality();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabling Dropper Plugin");
    }

    public void dropperFunctionality() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if(PlaceDropper.getDropperPlaced()) {
                    World w = getServer().getWorlds().get(0); //Assuming world in position 0
                    Location loc = new Location(
                            w,
                            PlaceDropper.getDropperLocation().getBlockX() + 0.5,
                            PlaceDropper.getDropperLocation().getBlockY() - 0.5,
                            PlaceDropper.getDropperLocation().getBlockZ() + 0.5
                            );

                    ItemStack d = new ItemStack(Material.NETHER_STAR);
                    ItemMeta m = (ItemMeta) d.getItemMeta();
                    m.setDisplayName("Droplet");
                    ArrayList<String> l = new ArrayList<String>();
                    l.add("Dropper secretion.");
                    m.setLore(l);
                    d.setItemMeta(m);

                    Item i = w.dropItem(loc, d);
                    double rx = 0.25*(Math.random() - 0.5);
                    double rz = 0.25*(Math.random() - 0.5);
                    i.setVelocity(new Vector(rx, -0.1, rz));

                    Block b = w.getBlockAt(PlaceDropper.getDropperLocation());
                    if(!(b.getType().equals(Material.DROPPER) && ((Directional)(b.getBlockData())).getFacing().equals(BlockFace.DOWN))) {
                        b.setType(Material.DROPPER);
                        BlockData data = b.getBlockData();
                        ((Directional)data).setFacing(BlockFace.DOWN);
                        BlockState bl = b.getState();
                        ((Dropper)bl).setLock("Secret Key");
                        bl.update();
                        b.setBlockData(data);
                    }
                }
            }
        }.runTaskTimer(this, 0, 175);
    }
}
