package me.jdog.msg.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muricans on 11/17/16.
 */
public class GuiPanel extends GuiBase {

    private static List<String> elore = new ArrayList<String>();
    private static List<String> rlore = new ArrayList<String>();
    private static List<String> slore = new ArrayList<String>();

    public GuiPanel(String name, int size) {
        super(name, size);
    }

    @Override
    public void registerItems() {
        // Set items
        ItemStack j = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta e = j.getItemMeta();
        ItemStack l = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta r = l.getItemMeta();
        ItemStack sc = new ItemStack(Material.BLAZE_ROD);
        ItemMeta b = sc.getItemMeta();

        // Set item data

        // emerald
        String ename = ChatColor.translateAlternateColorCodes('&', "&aJoin");
        String elores = "Join the auto staffchat";
        e.setDisplayName(ename);
        elore.add(elores);
        e.setLore(elore);
        j.setItemMeta(e);
        elore.remove(elores);

        // redstone
        String rname = ChatColor.translateAlternateColorCodes('&', "&cLeave");
        String rlores = "Leave the auto staffchat";
        r.setDisplayName(rname);
        rlore.add(rlores);
        r.setLore(rlore);
        l.setItemMeta(r);
        rlore.remove(rlores);

        // blaze rod
        String bname = ChatColor.translateAlternateColorCodes('&', "&eStaffChat");
        String slores = "Toggle staffchat";
        b.setDisplayName(bname);
        slore.add(slores);
        b.setLore(slore);
        sc.setItemMeta(b);
        slore.remove(slores);

        // set item pos
        inventory.setItem(10, j);
        inventory.setItem(13, sc);
        inventory.setItem(16, l);
    }

    @Override
    public void click(ItemStack itemStack, Player player) {
        if (itemStack.hasItemMeta()) {
            if (itemStack.getItemMeta().hasDisplayName()) {
                String n = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
                if (n.equals("Join")) {
                    player.performCommand("mo auto join");
                }
                if (n.equals("Leave")) {
                    player.performCommand("mo auto leave");
                }

                if (n.equals("StaffChat")) {
                    player.performCommand("sc");
                }

            }
        }
    }

}
