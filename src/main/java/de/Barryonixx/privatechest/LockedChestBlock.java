package de.Barryonixx.privatechest;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

public class LockedChestBlock {
    private String playerName;
    private Block chestBlock;
    private Block signBlock;

    public LockedChestBlock(Player player, Block chestBlock, Block signBlock) {
        this.playerName = player.getName();
        this.chestBlock = chestBlock;
        this.signBlock = signBlock;
    }

    public LockedChestBlock(String playerName, Block chestBlock, Block signBlock){
        this.playerName = playerName;
        this.chestBlock = chestBlock;
        this.signBlock = signBlock;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Block getChestBlock() {
        return chestBlock;
    }

    public void setChestBlock(Block chestBlock) {
        this.chestBlock = chestBlock;
    }

    public Block getSignBlock() {
        return signBlock;
    }

    public void setSignBlock(Block signBlock) {
        this.signBlock = signBlock;
    }
}
