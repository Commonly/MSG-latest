package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import me.jdog.msg.other.api.fetch.UUIDFetcher;
import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.config.Config;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Muricans on 1/19/17.
 */
public class StaffList implements CommandExecutor {

    private Main main;

    public StaffList(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Config staffList = new Config(main, "stafflist.yml");
        if (args.length == 0) {
            sender.sendMessage(Color.addColor("&e[MSG] &cStaff > &aPlease add arguments"));
            sender.sendMessage(Color.addColor("/stafflist >"));
            sender.sendMessage("create <groupName> <groupFormat>");
            sender.sendMessage("set <player> <groupName>");
            sender.sendMessage("format <groupName> <newGroupFormat>");
            return true;
        }
        if (args[0].equalsIgnoreCase("create")) {
            if (args.length == 1 || args.length == 2) {
                sender.sendMessage(Color.addColor("&e[MSG] &cStaff > &aMissing arguments : groupName and groupFormat"));
                sender.sendMessage("Example: /stafflist create admin &c&lAdmin %player%");
                return true;
            }
            String arg1 = args[1];
            String arg2 = StringUtils.join(args, ' ', 2, args.length);
            if (arg1 != null && arg2 != null) {
                staffList.set("groups." + arg1 + ".groupFormat", arg2);
                sender.sendMessage(Color.addColor("&aGroup '" + arg1.toUpperCase() + "' has been created with the format of " + arg2));
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 1 || args.length == 2) {
                sender.sendMessage(Color.addColor("&e[MSG] &cStaff > &aMissing arguments : player and groupName"));
                sender.sendMessage("Example: /stafflist set Muricans admin");
                return true;
            }
            String arg1 = args[1];
            String arg2 = args[2];
            String uuid = new UUIDFetcher(arg1).getUUIDAsString();
            if (arg1 != null && arg2 != null) {
                if (staffList.getFile().contains("groups." + arg2)) {
                    staffList.set("data." + uuid + ".group", arg2);
                    sender.sendMessage(Color.addColor("&a" + arg1.toUpperCase() + " group has been set to " + arg2.toUpperCase()));
                }
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("format")) {
            if (args.length == 1 || args.length == 2) {
                sender.sendMessage(Color.addColor("&e[MSG] &cStaff > &aMissing arguments : groupName and newGroupFormat"));
                sender.sendMessage("Example: /stafflist format admin &c&lAdmin %player%");
                return true;
            }
            String arg1 = args[1];
            String arg2 = StringUtils.join(args, ' ', 2, args.length);
            if (arg1 != null && arg2 != null) {
                if (staffList.getFile().contains("groups." + arg1)) {
                    staffList.set("groups." + arg1 + ".groupFormat", arg2);
                    sender.sendMessage(Color.addColor("&a'" + arg1.toUpperCase() + "' format has been set to " + arg2));
                }
                return true;
            }
        }
        return false;
    }

}
