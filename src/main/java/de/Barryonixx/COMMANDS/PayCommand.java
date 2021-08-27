package de.Barryonixx.COMMANDS;

import de.Barryonixx.vault.Vaultmanager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("§cDu musst diesen Command als Spieler ausführen!");
            return false;
        }

        Player player = (Player) sender;

        if(!(args.length == 2)){
            player.sendMessage("§cBitte benutze §6/pay SPIELER ANZAHL");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null){
            player.sendMessage("§cDer Spieler wurde nicht gefunden!");
            return false;
        }

        if(target == player){
            player.sendMessage("§cDu kannst nicht an dich selbst überweisen!");
            return false;
        }

        int amount;

        try{
            amount = Integer.parseInt(args[1]);
        }catch(NumberFormatException e){
            player.sendMessage("§cBitte gebe eine gültige Zahl an!");
            return false;
        }

        if(amount <= 0){
            player.sendMessage("§cBitte gebe eine gültige Zahl an!");
            return false;
        }

        if(Vaultmanager.getMoney(player) < amount){
            player.sendMessage("§cDu hast nicht soviel Geld!");
            return false;
        }

        Vaultmanager.addMoney(target, amount);
        Vaultmanager.removeMoney(player, amount);

        player.sendMessage("§aDu hast das Geld erfolgreich überwiesen!");
        target.sendMessage("§6"+ player.getName() + " §ahat dir §6" + amount + " §6$ §aüberwiesen!");
        return true;

    }
}
