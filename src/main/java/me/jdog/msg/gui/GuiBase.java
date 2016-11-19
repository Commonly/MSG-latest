package me.jdog.msg.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Muricans on 11/17/16.
 */
public abstract class GuiBase {
    public Inventory inventory;

    public GuiBase(String name, int size) {
        inventory = Bukkit.createInventory(null, size, name);
        this.registerItems();
    }

    public abstract void registerItems();

    public abstract void click(ItemStack itemStack, Player player);

}
