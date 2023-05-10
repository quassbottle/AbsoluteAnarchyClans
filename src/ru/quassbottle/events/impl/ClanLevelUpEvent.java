package ru.quassbottle.events.impl;

import org.bukkit.entity.Player;
import ru.quassbottle.events.ClanEvent;
import ru.quassbottle.models.Clan;

public class ClanLevelUpEvent extends ClanEvent {
    private Clan clan;
    private int lvl;

    @Override
    public Clan getClan() {
        return clan;
    }

    public int getLevel() {
        return lvl;
    }

    public ClanLevelUpEvent(Clan clan, int lvl) {
        this.clan = clan;
        this.lvl = lvl;
    }
}
