package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinJSON implements Listener {

    private Main plugin;

    public JoinJSON(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (plugin.getConfig().getBoolean("json.use-welcome-message") == true) {
            String welcomeMessage = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("json.json-welcome").replace("%player%", e.getPlayer().getName()));
            String hoverMessage = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("json.json-hover").replace("%player%", e.getPlayer().getName()));
            String execute = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("json.json-command").replace("%player%", e.getPlayer().getName()));
            new FancyMessage(welcomeMessage).tooltip(hoverMessage).suggest(execute).send(e.getPlayer());
        } else {
            return;
        }
    }

}
