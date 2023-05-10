package ru.quassbottle.commands.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.events.EventCaller;
import ru.quassbottle.events.impl.ClanRenameEvent;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

public class RenameCommand extends BasicCommand {
    @Override
    public String getName() {
        return "rename";
    }

    @Override
    public String getDescription() {
        return "переименовать клан";
    }

    @Override
    public String getArgs() {
        return "<название>";
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        Player pl = (Player) sender;
        Clan c = XProjectClans.getInstance().getClansHandler().getClanStorage().get(pl);
        if (c == null) {
            pl.sendMessage(XPClansUtils.getFormat("&cВы не состоите в клане."));// TODO not in clan message
            return;
        }
        if (c.getMember(pl).getAccessLevel() < 10) {
            pl.sendMessage(XPClansUtils.getFormat("&cНедостаточно прав."));
            return;
        }
        if (args.length > 0) {
            if (args[0].length() > 24) { // TODO
                pl.sendMessage(XPClansUtils.getFormat("&cСлишком длинное имя (" + args[0].length() + " > 24)"));
                return;
            }
            EventCaller.call(new ClanRenameEvent(c, pl, XPClansUtils.getFormat(args[0])));
            c.setName(XPClansUtils.getFormat(args[0]));
            return;
        }
        pl.sendMessage(XPClansUtils.getFormat("&cНеверные аргументы"));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
