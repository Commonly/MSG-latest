package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import me.jdog.msg.other.network.type.ConnectionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reload implements CommandExecutor {

    private Main plugin;

    public reload(Main pl) {
        this.plugin = pl;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("message")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Nope!");
                return true;
            }
            if (args.length == 0) {
                plugin.MessageAPI(sender, "&bPlease add args! Accepted args: reload | help | motd | sv_version | online");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                this.plugin.reloadConfig();
                plugin.MessageAPI(sender, "&bConfig has been reloaded!");
                return true;
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
