package de.Barryonixx.privatechest;

import de.Barryonixx.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;

public class Chestlistener implements Listener {
    @EventHandler
    public void onSignUpdate(SignChangeEvent event){
        Player player = event.getPlayer();
        Sign sign = (Sign) event.getBlock().getState();

        if(!sign.getType().name().contains("WALL_SIGN")){
            return;
        }

        WallSign s = (WallSign) event.getBlock().getBlockData();
        Block attached = event.getBlock().getRelative(s.getFacing().getOppositeFace());
        if (!attached.getType().equals(Material.CHEST)) return;

        Chest chest = (Chest) attached.getState();

        if(event.getLine(1) == null) return;

        String line1 = event.getLine(1);

        if(!ChatColor.stripColor(line1).equalsIgnoreCase("[PRIVAT]")) return;

        if(chest.getInventory() instanceof DoubleChestInventory){
            player.sendMessage("§cDu kannst keine Doppelkiste als Privat markieren!");
            return;
        }

        event.setLine(2, "§3" + player.getName());
        Main.getInstance().getChestmanager().lockChest(player, sign.getBlock(), attached);
        player.sendMessage("§aDu hast diese Kiste als Privat markiert!");
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getBlock().getType().equals(Material.CHEST)){
            Block b = event.getBlock();
            LockedChestBlock thisChest = null;

            for(LockedChestBlock lockedChest : Main.getInstance().getChestmanager().getLockedChests()){
                if(lockedChest.getChestBlock().getLocation().equals(b.getLocation())){
                    Bukkit.broadcastMessage("FOUND");
                    thisChest = lockedChest;

                    break;
                }
            }

            if(thisChest == null) return;

            Player player = event.getPlayer();

            if(!player.getName().equalsIgnoreCase(thisChest.getPlayerName())){
                player.sendMessage("§cDu kannst die Kiste eines anderen Spielers nicht zerstören!");
                event.setCancelled(true);
            }else{
                player.sendMessage("§aDu hast deine Private Kiste zerstört!");
            }

            Main.getInstance().getChestmanager().removeChest(thisChest);

            return;
        }

        if (event.getBlock().getType().name().toLowerCase().contains("sign")) {
            Sign sign = (Sign) event.getBlock().getState();

            if (sign.getLine(1).equalsIgnoreCase("[privat]") && sign.getType().name().toLowerCase().contains("wall_sign")) {

                WallSign wallSign = (WallSign) sign.getBlockData();
                Block attached = event.getBlock().getRelative(wallSign.getFacing().getOppositeFace());

                if(attached.getType().equals(Material.CHEST)){
                    Block chestBlock = attached;
                    Block signBlock = event.getBlock();

                    LockedChestBlock thisChest = null;

                    for(LockedChestBlock lockedChest : Main.getInstance().getChestmanager().getLockedChests()){
                        if(lockedChest.getChestBlock().getLocation().equals(chestBlock.getLocation())){
                            thisChest = lockedChest;
                            break;
                        }
                    }

                    if(thisChest == null){
                        event.getPlayer().sendMessage("§cEs ist ein Fehler aufgetreten. Um die Privat-Chest zu zerstören, probiere die Kiste zu zerstören, besteht das Problem weiterhin, wende dich an den Support!");
                        event.setCancelled(true);
                    }

                    Main.getInstance().getChestmanager().removeChest(thisChest);
                    event.getPlayer().sendMessage("§aDiese Kiste ist nun nicht mehr als Privat markiert!");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.CHEST)) {
            LockedChestBlock thisChest = null;

            for(LockedChestBlock lockedChest : Main.getInstance().getChestmanager().getLockedChests()){
                if(lockedChest.getChestBlock().getLocation().equals(event.getClickedBlock().getLocation())){
                    thisChest = lockedChest;
                    break;
                }
            }

            if(thisChest == null) return;

            if (!thisChest.getPlayerName().equalsIgnoreCase(player.getName())) {
                player.sendMessage("§cDu kannst die private Kiste eines anderen Spielers nicht öffnen!");
                event.setCancelled(true);
            }
        }
    }
}
