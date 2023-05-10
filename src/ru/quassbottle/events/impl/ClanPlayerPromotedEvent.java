package ru.quassbottle.events.impl;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import ru.quassbottle.events.ClanEvent;
import ru.quassbottle.models.Clan;

public class ClanPlayerPromotedEvent extends ClanEvent {
    private Clan clan;
    private OfflinePlayer player;

    @Override
    public Clan getClan() {
        return clan;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public ClanPlayerPromotedEvent(Clan clan, OfflinePlayer player) {
        this.clan = clan;
        this.player = player;
    }
}
