package me.zjls.niclogin.mysql;

import lombok.Getter;
import me.zjls.niclogin.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class MySQL {

    private String host = "localhost";
    private int port = 3306;
    private String database = "minecraft";
    private String username = "root";
    private String password = "Whitewook666";
    private boolean useSSL = false;

    private Connection connection;

    private Main plugin;

    public MySQL(Main plugin) {
        this.plugin = plugin;
        this.host = plugin.getConfig().getString("mysql.host");
        this.port = plugin.getConfig().getInt("mysql.port");
        this.database = plugin.getConfig().getString("mysql.database");
        this.username = plugin.getConfig().getString("mysql.username");
        this.password = plugin.getConfig().getString("mysql.password");
        this.useSSL = plugin.getConfig().getBoolean("mysql.useSSL");
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL="
                    + useSSL + "&autoReconnect=true", username, password);
        }
    }

    public void disConnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
