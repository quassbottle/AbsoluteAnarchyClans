package ru.quassbottle.handlers.deserialize;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.models.Clan;
import ru.quassbottle.models.MemberPair;
import ru.quassbottle.utils.LogService;
import ru.quassbottle.utils.XPClansUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ClansDeserializer {
    public void deserializeClan(Clan clan, Player creator) {
        try {
            File clanFile = new File(XProjectClans.getInstance().getDataFolder() + File.separator + "clans" + File.separator, clan.getUuid().toString() + ".yml");
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(clanFile);
            configuration.options().copyDefaults(true);
            for (ClanSection section : ClanSection.values()) {
                switch (section) {
                    case NAME: {
                        configuration.set(section.name().toLowerCase(), clan.getName());
                        break;
                    }
                    case UUID: {
                        configuration.set(section.name().toLowerCase(), clan.getUuid().toString());
                        break;
                    }
                    case POINTS: {
                        configuration.set(section.name().toLowerCase(), clan.getPoints());
                        break;
                    }
                    case EXPERIENCE: {
                        configuration.set(section.name().toLowerCase(), clan.getExperience());
                        break;
                    }
                    case MEMBERS: {
                        List<String> pairs = Collections.singletonList(creator.getUniqueId() + ":10");
                        //for (MemberPair pair : clan.getInternalMembers()) {
                        //    pairs.add(pair.getPlayer().getUniqueId() + ":" + pair.getAccessLevel());
                        //}
                        configuration.set(section.name().toLowerCase(), pairs);
                        break;
                    }
                }
            }
            configuration.save(clanFile);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            XProjectClans.getInstance().getLogService().logBy("&cWTF", LogService.LogType.CLANS_DESERIALIZER);
        }
    }

    public Clan serializeClan(File clanFile) {
        YamlConfiguration clanConfiguration = YamlConfiguration.loadConfiguration(clanFile);
        String name = XPClansUtils.getFormat(clanConfiguration.getString(ClanSection.NAME.name().toLowerCase()));
        UUID uuid = UUID.fromString(clanConfiguration.getString(ClanSection.UUID.name().toLowerCase()));
        int points = clanConfiguration.getInt(ClanSection.POINTS.name().toLowerCase());
        int experience = clanConfiguration.getInt(ClanSection.EXPERIENCE.name().toLowerCase());
        List<MemberPair> pairList = new ArrayList<>();
        for (String pairString : clanConfiguration.getStringList(ClanSection.MEMBERS.name().toLowerCase())) {
            String[] rawPair = pairString.split(":");
            MemberPair pair = new MemberPair(XProjectClans.getInstance().getServer().getOfflinePlayer(UUID.fromString(rawPair[0])), Integer.parseInt(rawPair[1]));
            pairList.add(pair);
        }
        return Clan.getClan(pairList, experience, points, name, uuid);
    }

    /*
    public File getClanFile(Clan clan) {
        for (File file : XProjectClans.getInstance().getClansHandler().getClansDir().listFiles()) {
            if (file.getName().contains(clan.getUuid().toString()))
                return file;
        }
        return null;
    }
     */

    public static enum ClanSection {
        NAME,
        UUID,
        EXPERIENCE,
        POINTS,
        MEMBERS
    }
}
