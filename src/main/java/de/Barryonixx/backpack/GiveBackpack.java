package de.Barryonixx.backpack;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class GiveBackpack implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (sender.hasPermission("skylydra.backpackcommand")) {
            Bukkit.broadcastMessage("t");

            ItemStack skull = createSkull("§e§lRucksack", true, 1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTgyNjQxNzg2YTQyMjA4OGY3NWRjZWU3MDIwNWQ1ODA2MDBmNjlkNmFhMmY3N2QyNjc4YjU4ZDg5YjY5NzNhNiJ9fX0=", "\n", "§aÖffnet deinen Rucksack");

            player.getInventory().addItem(skull);

        }
        return false;
    }

    private ItemStack createSkull(String name, boolean unbreakable, int count, String url, String... itemLore) {
        ItemStack head = new ItemStack(Material.LEGACY_SKULL_ITEM, count, (short) 3);
        if (url.isEmpty())
            return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setUnbreakable(unbreakable);

        ArrayList<String> lore = new ArrayList<>();

        for(String s : itemLore){
            lore.add(s);
        }

        headMeta.setLore(lore);
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }

        headMeta.setDisplayName(name);
        head.setItemMeta(headMeta);
        return head;
    }
}
