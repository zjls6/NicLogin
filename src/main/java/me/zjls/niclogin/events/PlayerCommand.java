package me.zjls.niclogin.events;

import me.zjls.niclogin.Main;
import me.zjls.niclogin.utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommand implements Listener {

    private Main plugin;

    public PlayerCommand(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (plugin.getPlayerData().isLogged(p)) {
            return;
        }
        String message = e.getMessage();

        if (message.startsWith("/l") || message.startsWith("/login") || message.startsWith("/L")) {

        }else {
            e.setCancelled(true);
            p.sendMessage(Color.str("&7[&#d00000✘&7] &#f48c06请先登录！"));
        }
    }
}
