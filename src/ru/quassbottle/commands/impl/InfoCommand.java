package ru.quassbottle.commands.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.models.Clan;
import ru.quassbottle.models.MemberPair;
import ru.quassbottle.utils.XPClansUtils;

import java.util.UUID;

public class InfoCommand extends BasicCommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "информация о вашем клане";
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        Player pl = (Player) sender;
        Clan clan = XProjectClans.getInstance().getClansHandler().getClanStorage().get(pl);
        boolean admin = false;
        if (args.length > 0 && pl.hasPermission("xpclans.admin")) {
            clan = XProjectClans.getInstance().getClansHandler().getClanStorage().get(args[0]);
            admin = true;
        }
        if (clan == null) {
            pl.sendMessage(XPClansUtils.getFormat("&cВы не состоите в клане."));
            return;
        }
        String result = "\n&6[################################################]\n";
        result += "&c&lИнформация о клане " + clan.getName() + "\n \n";
        //result += "&8UUID: " + clan.getUuid().toString() + "\n \n";
        result += "&aЛидер: &r" + clan.getLeader().getName() + "\n";
        if (clan.getOfficers() != null) {
            result += "&aОфицеры: &r";
            for (MemberPair pair : clan.getOfficers()) {
                result += pair.getPlayer().getName() + "&r, &e";
            }
            result = result.substring(0, result.length() - 4);
        }
        result += "&aИгроков в клане: &r" + clan.getMembersAmount() + "/" + clan.getMaxMembers();
        if (!admin)
            result += " &8(Вы - " + clan.getRoleName(pl) + "&8)";
        result += "\n \n";
        int lvl = XPClansUtils.getLevel(clan.getExperience());
        result += "&aУровень: &r" + lvl + " &8(" + clan.getExperience() + "/" + XPClansUtils.getExp(lvl + 1) + ")\n";
        result += "&aОчки: &r" + clan.getPoints() + "\n \n";
        result +="&6[################################################]\n";
        pl.sendMessage(XPClansUtils.getFormat(result));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
