package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.cmd.CMD;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Muricans on 1/2/17.
 */
public class ReplaceChat extends CMD {
    private static HashMap<String, Boolean> replaceChat = new HashMap<>();

    public ReplaceChat() {
        super("replacechat");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            String invalidUsage = Color.addColor("replacechat.invalid-usage", Main.getInstance());
            sender.sendMessage(invalidUsage);
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null || !target.isOnline()) {
            String offline = Color.addColor("replacechat.offline-player", Main.getInstance()).replace("%target%", args[0]);
            sender.sendMessage(offline);
            return true;
        }
        if(replaceChat.containsKey(target.getName())) {
            String toggledOff = Color.addColor("replacechat.toggled-off", Main.getInstance()).replace("%target%", target.getName());
            sender.sendMessage(toggledOff);
            replaceChat.remove(target.getName());
        } else if (!replaceChat.containsKey(target.getName())) {
            String toggledOn = Color.addColor("replacechat.toggled-on", Main.getInstance()).replace("%target%", target.getName());
            sender.sendMessage(toggledOn);
            replaceChat.put(target.getName(), true);
        }
        return false;
    }

    public static HashMap<String, Boolean> getReplaceChat() {
        return replaceChat;
    }

}
