package me.jdog.msg;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.jdog.msg.gui.GuiPanel;
import me.jdog.msg.other.Updater;
import me.jdog.msg.other.commands.*;
import me.jdog.msg.other.config.DataManager;
import me.jdog.msg.other.events.*;
import me.jdog.msg.other.network.ServerUtils;
import me.jdog.murapi.api.Color;
import me.jdog.murapi.api.cmd.CMDManager;
import me.jdog.murapi.api.gui.GuiManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    // I try not to use instances but at this point it's kind of needed
    @Deprecated
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private ProtocolManager protocolManager;

    public ServerUtils serverUtils = ServerUtils.getInstance();
    public DataManager dataManager = DataManager.getInstance();
    public Map<String, String> reply = new HashMap<>();
    private volatile boolean allowChat = true;

    public void MessageAPI(Player target, String msg) {
        msg = Color.addColor(msg);
        target.sendMessage(msg);
    }

    // double method unneeded // all methods used it before-hand so i had to keep it ;-(
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
        Logger logger = this.getLogger();
        getLogger().info("Checking for updates...");
        Object[] updates = Updater.getLastUpdate();
        if(updates.length == 2) {
            getLogger().info("Update found for MSG! https://www.spigotmc.org/resources/msg-tested-on-1-8-1-7-10-1-10.31708/updates");
        } else {
            getLogger().info("No updates found! All up to date.");
        }
        GuiManager.registerGui(0, new GuiPanel("Message Panel", 27));
        CMDManager.registerCommand(1, new GuiCommand());
        CMDManager.registerCommand(2, new Vote());
        CMDManager.registerCommand(3, new ReplaceChat());
        CMDManager.registerCommand(4, new Profanity());

        getCommand("mpanel").setExecutor(new CMDManager());
        getCommand("vote").setExecutor(new CMDManager());
        getCommand("replacechat").setExecutor(new CMDManager());
        getCommand("profanity").setExecutor(new CMDManager());

        getServer().getPluginManager().registerEvents(new ChatEventOther(), this);

        this.eventList();
        LanguageEvent.log.create();
        this.commandList();
        this.saveDefaultConfig();
        this.dataManager.setup(this);
        Options.autoStaff = this.dataManager.getData().getStringList("auto");
        if (!Options.autoStaff.contains("dank_memed_error_fixed")) {
            Options.autoStaff.add("dank_memed_error_fixed");
        }
        this.dataManager.getData().set("auto", Options.autoStaff);
        this.dataManager.saveData();
        protocolManager = ProtocolLibrary.getProtocolManager();
        tab();
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

                String msg2 = StringUtils.join(args, ' ', 1, args.length);

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
                String msg = StringUtils.join(args, ' ', 0, args.length);

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
            MessageAPI(sender, "&bMessage help >>");
            MessageAPI(sender, "&b/msg (/m | /t | /tell) - Send a message to the specified person.");
            MessageAPI(sender, "&b/reply (/r) - Reply to the person you last messaged.");
            MessageAPI(sender, "&b/mhelp - Display message help.");
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
        //this.getCommand("socialspy").setExecutor(new SocialSpy(this)); // failed attempt at making a SocialSpy ;-( // FIXME: 1/3/17
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
        //pm.registerEvents(new EventSpy(this), this); // failed attempt at making a SocialSpy ;-( // FIXME: 1/3/17
        pm.registerEvents(new LanguageEvent(this), this);
    }

    private void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    // poorly coded... trying to make it better bare with me ;-)
    private void tab() {
        if(!getConfig().getBoolean("tab.cancel")) return;
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.TAB_COMPLETE) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                // check for permission
                if(!e.getPlayer().hasPermission("msg.tab")) {
                    PacketContainer packet = e.getPacket();
                    // the message
                    String start = packet.getStrings().read(0);
                    // check if it is a command by seeing if it starts with a '/'
                    if(!start.startsWith("/")) return;
                    if(getConfig().getBoolean("tab.first-param-only")) {
                        // only block the first param
                        if (start.contains(" ")) return;
                    }
                    // check if use-message is true
                    if(getConfig().getBoolean("tab.use-message")) {
                        // if so send them message
                        String send = Color.addColor("tab.message", Main.getInstance());
                        e.getPlayer().sendMessage(send);
                    }
                    // set it cancelled so the player doesn't see server commands.
                    e.setCancelled(true);
                }
            }
        });
    }

}
