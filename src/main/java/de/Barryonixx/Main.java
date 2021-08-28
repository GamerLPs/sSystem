package de.Barryonixx;

import de.Barryonixx.COMMANDS.*;
import de.Barryonixx.utils.FileManager;
import de.Barryonixx.vault.Vaultmanager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private Vaultmanager vaultmanager;
    private FileManager fileManager;
    public static String perm = "§c<§4§l✘§c> §7Keine Permission §c<§4§l✘§c>";

    @Override
    public void onEnable() {
        //COMMANDS
        this.fileManager = new FileManager();
        this.vaultmanager = new Vaultmanager();

        getCommand("message").setExecutor(new MESSAGECOMMAND());
        getCommand("replay").setExecutor(new REPLAYCOMMAND());
        getCommand("wetter").setExecutor(new WETTERCOMMAND());
        getCommand("gamemode").setExecutor(new GAMEMODECOMMAND());
        getCommand("money").setExecutor(new MONEYCOMMAND());
        getCommand("money").setTabCompleter(new MONEYCOMMAND());
        getCommand("pay").setExecutor(new PAYCOMMAND());
        getCommand("clearchat").setExecutor(new CLEARCHATCOMMAND());


        //LISTENERS
        PluginManager PM = Bukkit.getPluginManager();
        PM.registerEvents(new WETTERCOMMAND(), this);


        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§aDas System ist geladen.");
        Bukkit.getConsoleSender().sendMessage("");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§aDas System ist nicht geladen.");
        Bukkit.getConsoleSender().sendMessage("");
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
