package de.Barryonixx.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vaultmanager {
    private static Economy economy;

    public Vaultmanager(){
        boolean eco = false;

        if(economy == null){
             eco = setupEconomy();
        }

        if(!eco){
            System.out.println("Fehler beim laden von Vault!");
        }else{
            System.out.println("Vault erfolgreich geladen!");
        }

    }

    public static void addMoney(Player player, int amount){
        if(economy == null) return;
        economy.depositPlayer(player, amount);
    }

    public static void removeMoney(Player player, int amount){
        if(economy == null) return;
        economy.withdrawPlayer(player, amount);
    }

    @Deprecated
    public static void setMoney(Player player, int amount){
        if(economy == null) return;
        removeMoney(player, (int) getMoney(player));

        addMoney(player, amount);
    }

    public static double getMoney(Player player){
        if(economy == null) return 0;
        return economy.getBalance(player);
    }

    public Economy getEconomy() {
        return economy;
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
