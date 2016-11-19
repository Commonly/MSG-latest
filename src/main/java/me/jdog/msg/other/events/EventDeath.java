package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Muricans on 11/16/16.
 */
public class EventDeath implements Listener {

    private Main plugin;

    public EventDeath(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player k = p.getKiller();

        if (!plugin.getConfig().getBoolean("death.use") == true)
            return;

        if (p instanceof Player && k instanceof Player) {

            String deathMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("death.death-message").replace("%killer%", k.getName()).replace("%player%", p.getName()));

            if (p.isDead()) {
                e.setDeathMessage(null);
                plugin.broadcast(deathMessage);
            }
        }

    }

}
