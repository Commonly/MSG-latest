package me.jdog.msg.other.commands;

import me.jdog.murapi.api.cmd.CMD;
import me.jdog.murapi.api.gui.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand extends CMD {

    public GuiCommand() {
        super("mpanel");
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        p.openInventory(GuiManager.getGui(0).inventory);
        return false;
    }
}
