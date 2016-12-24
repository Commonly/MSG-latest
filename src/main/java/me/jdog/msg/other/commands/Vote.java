package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.cmd.CMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Muricans on 12/23/16.
 */
public class Vote extends CMD {

    public Vote() {
        super("vote");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        String header = Color.addColor("voting.header", Main.getInstance());
        sender.sendMessage(header);
        for(String links : Main.getInstance().getConfig().getStringList("voting.links")) {
            sender.sendMessage(Color.addColor(links));
        }
        String footer = Color.addColor("voting.footer", Main.getInstance());
        sender.sendMessage(footer);
        return false;
    }
}
