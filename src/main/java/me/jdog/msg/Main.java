package me.jdog.msg;

import me.jdog.msg.gui.GuiPanel;
import me.jdog.msg.other.Updater;
import me.jdog.msg.other.commands.*;
import me.jdog.msg.other.config.DataManager;
import me.jdog.msg.other.events.*;
import me.jdog.msg.other.network.ServerUtils;
import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.cmd.CMDManager;
import me.jdog.murapi.api.gui.GuiManager;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private static Main instance;
    @Deprecated
    public static Main getInstance() {
        return instance;
    }

    public ServerUtils serverUtils = ServerUtils.getInstance();
    public DataManager dataManager = DataManager.getInstance();
    public Map<String, String> reply = new HashMap<>();
    private volatile boolean allowChat = true;

    public void MessageAPI(Player target, String msg) {
        msg = Color.addColor(msg);
        target.sendMessage(msg);
    }

    public void MessageAPI(CommandSender sender, String msg) {
        msg = Color.addColor(msg);
        sender.sendMessage(msg);
    }

    public void broadcast(String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        Bukkit.getServer().broadcastMessage(msg);
    }

    public boolean depend() {
        if(getServer().getPluginManager().getPlugin("murAPI")==null) {
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        if(depend()) {
            getLogger().severe("murAPI not found! Download it here: https://www.spigotmc.org/resources/murapi.32116/");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Object[] updates = Updater.getLastUpdate();
        getLogger().info("Checking for updates...");
        if(updates.length == 2) {
            getLogger().info("Update found for MSG! https://www.spigotmc.org/resources/msg-tested-on-1-8-1-7-10-1-10.31708/updates");
        } else {
            getLogger().info("No updates found! All up to date.");
        }
        Logger logger = this.getLogger();

        GuiManager.registerGui(0, new GuiPanel("Message Panel", 27));
        CMDManager.registerCommand(1, new GuiCommand());
        CMDManager.registerCommand(2, new Vote());

        getCommand("mpanel").setExecutor(new CMDManager());
        getCommand("vote").setExecutor(new CMDManager());

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
        instance = null;
        Logger logger = this.getLogger();

        logger.info("Message has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("msg")) {
            String noargsMsg = Color.addColor("msg.noargsmsg", this);
            if (args.length == 0) {
                this.MessageAPI(sender, noargsMsg);
                return true;
            }

            if (args.length >= 2) {
                String notonlineMsg = Color.addColor("msg.notonlinemsg", this);
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    this.MessageAPI(sender, notonlineMsg);
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

                String senderMsg = Color.addColor("msg.sendermsg", this).replace("%target%", target.getName()).replace("%sender%", sender.getName()).replace("%msg%", msg2);
                String targetMsg = Color.addColor("msg.targetmsg", this).replace("%target%", target.getName()).replace("%sender%", sender.getName()).replace("%msg%", msg2);
                callEvent(new EventMessageHandler(target));
                this.MessageAPI(target, targetMsg);
                this.reply.put(sender.getName(), target.getName());
                this.reply.put(target.getName(), sender.getName());
                callEvent(new EventMessageHandler(sender));
                this.MessageAPI(sender, senderMsg);
                return true;
            }
        }

        if (cmd.getName().equalsIgnoreCase("reply")) {
            String noargsRep = Color.addColor("msg.noargsreply", this);
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

                String notonlineRep = Color.addColor("msg.notonlinereply", this);
                if(!reply.containsKey(sender.getName())) {
                    this.MessageAPI(sender, notonlineRep);
                    return true;
                }
                Player r = Bukkit.getPlayer(reply.get(sender.getName()));
                if (r == null || !r.isOnline()) {
                    this.MessageAPI(sender, notonlineRep);
                    return true;
                }

                String replyMsg = Color.addColor("msg.replymsg", this).replace("%sender%", sender.getName()).replace("%target%", r.getName()).replace("%msg%", msg);
                String replysenderMsg = Color.addColor("msg.replysendermsg", this).replace("%sender%", sender.getName()).replace("%target%", r.getName()).replace("%msg%", msg);
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
        this.getCommand("togglechat").setExecutor(new ToggleChat(this));
        //this.getCommand("socialspy").setExecutor(new SocialSpy(this));
    }

    private void eventList() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Leave(this), this);
        pm.registerEvents(new EventChat(this), this);
        pm.registerEvents(new Join(this), this);
        pm.registerEvents(new EventMessage(this), this);
        pm.registerEvents(new JoinJSON(this), this);
        pm.registerEvents(new EventDeath(this), this);
        pm.registerEvents(new EventSign(), this);
        pm.registerEvents(new EventInteract(), this);
        //pm.registerEvents(new EventSpy(this), this);
    }

    private void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

}
