package me.jdog.msg.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muricans on 11/17/16.
 */
public class GuiManager implements Listener {
    private static Map<Integer, GuiBase> guiBaseMap = new HashMap<>();

    public GuiManager() {
        this.registerGui(0, new GuiPanel("Message Panel", 27));
    }

    public static GuiBase getGui(int id) {
        return guiBaseMap.get(id);
    }

    public void registerGui(int id, GuiBase guiBase) {
        guiBaseMap.put(id, guiBase);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            if (e.getInventory().getName() != null) {
                for (GuiBase g : guiBaseMap.values()) {
                    if (e.getInventory().getName().equals(g.inventory.getName())) {
                        e.setCancelled(true);
                        g.click(e.getCurrentItem(), (Player) e.getWhoClicked());
                    }
                }
            }
        }
    }


}
