package me.jdog.msg;

import me.jdog.msg.gui.GuiManager;
import me.jdog.msg.other.commands.*;
import me.jdog.msg.other.config.DataManager;
import me.jdog.msg.other.events.*;
import me.jdog.msg.other.network.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public ServerUtils serverUtils = ServerUtils.getInstance();
    public DataManager dataManager = DataManager.getInstance();
    public Map<String, String> reply = new HashMap<>();
    private volatile boolean allowChat = true;

    public void MessageAPI(Player target, String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        target.sendMessage(msg);
    }

    public void MessageAPI(CommandSender sender, String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        sender.sendMessage(msg);
    }

    public void broadcast(String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        Bukkit.getServer().broadcastMessage(msg);
    }

    @Override
    public void onEnable() {
        Logger logger = this.getLogger();

        this.eventList();
        this.commandList();
        this.saveDefaultConfig();
        this.dataManager.setup(this);
        Options.autoStaff = this.dataManager.getData().getStringList("auto");
        if (!Options.autoStaff.contains("dank_memed_error_fixed")) {
            Options.autoStaff.add("dank_memed_error_fixed");
        }
        this.dataManager.getData().set("auto", Options.autoStaff);
        this.dataManager.saveData();

        logger.info("Message has been enabled!");
    }

    @Override
    public void onDisable() {
        Logger logger = this.getLogger();

        logger.info("Message has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("msg")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Command can only be used by a player.");
                return true;
            }
            Player p = (Player) sender;
            String noargsMsg = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("msg.noargsmsg"));
            if (args.length == 0) {
                this.MessageAPI(p, noargsMsg);
                return true;
            }

            if (args.length >= 2) {
                String notonlineMsg = ChatColor.translateAlternateColorCodes('&',
                        this.getConfig().getString("msg.notonlinemsg"));
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    this.MessageAPI(p, notonlineMsg);
                    return true;
                }

                String sendingSelf = ChatColor.translateAlternateColorCodes('&',
                        this.getConfig().getString("msg.sendingtoself"));
                if (target == sender) {
                    this.MessageAPI(sender, sendingSelf);
                    return true;
                }

                StringBuilder msg2 = new StringBuilder();
                @SuppressWarnings("unused")
                String[] newArray = Arrays.copyOfRange(args, 1, args.length);
                String[] arrstring = Arrays.copyOfRange(args, 1, args.length);
                int m = arrstring.length;
                int m2 = 0;
                while (m2 < m) {
                    String arg = arrstring[m2];
                    msg2.append(arg);
                    msg2.append(" ");
                    ++m2;
                }

                String senderMsg = ChatColor.translateAlternateColorCodes('&',
                        this.getConfig().getString("msg.sendermsg").replace("%target%", target.getName())
                                .replace("%sender%", sender.getName()).replace("%msg%", msg2));
                String targetMsg = ChatColor.translateAlternateColorCodes('&',
                        this.getConfig().getString("msg.targetmsg").replace("%sender%", sender.getName())
                                .replace("%target%", target.getName()).replace("%msg%", msg2));
                callEvent(new EventMessageHandler(target));
                this.MessageAPI(target, targetMsg);
                this.reply.put(p.getName(), target.getName());
                this.reply.put(target.getName(), p.getName());
                callEvent(new EventMessageHandler(sender));
                this.MessageAPI(sender, senderMsg);
                return true;
            }
        }

        if (cmd.getName().equalsIgnoreCase("reply")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Command can only be used by a player.");
                return true;
            }

            String noargsRep = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("msg.noargsreply"));
            if (args.length == 0) {
                this.MessageAPI(sender, noargsRep);
                return true;
            }

            if (args.length >= 1) {
                StringBuilder msg = new StringBuilder();
                String[] msg2 = args;
                int sendingSelf = msg2.length;
                int target = 0;
                while (target < sendingSelf) {
                    String arg = msg2[target];
                    msg.append(arg);
                    msg.append(" ");
                    ++target;
                }

                String notonlineRep = ChatColor.translateAlternateColorCodes('&',
                        this.getConfig().getString("msg.notonlinereply"));
                if(!reply.containsKey(sender.getName())) {
                    this.MessageAPI(sender, notonlineRep);
                    return true;
                }
                Player r = Bukkit.getPlayer(reply.get(sender.getName()));
                if (r == null || !r.isOnline()) {
                    this.MessageAPI(sender, notonlineRep);
                    return true;
                }

                String replyMsg = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("msg.replymsg")
                        .replace("%sender%", sender.getName()).replace("%target%", r.getName()).replace("%msg%", msg));
                String replysenderMsg = ChatColor.translateAlternateColorCodes('&',
                        this.getConfig().getString("msg.replysendermsg").replace("%target%", r.getName())
                                .replace("%sender%", sender.getName()).replace("%msg%", msg));
                callEvent(new EventMessageHandler(r));
                this.MessageAPI(r, replyMsg);
                callEvent(new EventMessageHandler(sender));
                this.MessageAPI(sender, replysenderMsg);
                return true;

            }
        }

        if (cmd.getName().equalsIgnoreCase("mhelp")) { // Default user help.
            if (!(sender instanceof Player)) {
                sender.sendMessage("Command can only be used by a player.");
                return true;
            }
            Player p = (Player) sender;
            if (p.hasPermission("msg.help")) {
                this.MessageAPI(p, "&bMessage help >>");
                this.MessageAPI(p, "&b/msg (/m | /t | /tell) - Send a message to the specified person.");
                this.MessageAPI(p, "&b/reply (/r) - Reply to the person you last messaged.");
                this.MessageAPI(p, "&b/mhelp - Display message help.");
            }
            return true;
        }

        return true;
    }

    public void allowChat(boolean allow) {
        this.allowChat = allow;
    }

    public boolean isChatEnabled() {
        return this.allowChat;
    }

    private void commandList() {
        this.getCommand("message").setExecutor(new reload(this));
        this.getCommand("staffchat").setExecutor(new StaffChat(this));
        this.getCommand("moptions").setExecutor(new Options(this));
        this.getCommand("mpanel").setExecutor(new GuiCommand(this));
        this.getCommand("togglechat").setExecutor(new ToggleChat(this));
    }

    private void eventList() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Leave(this), this);
        pm.registerEvents(new EventChat(this), this);
        pm.registerEvents(new Join(this), this);
        pm.registerEvents(new EventMessage(this), this);
        pm.registerEvents(new JoinJSON(this), this);
        pm.registerEvents(new EventDeath(this), this);
        pm.registerEvents(new GuiManager(), this);
    }

    private void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

}
