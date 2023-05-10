package ru.quassbottle.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EventCaller {
    public static void call(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
