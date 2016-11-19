package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import me.jdog.msg.other.commands.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventChat implements Listener {

    private Main plugin;

    public EventChat(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        String scFormat = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("staffchat.format")
                .replace("%player%", p.getName()).replace("%msg%", msg));
        if (StaffChat.chat.contains(p)) {
            e.setCancelled(true);
        }
        if (StaffChat.sc.contains(p) && plugin.getConfig().getBoolean("use-staff-chat") == true) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl.hasPermission("msg.staffchat.see")) {
                    pl.sendMessage(scFormat);
                }
            }
        }

        if (!plugin.isChatEnabled()) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!pl.hasPermission("msg.chat.talk")) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
