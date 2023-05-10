package ru.quassbottle.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.events.EventCaller;
import ru.quassbottle.events.impl.ClanKickEvent;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

public class KickCommand extends BasicCommand {
    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "исключить игрока из клана";
    }

    @Override
    public String getArgs() {
        return "<ник>";
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        Player pl = (Player) sender;
        Clan clan = XProjectClans.getInstance().getClansHandler().getClanStorage().get(pl);
        if (clan == null) {
            pl.sendMessage(XPClansUtils.getFormat("&cВы не состоите в клане."));// TODO not in clan message
            return;
        }
        if (clan.getMember(pl).getAccessLevel() < 5) {
            pl.sendMessage(XPClansUtils.getFormat("&cНедостаточно прав."));
            return;
        }
        if (args.length > 0) {
            OfflinePlayer kicked = Bukkit.getOfflinePlayer(args[0]);
            if (!kicked.hasPlayedBefore() && !kicked.isOnline()) {
                pl.sendMessage(XPClansUtils.getFormat("&cНеизвестный игрок \"" + args[0] + "\"!"));
                return;
            }
            if (clan.getMember(kicked) == null) {
                pl.sendMessage(XPClansUtils.getFormat("&cИгрок \"" + kicked.getName() + "\" не состоит в вашем клане."));
                return;
            }
            clan.removeMember(kicked);
            EventCaller.call(new ClanKickEvent(clan, kicked, pl));
            return;
        }
        pl.sendMessage(XPClansUtils.getFormat("&cНеверные аргументы."));

    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
