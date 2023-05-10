package ru.quassbottle.storages;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.handlers.deserialize.ClansDeserializer;
import ru.quassbottle.models.Clan;

import java.io.File;
import java.util.UUID;

public class ClanStorage {
    public void addClan(Clan clan, Player creator) {
        XProjectClans.getInstance().getClansHandler().createClanFile(clan, creator);
    }

    public void deleteClan(Clan clan) {
        if (XProjectClans.getInstance().getClansHandler().getClansDir().listFiles() != null) {
            for (File file : XProjectClans.getInstance().getClansHandler().getClansDir().listFiles()) {
                if (file.getName().contains(clan.getUuid().toString())) {
                    file.delete();
                }
            }
        }
    }

    public Clan get(String uuid) {
        if (XProjectClans.getInstance().getClansHandler().getClansDir().listFiles() == null)
            return null;
        for (File file : XProjectClans.getInstance().getClansHandler().getClansDir().listFiles()) {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            if (configuration.getString(ClansDeserializer.ClanSection.UUID.name().toLowerCase()).equals(uuid)) {
                return XProjectClans.getInstance().getClansHandler().getDeserializer().serializeClan(file);
            }
        }
        return null;
        /*
        for (Clan clan : clans) {
            if (clan.getUuid().equals(uuid))
                return clan;
        }
        return null;
         */
    }

    public Clan get(Player member) {
        if (XProjectClans.getInstance().getClansHandler().getClansDir().listFiles() == null)
            return null;
        for (File file : XProjectClans.getInstance().getClansHandler().getClansDir().listFiles()) {
            Clan clan = XProjectClans.getInstance().getClansHandler().getDeserializer().serializeClan(file);
            if (clan.getMember(member) != null)
                return clan;
        }
        return null;
        /*
        for (Clan clan : clans) {
            if (clan.getMember(member) != null)
                return clan;
        }
        return null;

         */
    }
}
