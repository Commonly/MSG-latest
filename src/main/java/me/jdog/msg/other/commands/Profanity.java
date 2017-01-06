package me.jdog.msg.other.commands;

import me.jdog.msg.other.events.LanguageEvent;
import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.cmd.CMD;
import me.jdog.murapi.api.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Muricans on 1/5/17.
 */
public class Profanity extends CMD {

    public Profanity() {
        super("profanity");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Color.addColor("&cPlease add a player to lookup."));
            return true;
        }
        try {
            Config log = LanguageEvent.log;
            List<String> logList = log.getStringList(args[0]);
            sender.sendMessage(Color.addColor("&aLog for @" + args[0]));
            for (String words : logList) {
                sender.sendMessage(Color.addColor("    &c- &f" + words));
            }
        } catch (Exception e) {
            sender.sendMessage(Color.addColor("&aCouldn't find " + args[0] + " on the list, please try a different player."));
        }
        return false;
    }
}
