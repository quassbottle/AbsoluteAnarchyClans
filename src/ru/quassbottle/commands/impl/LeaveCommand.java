package ru.quassbottle.commands.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.events.EventCaller;
import ru.quassbottle.events.impl.ClanLeaveEvent;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

public class LeaveCommand extends BasicCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "покинуть клан";
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        Player pl = (Player) sender;
        Clan c = XProjectClans.getInstance().getClansHandler().getClanStorage().get(pl);
        if (c == null) {
            sender.sendMessage(XPClansUtils.getFormat("&cВы не состоите в клане."));// TODO not in clan message
            return;
        }
        if (c.getLeader().getUniqueId().equals(pl.getUniqueId())) {
            sender.sendMessage(XPClansUtils.getFormat("&cВы не можете покинуть клан."));// TODO not in clan message
            return;
        }
        EventCaller.call(new ClanLeaveEvent(c, pl));
        c.removeMember(pl);
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
