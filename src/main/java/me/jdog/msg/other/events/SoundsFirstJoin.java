package me.jdog.msg.other.events;

import me.jdog.msg.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

/**
 * Created by Muricans on 2/2/17.
 */
public class SoundsFirstJoin implements Listener {

    private Main main;

    public SoundsFirstJoin(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(!e.getPlayer().hasPlayedBefore()) {
            List<String> sounds = main.dataManager.getData().getStringList("sounds");
            sounds.add(e.getPlayer().getUniqueId().toString());
            main.dataManager.getData().set("sounds", sounds);
            main.dataManager.saveData();
            main.dataManager.reloadData();
        }
    }

}
