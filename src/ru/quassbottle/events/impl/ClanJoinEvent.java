package ru.quassbottle.events.impl;

import org.bukkit.entity.Player;
import ru.quassbottle.events.ClanEvent;
import ru.quassbottle.models.Clan;

public class ClanJoinEvent extends ClanEvent {
    private Clan clan;
    private Player player;

    @Override
    public Clan getClan() {
        return clan;
    }

    public Player getPlayer() {
        return player;
    }

    public ClanJoinEvent(Clan clan, Player player) {
        this.clan = clan;
        this.player = player;
    }
}
