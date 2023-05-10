package ru.quassbottle.commands.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.events.EventCaller;
import ru.quassbottle.events.impl.ClanJoinEvent;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

public class JoinCommand extends BasicCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "присоединиться к клану";
    }

    @Override
    public String getArgs() {
        return "<лидер>";
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        Player pl = (Player) sender;
        if (args.length > 0) {
            Clan c = InviteCommand.invitedPlayers.get(pl);
            if (c != null) {
                if (c.getLeader().getName().toLowerCase().equals(args[0].toLowerCase())) {
                    InviteCommand.invitedPlayers.remove(pl);
                    c.addMember(pl);
                    EventCaller.call(new ClanJoinEvent(c, pl));
                }
            }
            else
                pl.sendMessage(XPClansUtils.getFormat("&cВы не приглашены в клан."));
        }
        else {
            pl.sendMessage(XPClansUtils.getFormat("&cВведите ник лидера клана."));
        }
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
