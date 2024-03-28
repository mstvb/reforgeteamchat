package net.reforge;

import org.bukkit.entity.Player;

public class PlayerInfo {

    private Player player;
    private boolean loggedIn = false;
    private boolean notify = false;


    public PlayerInfo(final Player player) {
        this.player = player;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean isNotify() { return notify; }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setNotify(boolean notify) { this.notify = notify; }
}
