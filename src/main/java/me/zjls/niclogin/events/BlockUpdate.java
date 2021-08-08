package me.zjls.niclogin.events;

import me.zjls.niclogin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockUpdate implements Listener {

    private Main plugin;

    public BlockUpdate(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (plugin.getPlayerData().isLogged(p) && p.hasPermission("niclogin.admin")) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (plugin.getPlayerData().isLogged(p) && p.hasPermission("niclogin.admin")) {
            return;
        }
        e.setCancelled(true);
    }
}

