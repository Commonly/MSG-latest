package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventMessage implements Listener {

    Main plugin;

    public EventMessage(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onMessage(EventMessageHandler e) {

    }

}
