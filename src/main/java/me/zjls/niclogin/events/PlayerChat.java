package me.zjls.niclogin.events;

import me.zjls.niclogin.Main;
import me.zjls.niclogin.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerChat implements Listener {

    private Main plugin;

    public PlayerChat(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        if (plugin.getPlayerData().getEditUsernamePlayers().contains(p.getUniqueId())) {
            e.setCancelled(true);
            plugin.getPlayerData().getEditUsernamePlayers().remove(p.getUniqueId());
            if (plugin.getPlayerData().getPlayerUsernameMap().get(p.getUniqueId()) == null) {
                plugin.getPlayerData().getPlayerUsernameMap().put(p.getUniqueId(), message);
            } else {
                plugin.getPlayerData().getPlayerUsernameMap().replace(p.getUniqueId(), message);
            }
            plugin.getPlayerData().sendLoginMessage(p);
        } else if (plugin.getPlayerData().getEditPasswordPlayers().contains(p.getUniqueId())) {
            e.setCancelled(true);
            plugin.getPlayerData().getEditPasswordPlayers().remove(p.getUniqueId());
            if (plugin.getPlayerData().getPlayerPasswordMap().get(p.getUniqueId()) == null) {
                plugin.getPlayerData().getPlayerPasswordMap().put(p.getUniqueId(), message);
            } else {
                plugin.getPlayerData().getPlayerPasswordMap().replace(p.getUniqueId(), message);
            }
            Bukkit.getServer().getScheduler().runTask(plugin, bukkitTask -> p.performCommand("l login " + plugin.getPlayerData().getUsername(p) + " " + plugin.getPlayerData().getPassword(p)));
            plugin.getPlayerData().sendLoginMessage(p);
        } else if (!plugin.getPlayerData().isLogged(p)) {
            e.setCancelled(true);
            p.sendMessage(Color.str("&7[&#d00000✘&7] &#f48c06请先登录！"));
        }

    }
}
