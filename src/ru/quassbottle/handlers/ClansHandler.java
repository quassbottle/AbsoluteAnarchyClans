package ru.quassbottle.handlers;

import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.events.EventCaller;
import ru.quassbottle.events.impl.ClanLevelUpEvent;
import ru.quassbottle.handlers.deserialize.ClansDeserializer;
import ru.quassbottle.models.Clan;
import ru.quassbottle.storages.ClanStorage;
import ru.quassbottle.utils.LogService;
import ru.quassbottle.utils.XPClansUtils;

import java.io.File;

public class ClansHandler {
    private ClansDeserializer deserializer;
    private ClanStorage clanStorage;
    private File clansDir;

    public ClansHandler() {
        this.deserializer = new ClansDeserializer();
        this.clanStorage = new ClanStorage();
        this.clansDir = new File(XProjectClans.getInstance().getDataFolder() + File.separator + "clans");

        if (!clansDir.exists()) {
            clansDir.mkdirs();
        }
        /*
        if (clansDir.listFiles() == null) {
            XProjectClans.getInstance().getLogService().logBy("&cThere are no clans to load.", LogService.LogType.CLANS_HANDLER);
        }
        else {
            for (File file : clansDir.listFiles()) {
                clanStorage.addClan(deserializer.serializeClan(file));
            }
        }
         */
    }

    public void giveExperience(Clan clan, int exp) {
        int newExp = clan.getExperience() + exp;
        if (XPClansUtils.getLevel(newExp) > clan.getLevel()) {
            EventCaller.call(new ClanLevelUpEvent(clan, XPClansUtils.getLevel(newExp)));
        }
        clan.setExperience(clan.getExperience() + exp);
    }

    public void givePoints(Clan clan, int amount) {
        clan.setPoints(clan.getPoints() + amount);
    }

    public void subtractPoints(Clan clan, int amount) {
        clan.setPoints(Math.max(0, clan.getPoints() - amount));
    }

    public boolean canBuy(Clan clan, int price) {
        return clan.getPoints() - price > 0;
    }

    public File getClansDir() {
        return clansDir;
    }

    public void createClanFile(Clan clan, Player creator) {
        deserializer.deserializeClan(clan, creator);
    }

    public ClanStorage getClanStorage() {
        return clanStorage;
    }
    /*
    public void deserializeAll() {
        for (Clan clan : clanStorage.getClans()) {
            deserializer.deserializeClan(clan);
        }
    }
       */
    public ClansDeserializer getDeserializer() {
        return deserializer;
    }



}
