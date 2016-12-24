package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import me.jdog.msg.other.commands.SocialSpy;
import me.jdog.murapi.api.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Muricans on 11/22/16.
 */
public class EventSpy implements Listener {
    private Main plugin;

    public EventSpy(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onChat(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();
        Player p = e.getPlayer();
        if (msg.startsWith("/msg") || msg.startsWith("/reply") || msg.startsWith("/r") || msg.startsWith("/m") || msg.startsWith("/t") || msg.startsWith("/tell")) {
            String format = Color.addColor("socialspy.format", plugin).replace("%sender%", p.getName()).replace("%msg%", msg);
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                if (pl.hasPermission("msg.socialspy")) {
                    if (SocialSpy.ss.contains(pl.getName())) {
                        pl.sendMessage(format);
                    }
                }
            }
        }
    }
}
