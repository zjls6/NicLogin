package me.zjls.niclogin.events;

import me.zjls.niclogin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryEvent implements Listener {

    private Main plugin;

    public InventoryEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();
        if (plugin.getPlayerData().isLogged(p)) {
            return;
        }
        e.setCancelled(true);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (plugin.getPlayerData().isLogged(p)) {
            return;
        }
        e.setCancelled(true);
    }
}
