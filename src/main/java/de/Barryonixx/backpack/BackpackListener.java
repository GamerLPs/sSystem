package de.Barryonixx.backpack;

import de.Barryonixx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BackpackListener implements Listener {

    final String backpackName = "§e§lRucksack";

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getItemInHand() == null) return;

        ItemStack s = player.getItemInHand();

        if(s.getItemMeta() == null) return;

        if (s.getItemMeta().getDisplayName().equalsIgnoreCase(backpackName)) {

            Backpack backpack = Main.getInstance().getBackpackManager().getBackpack(player.getUniqueId());

            player.openInventory(backpack.getInventory());
        }


    }
}
