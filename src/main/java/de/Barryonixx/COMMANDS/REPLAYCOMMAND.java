package de.Barryonixx.COMMANDS;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class REPLAYCOMMAND implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player)sender;
        Player t = (Player) MESSAGECOMMAND.replay.get(p);

        if(t != null){
            if(!MESSAGECOMMAND.msg.contains(t)){
                StringBuilder sb = new StringBuilder();
                int i = 0;
                while (i < args.length){
                    sb.append(String.valueOf(args[i] + " "));
                    i++;
                }
                String message = sb.toString();
                p.sendMessage("§8| §C§LMSG§8 » §4§o" + t.getName() + ": §e§o"+message);
                t.sendMessage("§8| §C§LMSG§8 » §4§o" + p.getName() + ": §e§o"+message);
                MESSAGECOMMAND.replay.put(t, p);
            }else{
                p.sendMessage("§8| §C§LMSG§8 » §7§oHat seine Privat-Nachricht deaktiviert");
            }
        } else p.sendMessage("§8| §C§LMSG§8 » §4§oFehler§7§o: Keine weiterführende Nachricht");
        return false;
    }
}
