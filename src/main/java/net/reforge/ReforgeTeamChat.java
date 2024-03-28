package net.reforge;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public final class ReforgeTeamChat extends JavaPlugin {


    public static final String PREFIX = "§8[§aTeamChat§8]";
    private static ReforgeTeamChat instance;
    public static PluginManager pluginManager = Bukkit.getPluginManager();
    private final Map<UUID, PlayerInfo> playerInfoMap = Maps.newConcurrentMap();



    @Override
    public void onEnable() {


        instance = this;


        // Register Commands
        getCommand("tc").setExecutor(new TeamChatCommand());


    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static ReforgeTeamChat getInstance() {
        return instance;
    }


    public PlayerInfo getPlayerInfo(final UUID uuid) {
        if (playerInfoMap.containsKey(uuid))
            return playerInfoMap.get(uuid);
        playerInfoMap.put(uuid, new PlayerInfo(Bukkit.getPlayer(uuid)));
        return playerInfoMap.get(uuid);
    }

}
