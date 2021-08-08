package me.zjls.niclogin.files;

import lombok.Getter;
import me.zjls.niclogin.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Getter
public class DataManager {

    private Main plugin;
    private File loginFile = null;
    private FileConfiguration loginConfig = null;

    public DataManager(Main plugin) {
        this.plugin = plugin;
        saveDefault();
    }

    public void reload() {
        if (loginFile == null) {
            loginFile = new File(plugin.getDataFolder(), "login.yml");
        }
        loginConfig = YamlConfiguration.loadConfiguration(loginFile);

        InputStream defaultStream = plugin.getResource("login.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            loginConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getLoginConfig() {
        if (loginConfig == null) {
            reload();
        }
        return loginConfig;
    }

    public void saveLoginConfig() {
        if (loginFile == null || loginConfig == null) {
            return;
        }
        try {
            getLoginConfig().save(loginFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        if (loginFile == null) {
            loginFile = new File(plugin.getDataFolder(), "login.yml");
        }
        if (!loginFile.exists()) {
            plugin.saveResource("login.yml", false);
        }
    }
}
