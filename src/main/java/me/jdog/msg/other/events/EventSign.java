package me.jdog.msg.other.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Muricans on 11/20/16.
 */
public class EventSign implements Listener {

    @EventHandler
    public void onSignCreate(SignChangeEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("msg.sign.create")) {
            if (e.getLine(0).equalsIgnoreCase("[sc]") || e.getLine(0).equalsIgnoreCase("[staffchat]")) {
                e.setLine(0, "§8[§cStaffChat§8]");
                e.setLine(1, "§aClick to toggle");
                e.setLine(2, "§aStaffChat");
            }
        }
    }

}
