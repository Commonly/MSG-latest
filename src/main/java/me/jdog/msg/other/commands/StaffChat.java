package me.jdog.msg.other.commands;

import com.connorlinfoot.bountifulapi.Actionbar;
import me.jdog.msg.Main;
import me.jdog.murapi.api.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class StaffChat implements CommandExecutor {

    public static ArrayList<String> chat = new ArrayList<String>();
    public static ArrayList<String> sc = new ArrayList<String>();
    Main plugin;

    public StaffChat(Main pl) {
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String scEnabled = Color.addColor("staffchat.enabled", plugin);
        String scDisabled = Color.addColor("staffchat.disabled", plugin);
        String notEnabled = Color.addColor("staff-chat-not-enabled", plugin).replace("%player%", sender.getName());
        if (cmd.getName().equalsIgnoreCase("staffchat")) {
            if (args.length == 0 && sender instanceof Player) {
                if (!sc.contains(sender.getName()) && plugin.getConfig().getBoolean("use-staff-chat") == true) {
                    sc.add(sender.getName());
                    chat.add(sender.getName());
                    plugin.MessageAPI(sender, scEnabled);
                    return true;
                }
                if (plugin.getConfig().getBoolean("use-staff-chat") == true) {
                    sc.remove(sender.getName());
                    chat.remove(sender.getName());
                    plugin.MessageAPI(sender, scDisabled);
                    return true;
                } else {
                    sender.sendMessage(notEnabled);
                }
                return true;
            }

            StringBuilder msg = new StringBuilder();
            String[] arrstring = Arrays.copyOfRange(args, 0, args.length);
            int m = arrstring.length;
            int m2 = 0;
            while (m2 < m) {
                String arg = arrstring[m2];
                msg.append(arg);
                msg.append(" ");
                ++m2;
            }
            String format = Color.addColor("staffchat.format", plugin).replace("%player%", sender.getName()).replace("%msg%", msg);
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                if (pl.hasPermission("msg.staffchat.see")) {
                    pl.sendMessage(format);
                    if (pl.hasPermission("msg.staffchat.actionbar") && plugin.getConfig().getBoolean("use-staff-chat-actionbar") && sender instanceof Player) {
                        Player p = (Player) sender;
                        String actionbar = Color.addColor("staffchat.actionbar-format", plugin).replace("%msg%", msg).replace("%player%", p.getName());
                        Actionbar.sendActionBar(p, actionbar);
                    }
                }
            }
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(Color.addColor(format));
            return true;
        }
        return true;
    }

}
