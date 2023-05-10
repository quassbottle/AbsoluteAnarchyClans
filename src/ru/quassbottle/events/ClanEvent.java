package ru.quassbottle.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ru.quassbottle.models.Clan;

public abstract class ClanEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public abstract Clan getClan();
}
