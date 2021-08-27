package de.Barryonixx.COMMANDS;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class MESSAGECOMMAND implements CommandExecutor {
    public static HashMap<Player, Player> replay = new HashMap();
    static ArrayList<Player> msg = new ArrayList();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            if((args.length == 0) || ((args.length==1) && !args[0].equalsIgnoreCase("toggle"))){
                p.sendMessage("§8| §C§LMSG§8 » §7§o/msg toggle");
                p.sendMessage("§8| §C§LMSG§8 » §7§o/msg <Spieler> <Nachricht>");
            }else if((args.length == 1) && (args[0].equalsIgnoreCase("toggle"))){
                if(msg.contains(p)){
                    msg.remove(p);
                    p.sendMessage("§8| §C§LMSG§8 » §7§oPrivat-Nachricht: §a§oaktiviert");
                }else{
                    msg.add(p);
                    p.sendMessage("§8| §C§LMSG§8 » §7§oPrivat-Nachricht: §c§odeaktiviert");
                }
            } else if(args.length >= 1){
                Player t = Bukkit.getPlayer(args[0]);
                if(t != null){
                    if(t != p){
                        if(!msg.contains(t)) {
                            StringBuilder sb = new StringBuilder();
                            int i = 1;
                            while (i < args.length) {
                                sb.append(args[i]+ " ");
                                i++;
                            }
                            String message = sb.toString();
                            p.sendMessage("§8| §C§LMSG§8 » §4§o" + t.getName() + ": §e§o"+msg);
                            t.sendMessage("§8| §C§LMSG§8 » §4§o" + p.getName() + ": §e§o"+msg);
                            replay.put(t, p);
                            replay.put(p, t);
                        }else{
                            p.sendMessage("§8| §C§LMSG§8 » §7§oHat seine Privat-Nachricht deaktiviert");
                        }
                    }else p.sendMessage("§8| §C§LMSG§8 » §4§oFehler§7§o: Du kannst dir keine Nachricht schreiben");
                }else {
                    p.sendMessage("§8| §C§LMSG§8 » §4§oFehler§7§o: Spieler nicht online");
                }
            }
        }else{
            sender.sendMessage("DU BIST KEIN SPIELER");
        }
        return false;
    }
}
