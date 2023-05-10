package ru.quassbottle.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.events.EventCaller;
import ru.quassbottle.events.impl.ClanPlayerPromotedEvent;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

public class PromoteCommand extends BasicCommand {
    @Override
    public String getName() {
        return "promote";
    }

    @Override
    public String getDescription() {
        return "поывысить игрока";
    }

    @Override
    public String getArgs() {
        return "<ник>";
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        Player pl = (Player) sender;
        Clan c = XProjectClans.getInstance().getClansHandler().getClanStorage().get(pl);
        if (c == null) {
            sender.sendMessage(XPClansUtils.getFormat("&cВы не состоите в клане."));// TODO not in clan message
            return;
        }
        if (c.getMember(pl).getAccessLevel() < 10) {
            pl.sendMessage(XPClansUtils.getFormat("&cНедостаточно прав."));
            return;
        }
        if (args.length > 0) {
            OfflinePlayer promoted = Bukkit.getOfflinePlayer(args[0]);
            if (!promoted.hasPlayedBefore() && !promoted.isOnline()) {
                pl.sendMessage(XPClansUtils.getFormat("&cНеизвестный игрок \"" + args[0] + "\"!"));
                return;
            }
            Clan promotedClan = XProjectClans.getInstance().getClansHandler().getClanStorage().get(promoted.getPlayer());
            if (promotedClan == null) {
                pl.sendMessage(XPClansUtils.getFormat("&cЭтот игрок не состоит в клане."));
                return;
            }
            if (!promotedClan.getUuid().equals(c.getUuid())) {
                pl.sendMessage(XPClansUtils.getFormat("&cЭтот игрок состоит не в вашем клане."));
                return;
            }
            EventCaller.call(new ClanPlayerPromotedEvent(c, promoted.getPlayer()));
            c.promotePlayer(promoted, 5);
            return;

        }
        pl.sendMessage(XPClansUtils.getFormat("&cНеверные аргументы"));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
