package de.Barryonixx.item;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ItemManager {
    private ArrayList<FloorItemStack> itemStacks;

    public ItemManager(){
        this.itemStacks = new ArrayList<>();
    }

    public void add(FloorItemStack itemStack){
        this.itemStacks.add(itemStack);
    }

    public void removeAll(){
        for (FloorItemStack itemStack : itemStacks) {
            itemStacks.remove(itemStack);
            itemStack.kill();
        }
    }

    public FloorItemStack getColliding(Player player){
        if(!isColliding(player)) return null;

        Location loc = player.getLocation();

        for(FloorItemStack itemStack : itemStacks){
            Location l = itemStack.getLocation();

            if(l.distance(loc) < 1){
                return itemStack;
            }
        }

        return null;
    }

    public void addToPlayer(Player player, FloorItemStack itemStack){
        itemStacks.remove(itemStack);
        itemStack.addToPlayer(player);
    }

    public void remove(FloorItemStack floorItemStack){
        itemStacks.remove(floorItemStack);
        floorItemStack.kill();
    }

    public boolean isColliding(Player player){
        Location loc = player.getLocation();

        for(FloorItemStack itemStack : itemStacks){
            Location l = itemStack.getLocation();

            if(l.distance(loc) < 1){
                return true;
            }
        }

        return false;
    }
}
