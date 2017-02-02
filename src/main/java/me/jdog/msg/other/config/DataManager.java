package me.jdog.msg.other.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;

public class DataManager {

    static DataManager instance = new DataManager();
    private Plugin p;
    private FileConfiguration data;
    private File dfile;

    private DataManager() {
    }

    public static DataManager getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        this.dfile = new File(p.getDataFolder(), "data.yml");
        if (!this.dfile.exists()) {
            try {
                this.dfile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
        }
        this.data = YamlConfiguration.loadConfiguration(this.dfile);
    }

    public FileConfiguration getData() {
        return this.data;
    }

    public void saveData() {
        try {
            this.data.save(this.dfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
        }
    }

    public void reloadData() {
        this.data = YamlConfiguration.loadConfiguration(this.dfile);
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }
}
