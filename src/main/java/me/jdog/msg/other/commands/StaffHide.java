package me.jdog.msg.other.commands;

import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.cmd.CMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Muricans on 1/22/17.
 */
public class StaffHide extends CMD {

    private static HashMap<String, Boolean> hidden = new HashMap<>();

    public StaffHide() {
        super("staffhide");
    }

    static boolean contains(String player) {
        return hidden.containsKey(player);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!hidden.containsKey(player.getName())) {
                hidden.put(player.getName(), true);
                player.sendMessage(Color.addColor("&7You're now hidden from the stafflist."));
                return true;
            } else if (hidden.containsKey(player.getName())) {
                hidden.remove(player.getName());
                player.sendMessage(Color.addColor("&7You're no longer hidden from the stafflist."));
                return true;
            }
        }
        return false;
    }

}
