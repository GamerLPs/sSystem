package de.Barryonixx.COMMANDS;
import de.Barryonixx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;

public class WETTERCOMMAND implements CommandExecutor, Listener {

    public static Inventory invTag = Bukkit.createInventory(null, 9, "§8§oBitte wähle:");


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player)sender;
            if(command.getName().equalsIgnoreCase("wetter")){
                if(p.hasPermission("system.wetter")){

                    ArrayList YWLore = new ArrayList();
                    YWLore.add("§e§oHier mit wählst du Tag aus!");
                    ItemStack YW = new ItemStack(Material.YELLOW_BANNER, 1);
                    ItemMeta YWM = YW.getItemMeta();
                    YWM.setDisplayName("§7Auswahl: §a§l§oTAG/DAY");
                    YWM.setLore(YWLore);
                    YW.setItemMeta(YWM);



                    ArrayList BWLore = new ArrayList();
                    BWLore.add("§e§oHier mit wählst du Nacht aus!");
                    ItemStack BW = new ItemStack(Material.BLACK_BANNER, 1);
                    ItemMeta BWM = BW.getItemMeta();
                    BWM.setDisplayName("§7Auswahl: §a§l§oNACHT/NIGHT");
                    BWM.setLore(BWLore);
                    BW.setItemMeta(BWM);


                    ArrayList GWLore = new ArrayList();
                    GWLore.add("§e§oHier mit wählst du Regen aus!");
                    ItemStack GW = new ItemStack(Material.GRAY_BANNER, 1);
                    ItemMeta GWM = GW.getItemMeta();
                    GWM.setDisplayName("§7Auswahl: §a§l§oREGEN/RAIN");
                    GWM.setLore(GWLore);
                    GW.setItemMeta(GWM);


                    ArrayList BLWLore = new ArrayList();
                    BLWLore.add("§e§oHier mit wählst du Gewitter aus!");
                    ItemStack BLW = new ItemStack(Material.LIGHT_BLUE_BANNER, 1);
                    ItemMeta BLWM = BLW.getItemMeta();
                    BLWM.setDisplayName("§7Auswahl: §a§l§oGEWITTER/THUNDER");
                    BLWM.setLore(BLWLore);
                    BLW.setItemMeta(BLWM);


                    ArrayList RLore = new ArrayList();
                    RLore.add("§e§oHier entfernst alles! §7§o(REGEN, GEWITTER, NACHT)");
                    ItemStack RESET = new ItemStack(Material.GREEN_BANNER, 1);
                    ItemMeta RESETM = RESET.getItemMeta();
                    RESETM.setDisplayName("§7Auswahl: §a§l§oRESET");
                    RESETM.setLore(RLore);
                    RESET.setItemMeta(RESETM);

                    ItemStack leer = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
                    ItemMeta leerM = leer.getItemMeta();
                    leerM.setDisplayName(" ");
                    leer.setItemMeta(leerM);



                    invTag.setItem(0, YW);
                    invTag.setItem(1, BW);
                    invTag.setItem(2, leer);
                    invTag.setItem(3, leer);
                    invTag.setItem(4, RESET);
                    invTag.setItem(5, leer);
                    invTag.setItem(6, leer);
                    invTag.setItem(7, GW);
                    invTag.setItem(8, BLW);
                    p.openInventory(invTag);

                }else{
                    p.sendMessage(Main.perm);
                }
            }
        }else{
            System.out.println("DU BIST KEIN SPIELER!");
        }

        return false;
    }

    @EventHandler
    public void onWetter (InventoryClickEvent event){
        final Player p = (Player) event.getWhoClicked();
        if(event.getInventory().equals(invTag)){
            event.setCancelled(true);

            switch (event.getCurrentItem().getType()){

                case YELLOW_BANNER:
                    p.closeInventory();
                    p.getWorld().setTime(1000);
                    p.getWorld().setStorm(false);
                    p.getWorld().setThundering(false);
                    p.sendMessage("§8| §c§lTAG§8 » §7§oDeine Auswahl war erfolgreich!");
                    break;

                case BLACK_BANNER:
                    p.closeInventory();
                    p.getWorld().setTime(13000);
                    p.sendMessage("§8| §c§lNACHT§8 » §7§oDeine Auswahl war erfolgreich!");
                    break;

                case GREEN_BANNER:
                    p.closeInventory();
                    p.getWorld().setTime(1000);
                    p.getWorld().setThundering(false);
                    p.getWorld().setStorm(false);
                    p.getWorld().setWeatherDuration(0);
                    p.sendMessage("§8| §c§lRESET§8 » §7§oDeine Auswahl war erfolgreich!");
                    break;

                case GRAY_BANNER:
                    p.closeInventory();
                    p.getWorld().setStorm(true);
                    p.getWorld().setWeatherDuration(5);
                    p.sendMessage("§8| §c§lREGEN§8 » §7§oDeine Auswahl war erfolgreich!");
                    break;

                case LIGHT_BLUE_BANNER:
                    p.closeInventory();
                    p.getWorld().setTime(13000);
                    p.getWorld().setStorm(true);
                    p.getWorld().setThundering(true);
                    p.sendMessage("§8| §c§lGEWITTER§8 » §7§oDeine Auswahl war erfolgreich!");
                    break;

            }
        }
    }
}
