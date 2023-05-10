package ru.quassbottle.events.impl;

import org.bukkit.entity.Player;
import ru.quassbottle.events.ClanEvent;
import ru.quassbottle.models.Clan;

public class ClanInvitedEvent extends ClanEvent {
    private Clan clan;
    private Player player;
    private Player who;

    @Override
    public Clan getClan() {
        return clan;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getWhoInvite() {
        return who;
    }

    public ClanInvitedEvent(Clan clan, Player player, Player whoInvite) {
        this.clan = clan;
        this.player = player;
        this.who = whoInvite;
    }
}
