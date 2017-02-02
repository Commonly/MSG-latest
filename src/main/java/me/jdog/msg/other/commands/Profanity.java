package me.jdog.msg.other.commands;

import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.cmd.CMD;
import me.jdog.murapi.api.config.Config;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Created by Muricans on 1/5/17.
 */
public class Profanity extends CMD {
    private Plugin main;

    public Profanity(Plugin main) {
        super("profanity");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Color.addColor("&cPlease add arguments: lookup | blacklist | whitelist"));
            return true;
        }
        String args0 = args[0].toLowerCase();

        switch (args0) {
            case "lookup":
                if (sender.hasPermission("msg.profanity.lookup")) {
                    if (args.length == 1) {
                        sender.sendMessage(Color.addColor("&cPlease add a player to lookup."));
                        return true;
                    }
                    Config log = new Config(main, "log.yml");
                    List<String> logList = log.getStringList(args[1]);
                    sender.sendMessage(Color.addColor("&aLog for @" + args[1]));
                    for (String words : logList) {
                        sender.sendMessage(Color.addColor("    &c- &f" + words));
                    }
                }
                break;
            case "blacklist":
                if (sender.hasPermission("msg.profanity.blacklist")) {
                    if (args.length == 1) {
                        sender.sendMessage("Please add a word to blacklist.");
                        return true;
                    }
                    List<String> blacklist = main.getConfig().getStringList("profanity.blacklist");
                    String msg = StringUtils.join(args, ' ', 1, args.length);
                    blacklist.add(msg);
                    main.getConfig().set("profanity.blacklist", blacklist);
                    main.saveConfig();
                }
                break;
            case "whitelist":
                if (sender.hasPermission("msg.profanity.whitelist")) {
                    if (args.length == 1) {
                        sender.sendMessage("Please add a word to whitelist.");
                        return true;
                    }
                    List<String> whitelist = main.getConfig().getStringList("profanity.whitelist");
                    String msg2 = StringUtils.join(args, ' ', 1, args.length);
                    whitelist.add(msg2);
                    main.getConfig().set("profanity.whitelist", whitelist);
                    main.saveConfig();
                }
                break;
        }
        return false;
    }
}
