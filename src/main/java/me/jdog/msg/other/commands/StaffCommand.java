package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.cmd.CMD;
import me.jdog.murapi.api.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Muricans on 1/19/17.
 */
public class StaffCommand extends CMD {

    private Main main;

    public StaffCommand(Main main) {
        super("staff");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Config staffList = new Config(main, "stafflist.yml");
        sender.sendMessage(Color.addColor("staff.header", main));
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (staffList.getFile().contains("data." + pl.getUniqueId().toString())) {
                if (!StaffHide.contains(pl.getName())) {
                    String group = staffList.getString("data." + pl.getUniqueId().toString() + ".group");
                    String prefix = staffList.getString("groups." + group + ".groupFormat").replace("%player%", pl.getName());
                    String prefixCon = Color.addColor(prefix);
                    String groupSet = Color.addColor("staff.group", main).replace("%group%", prefixCon);
                    sender.sendMessage(groupSet);
                }
            }
        }
        sender.sendMessage(Color.addColor("staff.footer", main));
        return false;
    }

}
