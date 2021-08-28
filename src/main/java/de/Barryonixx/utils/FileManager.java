package de.Barryonixx.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private File FOLDER, configFile, playerDataFile;

    private YamlConfiguration cfg, playerData;

    public FileManager(){
        this.FOLDER = new File("./plugins/skylydra/");
        this.configFile = new File(FOLDER, "config.yml");
        this.playerDataFile = new File(FOLDER, "playerData.yml");

        setupFiles();
    }

    public void saveFiles(){
        try{
            cfg.save(configFile);
            playerData.save(playerDataFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public YamlConfiguration getCfg() {
        return cfg;
    }

    public YamlConfiguration getPlayerData() {
        return playerData;
    }

    private void setupFiles(){
        try{
            if(!FOLDER.exists()) FOLDER.mkdirs();
            if(!configFile.exists()) configFile.createNewFile();
            if(!playerDataFile.exists()) playerDataFile.createNewFile();

            this.cfg = YamlConfiguration.loadConfiguration(configFile);
            this.playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
