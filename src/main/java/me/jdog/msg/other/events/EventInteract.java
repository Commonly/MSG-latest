package me.jdog.msg.other.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Muricans on 11/20/16.
 */
public class EventInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!(e.getAction() == Action.RIGHT_CLICK_BLOCK))
            return;
        if(e.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) e.getClickedBlock().getState();
            if(sign.getLine(0).equalsIgnoreCase("§8[§cStaffChat§8]") && e.getPlayer().hasPermission("msg.sign.interact"))
                e.getPlayer().performCommand("sc");
        }
    }

}
