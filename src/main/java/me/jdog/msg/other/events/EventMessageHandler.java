package me.jdog.msg.other.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * A custom event that will be used later on.
 *
 * @author Muricans
 */

public class EventMessageHandler extends Event {

    // later updates...

    private static HandlerList handlers = new HandlerList();

    private Player p;
    private CommandSender sender;

    public EventMessageHandler(Player p) {
        this.p = p;
    }

    public EventMessageHandler(CommandSender sender) {
        this.sender = sender;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return p;
    }

    public CommandSender getSender() {
        return sender;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

}
