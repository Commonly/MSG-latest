package me.jdog.msg.other.events;

import com.connorlinfoot.bountifulapi.Actionbar;
import me.jdog.msg.Main;
import me.jdog.msg.other.commands.StaffChat;
import me.jdog.murapi.api.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventChat implements Listener {

    private Main plugin;

    public EventChat(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        String scFormat = Color.addColor("staffchat.format", plugin).replace("%player%", p.getName()).replace("%msg%", msg);
        if (StaffChat.chat.contains(p.getName())) {
            e.setCancelled(true);
        }
        if (StaffChat.sc.contains(p.getName()) && plugin.getConfig().getBoolean("use-staff-chat")) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl.hasPermission("msg.staffchat.see")) {
                    pl.sendMessage(scFormat);
                    if(pl.hasPermission("msg.staffchat.actionbar") && plugin.getConfig().getBoolean("use-staff-chat-actionbar")) {
                        String actionbar = Color.addColor("staffchat.actionbar-format", plugin).replace("%msg%", msg).replace("%player%", p.getName());
                        Actionbar.sendActionBar(p, actionbar);
                    }
                }
            }
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            console.sendMessage(Color.addColor(scFormat));
        }

        if (!plugin.isChatEnabled()) {
            if(!p.hasPermission("msg.chat.talk")) {
                e.setCancelled(true);
            }
        }
    }
}
