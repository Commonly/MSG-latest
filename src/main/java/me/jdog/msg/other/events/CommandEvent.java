package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import me.jdog.murapi.api.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

/**
 * Created by Muricans on 1/17/17.
 */
public class CommandEvent implements Listener {

    private Main main;

    public CommandEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (main.getConfig().getBoolean("command-blocking.enable")) {
            if (!e.getPlayer().hasPermission("msg.command-blocking.bypass")) {
                String command = e.getMessage();
                List<String> cmds = main.getConfig().getStringList("command-blocking.blacklist");
                for (String cmd : cmds) {
                    String commandCon = command.toUpperCase();
                    if (commandCon.startsWith("/" + cmd.toUpperCase())) {
                        e.getPlayer().sendMessage(Color.addColor("command-blocking.warn-message", main));
                        e.setCancelled(true);
                        if (main.getConfig().getBoolean("command-blocking.alert")) {
                            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                                if (pl.hasPermission("msg.command-blocking.alert")) {
                                    pl.sendMessage(Color.addColor("command-blocking.alert-message", main).replace("%player%", e.getPlayer().getName()).replace("%msg%", command));
                                }
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
}
