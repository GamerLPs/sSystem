package de.Barryonixx;

import de.Barryonixx.COMMANDS.CLEARCHATCOMMAND;
import de.Barryonixx.COMMANDS.GAMEMODECOMMAND;
import de.Barryonixx.COMMANDS.PAYCOMMAND;
import de.Barryonixx.COMMANDS.MESSAGECOMMAND;
import de.Barryonixx.COMMANDS.WETTERCOMMAND;
import de.Barryonixx.vault.Vaultmanager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private Vaultmanager vaultmanager;
    public static String perm = "§c<§4§l✘§c> §7Keine Permission §c<§4§l✘§c>";

    @Override
    public void onEnable() {
        //COMMANDS
        this.vaultmanager = new Vaultmanager();

        getCommand("message").setExecutor(new MESSAGECOMMAND());
        getCommand("wetter").setExecutor(new WETTERCOMMAND());
        getCommand("gamemode").setExecutor(new GAMEMODECOMMAND());
        //getCommand("money").setExecutor(new MoneyCommand());
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
}
