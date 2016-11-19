package me.jdog.msg.other.commands;

import me.jdog.msg.Main;
import me.jdog.msg.gui.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand implements CommandExecutor {

    Main plugin;

    public GuiCommand(Main pl) {
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mpanel") && sender instanceof Player) {
            Player p = (Player) sender;
            p.openInventory(GuiManager.getGui(0).inventory);
        }
        return true;
    }

}
