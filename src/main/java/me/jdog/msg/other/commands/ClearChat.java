package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.cmd.CMD;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Muricans on 1/6/17.
 */
public class ClearChat extends CMD {
    private Main main;

    public ClearChat(Main main) {
        super("clearchat");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            clearChat();
            String clearChatMessage = Color.addColor("chat.clearchat-message-noargs", main).replace("%sender%", sender.getName());
            Bukkit.getServer().broadcastMessage(clearChatMessage);
            return true;
        }
        if (!sender.hasPermission("msg.clearchat.reason")) {
            sender.sendMessage("You don't have permission to add a reason.");
            return true;
        }
        String msg = StringUtils.join(args, ' ', 0, args.length);
        clearChat();
        if (!msg.contains("-s")) {
            String clearChatMessage = Color.addColor("chat.clearchat-message", main).replace("%sender%", sender.getName()).replace("%reason%", msg);
            Bukkit.getServer().broadcastMessage(clearChatMessage);
        }
        return false;
    }

    private void clearChat() {
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
            pl.sendMessage("");
        }
    }

}
