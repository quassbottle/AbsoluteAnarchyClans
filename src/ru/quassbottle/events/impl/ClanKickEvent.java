package ru.quassbottle.events.impl;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import ru.quassbottle.events.ClanEvent;
import ru.quassbottle.models.Clan;

public class ClanKickEvent extends ClanEvent {
    private Clan clan;
    private OfflinePlayer player;
    private Player who;

    @Override
    public Clan getClan() {
        return clan;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public Player getWhoKicked() {
        return who;
    }

    public ClanKickEvent(Clan clan, OfflinePlayer player, Player whoKicked) {
        this.clan = clan;
        this.who = whoKicked;
        this.player = player;
    }
}
