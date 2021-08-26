package de.Barryonixx.COMMANDS;

import de.Barryonixx.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CLEARCHATCOMMAND implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(command.getName().equalsIgnoreCase("clearchat")){
                if(player.hasPermission("system.clearchat")){
                    for (int i = 0; i < 100; i++){
                        Bukkit.broadcastMessage("");
                    }
                    Bukkit.broadcastMessage("§8| §c§lClearChat§8 » §7Chat wurde von §e"+player.getName()+ " §7gesäubert.");

                }else{
                    player.sendMessage(Main.perm);
                }
            }
        }else{
            sender.sendMessage("DU BIST KEIN SPIELER");
        }
        return false;
    }
}
