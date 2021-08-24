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
        * So jetzt... sagen wir du codest jetzt hier den Commentar raus...
        * Wenn du fertig bist, gehst du oben rechts auf den grünen hacken (Commit)
        *
        * Dann oben links alle Datein auswählen...also mit dem hacken
        * Dann da wo jetzt Commit message steht, kannst du schreiben was du gemacht hast...Am besten kurz
        * Und dann einfach auf Commit and Push
        * Wenn es fehler gibt, liegt das daran, weil intellij sagt, das der Javacode nicht gut aussieht...
        * einfach auf Commit und Push nochmal klicken und dann auf Commit  & Push anyway
        *
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
