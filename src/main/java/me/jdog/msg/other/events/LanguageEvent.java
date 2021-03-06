package me.jdog.msg.other.events;

import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Created by Muricans on 1/5/17.
 */
public class LanguageEvent implements Listener {
    private Plugin main;

    public LanguageEvent(Plugin main) {
        this.main = main;
    }

    // watch yo profanity
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Config log = new Config(main, "log.yml");
        if (!main.getConfig().getBoolean("profanity.enable")) return;
        String before = e.getMessage();
        String msg = before.toLowerCase();
        Player player = e.getPlayer();
        if (player.hasPermission("msg.profanity.bypass")) return;
        List<String> blacklist = main.getConfig().getStringList("profanity.blacklist");
        List<String> whitelist = main.getConfig().getStringList("profanity.whitelist");
        for (String badWord : blacklist) {
            for (String goodWord : whitelist) {
                if (msg.contains(badWord) && msg.contains(goodWord)) {
                    e.setCancelled(false);
                    return;
                }
                if (msg.contains(goodWord) && msg.contains(badWord)) {
                    e.setCancelled(false);
                    return;
                }
                if (msg.contains(goodWord)) {
                    e.setCancelled(false);
                    return;
                }
                if (msg.contains(badWord)) {
                    if (main.getConfig().getBoolean("profanity.block")) {
                        e.setCancelled(true);
                    }
                    List<String> logList = log.getStringList(player.getName());
                    logList.add(before);
                    log.set(player.getName(), logList);
                    log.save();

                    String message = Color.addColor("profanity.warn-message", main);
                    player.sendMessage(message);
                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                        if (pl.hasPermission("msg.profanity.alert")) {
                            String alert = Color.addColor("profanity.alert", main).replace("%player%", player.getName()).replace("%msg%", before);
                            pl.sendMessage(alert);
                        }
                    }
                    return;
                }
            }
        }
    }

}
