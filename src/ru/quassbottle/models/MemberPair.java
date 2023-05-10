package ru.quassbottle.models;

import org.bukkit.OfflinePlayer;

public class MemberPair {
    private OfflinePlayer player;
    private int accessLevel;

    public MemberPair(OfflinePlayer player, int accessLevel) {
        this.accessLevel = accessLevel;
        this.player = player;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int value) {
        accessLevel = value;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}
