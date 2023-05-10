package ru.quassbottle.events.impl;

import org.bukkit.OfflinePlayer;
import ru.quassbottle.events.ClanEvent;
import ru.quassbottle.models.Clan;

public class ClanPlayerDemotedEvent extends ClanEvent {
    private Clan clan;
    private OfflinePlayer player;

    @Override
    public Clan getClan() {
        return clan;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public ClanPlayerDemotedEvent(Clan clan, OfflinePlayer player) {
        this.clan = clan;
        this.player = player;
    }
}
