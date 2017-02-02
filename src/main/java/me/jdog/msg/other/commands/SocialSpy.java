package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import me.jdog.murapi.api.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Muricans on 11/22/16.
 */
public class SocialSpy implements CommandExecutor {
    public static ArrayList<String> ss = new ArrayList<>();
    private Main plugin;

    public SocialSpy(Main pl) {
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("socialspy")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be executed by players, sorry!");
                return true;
            }
            Player player = (Player) sender;
            if (!ss.contains(player.getName())) {
                ss.add(player.getName());
                String ssEnabled = Color.addColor("socialspy.enabled", plugin);
                plugin.MessageAPI(player, ssEnabled);
                return true;
            } else {
                ss.remove(player.getName());
                String ssDisabled = Color.addColor("socialspy.disabled", plugin);
                plugin.MessageAPI(player, ssDisabled);
                return true;
            }
        }
        return false;
    }
}
