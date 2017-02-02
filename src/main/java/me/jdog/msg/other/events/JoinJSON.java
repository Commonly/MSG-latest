package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import me.jdog.murapi.api.Color;
import mkremins.fanciful.FancyMessage;
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
            String welcomeMessage = Color.addColor("json.json-welcome", plugin).replace("%player%", e.getPlayer().getName());
            String hoverMessage = Color.addColor("json.json-hover", plugin).replace("%player%", e.getPlayer().getName());
            String execute = Color.addColor("json.json-command", plugin).replace("%player%", e.getPlayer().getName());
            new FancyMessage(welcomeMessage).tooltip(hoverMessage).suggest(execute).send(e.getPlayer());
        } else {
            return;
        }
    }

}
