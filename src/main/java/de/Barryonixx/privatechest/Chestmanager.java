package de.Barryonixx.privatechest;

import de.Barryonixx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftChest;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Chestmanager {
    private ArrayList<LockedChestBlock> lockedChests;

    public Chestmanager(){
        this.lockedChests = new ArrayList<>();

        loadChests();
    }

    public void lockChest(Player player, Block sign, Block chestSelf){
        this.lockedChests.add(new LockedChestBlock(player, chestSelf, sign));
        save();
    }

    public ArrayList<LockedChestBlock> getLockedChests() {
        return lockedChests;
    }

    public boolean isChestLocked(Block block){
        for(LockedChestBlock b : this.lockedChests){
            if(b.getChestBlock() == block) {
                return true;
            }
        }

        return false;
    }

    public void save(){
        YamlConfiguration chests = Main.getInstance().getFileManager().getPlayerData();
        int iteration = 0;

        for(LockedChestBlock b : this.lockedChests){
            chests.set("LockedChest." + iteration +  ".Player", b.getPlayerName());

            chests.set("LockedChest." + iteration + ".Chest.X", b.getChestBlock().getLocation().getBlockX());
            chests.set("LockedChest." + iteration + ".Chest.Y", b.getChestBlock().getLocation().getBlockY());
            chests.set("LockedChest." + iteration + ".Chest.Z", b.getChestBlock().getLocation().getBlockZ());
            chests.set("LockedChest." + iteration + ".Chest.World", b.getChestBlock().getLocation().getWorld().getName());


            chests.set("LockedChest." + iteration + ".Sign.X", b.getSignBlock().getLocation().getBlockX());
            chests.set("LockedChest." + iteration + ".Sign.Y", b.getSignBlock().getLocation().getBlockY());
            chests.set("LockedChest." + iteration + ".Sign.Z", b.getSignBlock().getLocation().getBlockZ());
            chests.set("LockedChest." + iteration + ".Sign.World", b.getSignBlock().getLocation().getWorld().getName());

            iteration++;
        }

        Main.getInstance().getFileManager().saveFiles();
    }

    private void loadChests() {
        YamlConfiguration chests = Main.getInstance().getFileManager().getPlayerData();

        if(chests.getConfigurationSection("LockedChest") == null) return;

        for(String chestPath : chests.getConfigurationSection("LockedChest").getKeys(false)){
            int cX = chests.getInt("LockedChest." + chestPath + ".Chest.X");
            int cY = chests.getInt("LockedChest." + chestPath + ".Chest.Y");
            int cZ = chests.getInt("LockedChest." + chestPath + ".Chest.Z");
            String worldName = chests.getString("LockedChest." + chestPath + ".Chest.World");

            int sX = chests.getInt("LockedChest." + chestPath + ".Sign.X");
            int sY = chests.getInt("LockedChest." + chestPath + ".Sign.Y");
            int sZ = chests.getInt("LockedChest." + chestPath + ".Sign.Z");
            String sWorldName = chests.getString("LockedChest." + chestPath + ".Sign.World");

            Location sign = new Location(Bukkit.getWorld(sWorldName), sX, sY, sZ);
            Location chest = new Location(Bukkit.getWorld(worldName), cX, cY, cZ);

            Block signBlock = Bukkit.getWorld(sWorldName).getBlockAt(sign);
            Block chestBlock = Bukkit.getWorld(worldName).getBlockAt(chest);
            String playerName = chests.getString("LockedChest." + chestPath + ".Player");


            this.lockedChests.add(new LockedChestBlock(playerName, chestBlock, signBlock));
            Bukkit.getServer().getConsoleSender().sendMessage("Chestblock hinzugefügt!");
        }
    }

    public void removeChest(LockedChestBlock chest){

        YamlConfiguration chests = Main.getInstance().getFileManager().getPlayerData();

        chests.set("LockedChest", null);
        Main.getInstance().getFileManager().saveFiles();

        lockedChests.remove(chest);

        save();
    }
}
