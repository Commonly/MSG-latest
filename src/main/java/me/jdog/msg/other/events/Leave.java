package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {

    private Main plugin;

    public Leave(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        leaveReply(p);
    }

    private void leaveReply(Player player) {
        plugin.reply.remove(player.getName());
    }

}
