package de.Barryonixx.privatechest;

import de.Barryonixx.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;

public class Chestlistener implements Listener {
    private ArrayList<Block> lockedChests;

    public Chestlistener(){
        this.lockedChests = new ArrayList<>();

        loadBlockedChests();
    }

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

        if(event.getLine(1) == null) return;

        String line1 = event.getLine(1);

        if(!ChatColor.stripColor(line1).equalsIgnoreCase("[PRIVAT]")) return;

        event.setLine(2, "§3" + player.getName());

        player.sendMessage("§aDu hast diese Kiste als Privat markiert!");

        saveLock(player, event.getBlock());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        removeBlockOfYaml(event.getPlayer(), event.getBlock());
    }

    private void saveLock(Player player, Block b){
        YamlConfiguration playerData = Main.getInstance().getFileManager().getPlayerData();

        int x = b.getX();
        int y = b.getY();
        int z = b.getZ();
        String worldName = b.getWorld().getName();

        int lockBlockCount = 0;
        List<String> lockingPlayers = new ArrayList<>();

        if(playerData.isSet("LockChest.LockingPlayers")){
            lockingPlayers = playerData.getStringList("LockChest.LockingPlayers");
        }

        if(playerData.isSet("LockChest." + player.getName() + ".BlockLockCount")){
            lockBlockCount = playerData.getInt("LockChest." + player.getName() + ".BlockLockCount");
        }

        lockBlockCount++;

        if(!lockingPlayers.contains(player.getName())){
            lockingPlayers.add(player.getName());
        }

        playerData.set("LockChest.LockingPlayers", lockingPlayers);

        String s = String.valueOf(lockBlockCount);

        playerData.set("LockChest." + player.getName() + ".BlockLockCount", lockBlockCount);
        playerData.set("LockChest." + player.getName() + "." + s + ".X", x);
        playerData.set("LockChest." + player.getName() + "." + s + ".Y", y);
        playerData.set("LockChest." + player.getName() + "." + s + ".Z", z);
        playerData.set("LockChest." + player.getName() + "." + s + ".World", worldName);

        Main.getInstance().getFileManager().saveFiles();

        this.lockedChests.add(b);
    }

    private void loadBlockedChests(){
        YamlConfiguration playerData = Main.getInstance().getFileManager().getPlayerData();

        List<String> lockingPlayers = new ArrayList<>();

        if (!playerData.isSet("LockChest.LockingPlayers")) {
            return;
        }else{
            lockingPlayers = playerData.getStringList("LockChest.LockingPlayers");
        }

        for(String s : lockingPlayers){
            int lockBlockCountOfPlayer = playerData.getInt("LockChest." + s + ".BlockLockCount");

            for(int i = 1; i < lockBlockCountOfPlayer; i++){
                int x = playerData.getInt("LockChest." + s + "." + i + ".X");
                int y = playerData.getInt("LockChest." + s + "." + i + ".Y");
                int z = playerData.getInt("LockChest." + s + "." + i + ".Z");
                World world = Bukkit.getWorld(playerData.getString("LockChest." + s + "." + i + ".World"));

                Block blocked = world.getBlockAt(new Location(world, x, y, z));

                lockedChests.add(blocked);

            }
        }
    }

    private void removeBlockOfYaml(Player p, Block b){
        YamlConfiguration playerData = Main.getInstance().getFileManager().getPlayerData();

        int x = b.getX();
        int y = b.getY();
        int z = b.getZ();
        String worldName = b.getWorld().getName();

        int lockedBlocks = playerData.getInt("LockChest." + p.getName() + ".BlockLockCount");

        Bukkit.broadcastMessage("Trying " + p.getName() + " with Blocklockcount: " + lockedBlocks);

        for(int i = 1; i <= lockedBlocks; i++){
            Bukkit.broadcastMessage("ITERATION: "+ i);
            int x2 = playerData.getInt("LockChest." + p.getName() + "." + i + ".X");
            int y2 = playerData.getInt("LockChest." + p.getName() + "." + i + ".Y");
            int z2 = playerData.getInt("LockChest." + p.getName() + "." + i + ".Z");
            String worldName2 = playerData.getString("LockChest." + p.getName() + "." + i + ".World");

            if (x == x2 && y == y2 && z == z2 && worldName.equalsIgnoreCase(worldName2)) {
                Bukkit.broadcastMessage("FOUND");

                int newlockCount = lockedBlocks--;
                List<String> lockingPlayers = new ArrayList<>();

                if(playerData.isSet("LockChest.LockingPlayers")){
                    lockingPlayers = playerData.getStringList("LockChest.LockingPlayers");
                }

                if(newlockCount == 0){
                    lockingPlayers.remove(p.getName());
                    playerData.set("LockChest.LockingPlayers", lockingPlayers);

                }
                playerData.set("LockChest." + p.getName() + ".BlockLockCount", lockedBlocks--);
                playerData.set("LockChest." + p.getName() + "." + i, null);
                Main.getInstance().getFileManager().saveFiles();
                break;
            }

        }

    }
}
