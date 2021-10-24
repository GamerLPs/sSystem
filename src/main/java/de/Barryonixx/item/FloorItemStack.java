package de.Barryonixx.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FloorItemStack {
    private Location location;
    private ItemStack itemStack;
    private ArmorStand armorStand;

    public FloorItemStack(Location location, ItemStack itemStack){
        this.location = location;
        this.itemStack = itemStack;
    }

    public void show(){
        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), location.getX(), location.getY() - 0.45 , location.getZ()), EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setSmall(true);
        armorStand.setHelmet(itemStack);
    }

    public void kill(){
        armorStand.remove();
    }

    public void addToPlayer(Player player){
        if (isInventoryFull(player)) {
            player.sendMessage("§cDein Inventar ist Voll!");
            return;
        }

        player.getInventory().addItem(itemStack);
        armorStand.remove();
    }

    private boolean isInventoryFull(Player player){
        boolean r = true;

        for(ItemStack i : player.getInventory().getContents()){
            if(i == null){
                r = false;
            }
        }

        return r;
    }

    public Location getLocation() {
        return location;
    }
}
