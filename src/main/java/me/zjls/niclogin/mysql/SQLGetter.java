package me.zjls.niclogin.mysql;

import me.zjls.niclogin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {

    private Main plugin;
    private String table = "NicLogin";
    //    private String favorite = "Block#19,Sword#19,Armor#19,Utility#19,Bow#20,Potions#19,Utility#23,Block#24,Sword#20,Armor#20,Utility#22,Bow#19,Potions#20,Utility#25,Glass#19,Glass#19,Glass#19,Glass#19,Glass#19,Glass#19,Glass#19";

    public SQLGetter(Main plugin) {
        this.plugin = plugin;

    }

    public boolean isPlayerExists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.sql.getConnection().prepareStatement("select * from " + table + " where UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setPlayerLogged(Player p, int userID, String username, String password, String token) {
        if (!isPlayerExists(p.getUniqueId())) {
            return;
        }
        UUID uuid = p.getUniqueId();

        PreparedStatement ps;
        try {
            ps = plugin.sql.getConnection().prepareStatement("update " + table + " set UserID=?,Username=?,Password=?,Token=?,IP=?,isLogged=1 where UUID=?");

            ps.setInt(1, userID);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, token);
            ps.setString(5, p.getAddress().getHostString());
            ps.setString(6, uuid.toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
