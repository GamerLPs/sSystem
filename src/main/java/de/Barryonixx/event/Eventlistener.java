package de.Barryonixx.event;

import de.Barryonixx.Main;
import de.Barryonixx.item.FloorItemStack;
import de.Barryonixx.item.ItemManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Eventlistener implements Listener {
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        Item item = event.getItemDrop();

        new BukkitRunnable() {
            @Override
            public void run() {
                item.remove();
                FloorItemStack floorItemStack = new FloorItemStack(item.getLocation(), item.getItemStack());
                Main.getInstance().getItemManager().add(floorItemStack);
                floorItemStack.show();
            }
        }.runTaskLater(Main.getInstance(), 40);
    }

    @EventHandler
    public void onCollide(PlayerMoveEvent event){
        Player player = event.getPlayer();

        for(Entity e : player.getWorld().getEntities()){
            if(e instanceof Item && ((Item) e).isOnGround()){
                FloorItemStack floorItemStack = new FloorItemStack(e.getLocation(), ((Item) e).getItemStack());
                Main.getInstance().getItemManager().add(floorItemStack);
                floorItemStack.show();
                e.remove();
            }
        }

        if(event.getFrom().distance(event.getTo()) < 0.2){
            return;
        }



        if (Main.getInstance().getItemManager().isColliding(event.getPlayer())) {
            FloorItemStack i = Main.getInstance().getItemManager().getColliding(event.getPlayer());
            Main.getInstance().getItemManager().addToPlayer(event.getPlayer(), i);
        }
    }
}
