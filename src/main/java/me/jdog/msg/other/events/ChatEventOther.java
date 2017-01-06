package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import me.jdog.msg.other.commands.ReplaceChat;
import me.jdog.murapi.api.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Muricans on 1/2/17.
 */
public class ChatEventOther implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        HashMap<String, Boolean> list = ReplaceChat.getReplaceChat();
        if(list.containsKey(player.getName())) {
            e.setCancelled(true);
            List<String> words = Main.getInstance().getConfig().getStringList("replacechat.words");
            String format = e.getFormat().replace(e.getMessage(), "");
            for(Player pl : Bukkit.getServer().getOnlinePlayers()) {
                pl.sendMessage(format + get(words));
            }
        }
    }

    private String get(List<String> words) {
        return words.get(new Random().nextInt(words.size()));
    }

}
