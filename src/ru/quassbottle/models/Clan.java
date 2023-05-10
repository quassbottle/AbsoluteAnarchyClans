package ru.quassbottle.models;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.handlers.deserialize.ClansDeserializer;
import ru.quassbottle.utils.XPClansUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Clan {
    private List<MemberPair> playerList;
    private int experience;
    private int points;
    private String name;
    private UUID uuid;

    private Clan(List<MemberPair> playerList, int experience, int points, String name, UUID uuid) {
        this.playerList = playerList;
        this.experience = experience;
        this.points = points;
        this.name = name;
        this.uuid = uuid;
    }

    public Clan(OfflinePlayer creator, String name) {
        this.playerList = new ArrayList<MemberPair>();
        MemberPair pair = new MemberPair(creator, 10);
        playerList.add(pair);
        this.experience = 0;
        this.points = 0;
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public int getMembersAmount() {
        return this.getInternalMembers().size();
    }

    public int getMaxMembers() {
        //return XPClansUtils.GetMaxMembers(XPClansUtils.getLevel(getExperience()));
        return 100;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPoints() {
        return YamlConfiguration.loadConfiguration(getClanFile()).getInt(ClansDeserializer.ClanSection.POINTS.name().toLowerCase());
    }

    public int getExperience() {
        return YamlConfiguration.loadConfiguration(getClanFile()).getInt(ClansDeserializer.ClanSection.EXPERIENCE.name().toLowerCase());
    }

    public void promotePlayer(OfflinePlayer player, int rank) {
        YamlConfiguration clanCfg = YamlConfiguration.loadConfiguration(getClanFile());
        List<String> players = clanCfg.getStringList(ClansDeserializer.ClanSection.MEMBERS.name().toLowerCase());
        for (String playerString : new ArrayList<>(players)) {
            if (playerString.startsWith(player.getUniqueId().toString())) {
                players.set(players.indexOf(playerString), player.getUniqueId().toString() + ":" + rank);
            }
        }
        try {
            clanCfg.save(getClanFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getLevel() {
        return XPClansUtils.getLevel(getExperience());
    }

    public File getClanFile() {
        return new File(XProjectClans.getInstance().getClansHandler().getClansDir(), uuid.toString() + ".yml");
    }

    public void setExperience(int amount) {
        File a = getClanFile();
        YamlConfiguration clanCfg = YamlConfiguration.loadConfiguration(a);
        clanCfg.set(ClansDeserializer.ClanSection.EXPERIENCE.name().toLowerCase(), amount);
        try {
            clanCfg.save(getClanFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setPoints(int amount) {
        File a = getClanFile();
        YamlConfiguration clanCfg = YamlConfiguration.loadConfiguration(a);
        clanCfg.set(ClansDeserializer.ClanSection.POINTS.name().toLowerCase(), amount);
        try {
            clanCfg.save(getClanFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setName(String v) {
        File a = getClanFile();
        YamlConfiguration clanCfg = YamlConfiguration.loadConfiguration(a);
        clanCfg.set(ClansDeserializer.ClanSection.NAME.name().toLowerCase(), v);
        try {
            clanCfg.save(getClanFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addMember(OfflinePlayer player) {
        try {
            File a = getClanFile();
            YamlConfiguration clanCfg = YamlConfiguration.loadConfiguration(a);
            List<String> playerPairs = clanCfg.getStringList(ClansDeserializer.ClanSection.MEMBERS.name().toLowerCase());
            playerPairs.add(player.getUniqueId() + ":0");
            clanCfg.set(ClansDeserializer.ClanSection.MEMBERS.name().toLowerCase(), playerPairs);
            clanCfg.save(a);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeMember(OfflinePlayer player) {
        try {
            File a = getClanFile();
            YamlConfiguration clanCfg = YamlConfiguration.loadConfiguration(a);
            List<String> playerPairs = clanCfg.getStringList(ClansDeserializer.ClanSection.MEMBERS.name().toLowerCase());
            playerPairs.removeIf(user -> player.getUniqueId().toString().equals(user.split(":")[0]));
            clanCfg.set(ClansDeserializer.ClanSection.MEMBERS.name().toLowerCase(), playerPairs);
            clanCfg.save(a);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public MemberPair getMember(OfflinePlayer player) {
        for (MemberPair pair : getInternalMembers()) {
            if (pair.getPlayer().getUniqueId().equals(player.getUniqueId()))
                return pair;
        }
        return null;
    }

    public void broadcast(String str) {
        for (MemberPair pair : getInternalMembers()) {
            if (pair.getPlayer().isOnline()) {
                pair.getPlayer().getPlayer().sendMessage(str);
            }
        }
    }

    public OfflinePlayer getLeader() {
        for (MemberPair pair : getInternalMembers()) {
            if (pair.getAccessLevel() == 10) {
                return pair.getPlayer();
            }
        }
        return null;
    }

    public List<MemberPair> getOfficers() {
        List<MemberPair> t = new ArrayList<>();
        for (MemberPair pair : getInternalMembers()) {
            if (pair.getAccessLevel() > 5 && pair.getAccessLevel() != 10) {
                t.add(pair);
            }
        }
        return t.size() > 0 ? t : null;
    }

    public String getRoleName(Player player) { // SHIT
        return getMember(player).getAccessLevel() == 10 ? "ЛИДЕР" :
                getMember(player).getAccessLevel() >= 5 ? "МОДЕРАТОР" :
                        "УЧАСТНИК";
    }

    public List<MemberPair> getInternalMembers() {
        try {
            List<MemberPair> pairs = new ArrayList<>();
            YamlConfiguration clanCfg = YamlConfiguration.loadConfiguration(getClanFile());
            for (String pair : clanCfg.getStringList(ClansDeserializer.ClanSection.MEMBERS.name().toLowerCase())) {
                String[] p = pair.split(":");
                pairs.add(new MemberPair(Bukkit.getOfflinePlayer(UUID.fromString(p[0])), Integer.parseInt(p[1])));
            }
            return pairs;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Clan getClan(List<MemberPair> playerList, int exp, int points, String name, UUID uuid) {
        return new Clan(playerList, exp, points, name, uuid);
    }
}
