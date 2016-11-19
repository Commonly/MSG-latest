package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffChat implements CommandExecutor {

    public static ArrayList<Player> chat = new ArrayList<Player>();
    public static ArrayList<Player> sc = new ArrayList<Player>();
    Main plugin;
    public StaffChat(Main pl) {
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String scEnabled = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("staffchat.enabled"));
        String scDisabled = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("staffchat.disabled"));
        String notEnabled = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("staff-chat-not-enabled").replace("%player%", sender.getName()));
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("staffchat")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Command can only be used by a player.");
                return true;
            }
            if (!sc.contains(p) && plugin.getConfig().getBoolean("use-staff-chat") == true) {
                sc.add(p);
                chat.add(p);
                plugin.MessageAPI(p, scEnabled);
                return true;
            }
            if (plugin.getConfig().getBoolean("use-staff-chat") == true) {
                sc.remove(p);
                chat.remove(p);
                plugin.MessageAPI(p, scDisabled);
                return true;
            } else {
                sender.sendMessage(notEnabled);
            }
            return true;
        }
        return true;
    }

}
