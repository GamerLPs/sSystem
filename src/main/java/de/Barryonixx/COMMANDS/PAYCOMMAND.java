package de.Barryonixx.COMMANDS;

import de.Barryonixx.vault.Vaultmanager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PAYCOMMAND implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("DU BIST KEIN SPIELER");
            return false;
        }

        Player player = (Player) sender;

        if(!(args.length == 2)){
            player.sendMessage("§8| §e§lPAY§8 » §7§o/pay <Spieler> <Menge>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null){
            player.sendMessage("§8| §e§lPAY§8 » §4§oFehler§7§o: Spieler nicht online");
            return false;
        }
        int amount;

        try{
            amount = Integer.parseInt(args[1]);
        }catch(NumberFormatException e){
            player.sendMessage("§8| §e§lPAY§8 » §4§oFehler§7§o: Du musst eine Zahl eingeben");
            return false;
        }

        Vaultmanager.addMoney(target, amount);
        Vaultmanager.removeMoney(player, amount);

        player.sendMessage("§8| §e§lPAY§8 » §7§oDu hast §a§o"+target.getName()+ "§7§o, " +amount + " $ §7§ogegeben.");
        target.sendMessage("§8| §e§lPAY§8 » §7§oDu hast von §a§o"+ player.getName() + "§7§o, " + amount+ " $ §7§obekommen.");
        return true;

    }
}
