package me.zjls.niclogin;

import lombok.Getter;
import me.zjls.niclogin.commands.Login;
import me.zjls.niclogin.events.*;
import me.zjls.niclogin.files.DataManager;
import me.zjls.niclogin.listener.PluginMessage;
import me.zjls.niclogin.mysql.MySQL;
import me.zjls.niclogin.mysql.SQLGetter;
import me.zjls.niclogin.utils.Color;
import me.zjls.niclogin.utils.PlayerData;
import me.zjls.niclogin.utils.Requests;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

@Getter
public final class Main extends JavaPlugin {

    private PlayerData playerData;
    public DataManager data;

    public MySQL sql;
    public SQLGetter sqldata;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.sql = new MySQL(this);
        this.sqldata = new SQLGetter(this);

        getCommand("login").setExecutor(new Login(this));
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(this), this);
        getServer().getPluginManager().registerEvents(new PlayerCommand(this), this);
        getServer().getPluginManager().registerEvents(new BlockUpdate(this),this);
        getServer().getPluginManager().registerEvents(new InventoryEvent(this),this);
        saveDefaultConfig();

        try {
            sql.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            getLogger().info(Color.str("&c数据库连接失败!"));
        }
        if (sql.isConnected()) {
            getLogger().info(Color.str("&a数据库连接成功!"));
        }

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
//        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "NicLogin");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());

        this.playerData = new PlayerData(this);
        this.data = new DataManager(this);
        getLogger().info(Color.str("&a插件已开启"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        sql.disConnect();
        Bukkit.getScheduler().cancelTasks(this);
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        getLogger().info(Color.str("&a插件已关闭"));
    }
}
