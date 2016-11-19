package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import me.jdog.msg.other.commands.Options;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    Main plugin;

    public Join(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!plugin.dataManager.getData().isSet("auto"))
            return;
        Player player = e.getPlayer();
        if (Options.autoStaff.contains(e.getPlayer().getUniqueId().toString())) {
            e.getPlayer().performCommand("sc");
            plugin.MessageAPI(e.getPlayer(), "&cYou were automaticly put in staffchat!");
        }

    }

}
