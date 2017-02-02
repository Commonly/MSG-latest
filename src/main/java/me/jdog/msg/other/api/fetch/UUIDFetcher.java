package me.jdog.msg.other.api.fetch;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Muricans on 1/28/17.
 */
public class UUIDFetcher {
    private String playerName;

    /**
     * @param playerName The player name to get UUID from.
     */
    public UUIDFetcher(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Get the players UUID.
     *
     * @return The UUID of the player.
     */
    public UUID getUUID() {
        return Bukkit.getServer().getOfflinePlayer(playerName).getUniqueId();
    }

    /**
     * Get the players UUID in string format.
     *
     * @return The UUID of the player in string format.
     */
    public String getUUIDAsString() {
        return getUUID().toString();
    }

    /**
     * Get the players name.
     *
     * @return The players name from the string inputted.
     */
    public String getPlayerName() {
        return getOfflinePlayerByString().getName();
    }

    /**
     * Check if the player has joined before, this method uses the players uuid and not their name.
     *
     * @return UUID has played before or not.
     */
    public boolean hasUUIDJoined() {
        return getOfflinePlayerByUUID().hasPlayedBefore();
    }

    /**
     * Check if the player has joined before, this method uses the players name and not their uuid.
     *
     * @return Player has played before or not.
     */
    public boolean hasPlayerJoined() {
        return getOfflinePlayerByString().hasPlayedBefore();
    }

    /**
     * Get the players name.
     *
     * @return The players name from the uuid of the string inputted.
     */
    public String getPlayerNameByUUID() {
        return getOfflinePlayerByUUID().getName();
    }

    /**
     * Get the player.
     *
     * @return The player from the UUID from the string inputted.
     */
    public OfflinePlayer getOfflinePlayerByUUID() {
        return Bukkit.getServer().getOfflinePlayer(getUUID());
    }

    /**
     * Get the player.
     *
     * @return The player from the string inputted.
     */
    public OfflinePlayer getOfflinePlayerByString() {
        return Bukkit.getServer().getOfflinePlayer(playerName);
    }

    public Player getPlayerByUUID() {
        return Bukkit.getServer().getPlayer(getUUID()).getPlayer();
    }

    public Player getPlayerByString() {
        return Bukkit.getServer().getPlayer(playerName).getPlayer();
    }

}
