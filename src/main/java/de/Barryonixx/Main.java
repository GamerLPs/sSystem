package de.Barryonixx;

import de.Barryonixx.COMMANDS.GAMEMODECOMMAND;
import de.Barryonixx.COMMANDS.WETTERCOMMAND;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static String perm = "§c<§4§l✘§c> §7Keine Permission §c<§4§l✘§c>";

    @Override
    public void onEnable() {
        //COMMANDS
        getCommand("wetter").setExecutor(new WETTERCOMMAND());
        getCommand("gamemode").setExecutor(new GAMEMODECOMMAND());

        /*
        * Jetzt gibts aber 2 arten wie du weiterprogrammierst...
        *
        * 1.Du machst ganz normal weiter (recht einfach)
        *
        * oder
        *
        * 2.Du machst einzelne "updates"deines Plugins (etwas komplizierter aber eleganter)
        * Entscheide dich XD
        *
        * okay gleich
        * */


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
