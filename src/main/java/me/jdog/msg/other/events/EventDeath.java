package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import me.jdog.murapi.api.Color;
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

            String deathMessage = Color.addColor("death.death-message", plugin).replace("%killer%", k.getName()).replace("%player%", p.getName());

            if (p.isDead()) {
                e.setDeathMessage(null);
                plugin.broadcast(deathMessage);
            }
        }

    }

}
