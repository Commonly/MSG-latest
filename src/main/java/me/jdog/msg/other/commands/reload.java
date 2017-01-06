package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import me.jdog.msg.other.Updater;
import me.jdog.msg.other.network.type.ConnectionType;
import me.jdog.murapi.api.Color;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class reload implements CommandExecutor {

    private Main plugin;

    public reload(Main pl) {
        this.plugin = pl;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("message")) {
            if (args.length == 0) {
                plugin.MessageAPI(sender, "&bPlease add args! Accepted args: reload | help | motd | sv_version | online | update | addword");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                this.plugin.reloadConfig();
                plugin.MessageAPI(sender, "&bConfig has been reloaded!");
                return true;
            }

            if(args[0].equalsIgnoreCase("addword")) {
                if(args.length == 1) {
                    sender.sendMessage(Color.addColor("&cPlease enter a sentence to add!"));
                    return true;
                }
                List<String> words = plugin.getConfig().getStringList("replacechat.words");
                String word = StringUtils.join(args, ' ', 1, args.length);
                words.add(word);
                plugin.getConfig().set("replacechat.words", words);
                plugin.saveConfig();
                sender.sendMessage(Color.addColor("&aAdded word &b'" + word + "'"));
                return true;
            }

            if (args[0].equalsIgnoreCase("update")) {
                sender.sendMessage(Color.addColor("&eChecking for new updates..."));
                Object[] updates = Updater.getLastUpdate();
                if(updates.length == 2) {
                    sender.sendMessage("====================================");
                    sender.sendMessage(Color.addColor("&cMSG >>"));
                    sender.sendMessage(Color.addColor("&eNew update found. &cINFO:"));
                    sender.sendMessage(Color.addColor("&aNew version: " + updates[0]));
                    sender.sendMessage(Color.addColor("&aYour current version: " + plugin.getDescription().getVersion()));
                    sender.sendMessage(Color.addColor("&aNew: " + updates[1]));
                    sender.sendMessage(Color.addColor("&dDownload: &rhttps://www.spigotmc.org/resources/msg-tested-on-1-8-1-7-10-1-10.31708/updates"));
                    sender.sendMessage("====================================");
                } else {
                    sender.sendMessage(Color.addColor("&eNo updates found! You're all up to date."));
                }
            }

            if (args[0].equalsIgnoreCase("help")) {
                plugin.MessageAPI(sender, "&b&m---------------&r&aMSG&b&m---------------");
                plugin.MessageAPI(sender, "&c/msg (aliases: m, t, tell)");
                plugin.MessageAPI(sender, "&c/reply (aliases: r)");
                plugin.MessageAPI(sender, "&c/mhelp - Display message help.");
                plugin.MessageAPI(sender, "&c/staffchat - Enter staffchat room.");
                plugin.MessageAPI(sender, "&c/mpanel - Message panel");
                plugin.MessageAPI(sender, "&b&m---------------&r&aMSG&b&m---------------");
                return true;
            }

            if (args[0].equalsIgnoreCase("motd")) {
                plugin.MessageAPI(sender, "&bServer MOTD: " + plugin.serverUtils.getDataType(ConnectionType.SV_MOTD));
                return true;
            }

            if (args[0].equalsIgnoreCase("sv_version")) {
                plugin.MessageAPI(sender, "&bServer version: " + plugin.serverUtils.getDataType(ConnectionType.SV_VERSION));
                return true;
            }

            if (args[0].equalsIgnoreCase("online")) {
                plugin.MessageAPI(sender, "&bPlayers online: " + plugin.serverUtils.getDataType(ConnectionType.SV_ONLINE));
                return true;
            }
        }
        return true;
    }
}
