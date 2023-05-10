package ru.quassbottle.events.impl;

import org.bukkit.entity.Player;
import ru.quassbottle.events.ClanEvent;
import ru.quassbottle.models.Clan;

public class ClanRenameEvent extends ClanEvent {
    private Clan clan;
    private Player player;
    private String newName;

    @Override
    public Clan getClan() {
        return clan;
    }

    public Player getPlayer() {
        return player;
    }

    public String getNewName() { return newName; }

    public ClanRenameEvent(Clan clan, Player player, String newName) {
        this.clan = clan;
        this.player = player;
        this.newName = newName;
    }
}
