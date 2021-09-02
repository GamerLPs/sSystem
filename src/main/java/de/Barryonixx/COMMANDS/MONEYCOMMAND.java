package de.Barryonixx.COMMANDS;

import de.Barryonixx.Main;
import de.Barryonixx.vault.Vaultmanager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MONEYCOMMAND implements CommandExecutor, TabCompleter {
    private final String NoPlayerError = "DU BIST KEIN SPIELER";
    private final String PlayerNotFoundError = "§8| §3§lMONEY§8 » §4§oFehler§7§o: Spieler nicht online";
    private final String SendMoneyStatus = "§8| §3§LMONEY §8» §7§oKontostand: §a§o{MONEY}$";
    private final String SendMoneyStatusOtherPlayer = "§8| §3§LMONEY §8» §7§oDer Kontostand von §a§o{TARGET}§7§o: §a§o{TARGET.MONEY}$";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(NoPlayerError);
        }

        Player player = (Player) sender;

        if(args.length == 0){
            player.sendMessage(SendMoneyStatus.replace("{MONEY}","" + Vaultmanager.getMoney(player)));
            return true;
        }

        if(!player.hasPermission("skylydra.money")){
            player.sendMessage(Main.perm);
            return false;
        }

        if(args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);

            if(target == null){
                player.sendMessage(PlayerNotFoundError);
                return false;
            }

            double balance = Vaultmanager.getMoney(target);

            player.sendMessage(SendMoneyStatusOtherPlayer.replace("{TARGET}", target.getName()).replace("{TARGET.MONEY}", "" + balance));
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            Player target = Bukkit.getPlayer(args[1]);

            int amount;

            if(target == null){
                player.sendMessage(PlayerNotFoundError);
                return false;
            }

            try{
                amount = Integer.parseInt(args[2]);
            }catch(NumberFormatException e){
                e.printStackTrace();
                player.sendMessage("§cBitte gebe eine gültige Zahl an!");
                return false;
            }

            Vaultmanager.setMoney(target, amount);
            player.sendMessage("§aDu hast den Kontostand von §6" + target.getName() + " §aauf §6" + amount + " §agesetzt!");
            return true;
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return null;
        if(args.length == 1){
            if(((Player)sender).isOp()){
                List<String> complets = new ArrayList<>();
                complets.add("set");

                for(Player p : Bukkit.getOnlinePlayers()){
                    complets.add(p.getName());
                }

                return complets;
            }
        }
        return null;
    }
}
