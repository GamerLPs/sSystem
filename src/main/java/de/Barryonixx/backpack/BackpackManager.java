package de.Barryonixx.backpack;

import de.Barryonixx.Main;
import de.Barryonixx.utils.FileManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

public class BackpackManager {

    private final Map<UUID, Backpack> map;

    public BackpackManager() {
        map = new HashMap<>();

        load();
    }

    public Backpack getBackpack(UUID uuid) {
        if(map.containsKey(uuid)) {
            return map.get(uuid);
        }

        Backpack backpack = new Backpack(uuid);
        map.put(uuid, backpack);
        return backpack;
    }

    public void setBackpack(UUID uuid, Backpack backpack) {
        map.put(uuid, backpack);
    }

    private void load() {
        YamlConfiguration cfg = Main.getInstance().getFileManager().getCfg();


        List<String> uuids = cfg.getStringList("backpacks");

        uuids.forEach(s -> {
            UUID uuid = UUID.fromString(s);
            String base64 = cfg.getString("backpack." + s);

            try {
                map.put(uuid, new Backpack(uuid, base64));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void save() {
        YamlConfiguration cfg = Main.getInstance().getFileManager().getCfg();


        List<String> uuids = new ArrayList<>();

        for (UUID uuid : map.keySet()) {
            uuids.add(uuid.toString());
        }

        cfg.set("backpacks", uuids);
        map.forEach((uuid, backpack) -> cfg.set("backpack." + uuid.toString(), backpack.toBase64()));

        Main.getInstance().getFileManager().saveFiles();
    }
}