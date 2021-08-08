package me.zjls.niclogin.events;

import me.zjls.niclogin.Main;
import me.zjls.niclogin.utils.Color;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.UUID;

public class PlayerJoin implements Listener {

    private Main plugin;

    public PlayerJoin(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent e) {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        plugin.getData().reload();
        FileConfiguration loginConfig = plugin.getData().getLoginConfig();
        UUID uuid = p.getUniqueId();
        if (loginConfig.contains(uuid.toString())) {
            plugin.getPlayerData().getPlayerUsernameMap().put(uuid
                    , loginConfig.getString(uuid + ".username"));
        }

//        Bukkit.broadcastMessage(Color.str("&7[&a+&7] " + p.getName()));
        plugin.getPlayerData().sendLoginMessage(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        plugin.getPlayerData().getLoginPlayers().remove(p.getUniqueId());
        plugin.getPlayerData().getPlayerUsernameMap().remove(p.getUniqueId());
        plugin.getPlayerData().getPlayerPasswordMap().remove(p.getUniqueId());
        plugin.getPlayerData().getEditPasswordPlayers().remove(p.getUniqueId());
        plugin.getPlayerData().getEditUsernamePlayers().remove(p.getUniqueId());
    }
}
