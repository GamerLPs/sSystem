package de.Barryonixx;

import de.Barryonixx.COMMANDS.*;
import de.Barryonixx.backpack.BackpackListener;
import de.Barryonixx.backpack.BackpackManager;
import de.Barryonixx.backpack.GiveBackpack;
import de.Barryonixx.event.Eventlistener;
import de.Barryonixx.item.ItemManager;
import de.Barryonixx.privatechest.Chestlistener;
import de.Barryonixx.privatechest.Chestmanager;
import de.Barryonixx.utils.FileManager;
import de.Barryonixx.vault.Vaultmanager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    private Vaultmanager vaultmanager;
    private Chestlistener chestlistener;
    private ItemManager itemManager;
    private Chestmanager chestmanager;
    private FileManager fileManager;
    private BackpackManager backpackManager;
    public static String perm = "§c<§4§l✘§c> §7Keine Permission §c<§4§l✘§c>";

    @Override
    public void onEnable() {
        instance = this;
        //COMMANDS
        this.fileManager = new FileManager();
        this.chestlistener = new Chestlistener(); //IMPORTANT: Chestlistener erst nach Filemanager laden!
        this.backpackManager = new BackpackManager();
        this.vaultmanager = new Vaultmanager();
        this.itemManager = new ItemManager();
        this.chestmanager = new Chestmanager();

        getCommand("message").setExecutor(new MESSAGECOMMAND());
        getCommand("replay").setExecutor(new REPLAYCOMMAND());
        getCommand("wetter").setExecutor(new WETTERCOMMAND());
        getCommand("givebackpack").setExecutor(new GiveBackpack());
        getCommand("gamemode").setExecutor(new GAMEMODECOMMAND());
        getCommand("money").setExecutor(new MONEYCOMMAND());
        getCommand("money").setTabCompleter(new MONEYCOMMAND());
        getCommand("pay").setExecutor(new PAYCOMMAND());
        getCommand("clearchat").setExecutor(new CLEARCHATCOMMAND());


        //LISTENERS
        PluginManager PM = Bukkit.getPluginManager();
        PM.registerEvents(new WETTERCOMMAND(), this);
        PM.registerEvents(new BackpackListener(), this);
        PM.registerEvents(chestlistener, this);
        PM.registerEvents(new Eventlistener(), this);


        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§aDas System ist geladen.");
        Bukkit.getConsoleSender().sendMessage("");
    }

    @Override
    public void onDisable() {
        this.chestmanager.save();
        this.fileManager.saveFiles();
        backpackManager.save();

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§aDas System ist nicht geladen.");
        Bukkit.getConsoleSender().sendMessage("");
    }

    public BackpackManager getBackpackManager() {
        return backpackManager;
    }

    public static Main getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public Chestlistener getChestlistener() {
        return chestlistener;
    }

    public Chestmanager getChestmanager() {
        return chestmanager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
