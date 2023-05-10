package ru.quassbottle.commands.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.events.EventCaller;
import ru.quassbottle.events.impl.ClanInvitedEvent;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

import java.util.HashMap;

public class InviteCommand extends BasicCommand {
    public static HashMap<Player, Clan> invitedPlayers = new HashMap<>();

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "пригласить игрока в клан";
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
            pl.sendMessage(XPClansUtils.getFormat("&cВы не состоите в клане."));
            return;
        }
        if (args.length > 0) {
            Player invited = XProjectClans.getInstance().getServer().getPlayer(args[0]);
            if (invited != null) {
                Clan inv = XProjectClans.getInstance().getClansHandler().getClanStorage().get(invited);
                if (inv != null) {
                    pl.sendMessage(XPClansUtils.getFormat("&cЭтот игрок уже состоит в клане."));
                    return;
                }
                if (clan.getMembersAmount() + 1 > clan.getMaxMembers()) {
                    pl.sendMessage(XPClansUtils.getFormat("&cНедостаточно слотов у клана."));
                    return;
                }
                //if (invitedPlayers.get(invited) equals(clan)) {
                //    pl.sendMessage(XPClansUtils.getFormat("&cЭтот игрок уже приглашен в этот клан!"));
                //}
                else {
                    EventCaller.call(new ClanInvitedEvent(clan, invited, pl));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (invitedPlayers.containsKey(invited)) {
                                invitedPlayers.remove(invited);
                                invited.sendMessage(XPClansUtils.getFormat("&cВремя на принятие приглашения истекло."));
                                pl.sendMessage(XPClansUtils.getFormat("&cВремя на принятие приглашения истекло."));
                            }
                        }
                    }.runTaskLater(XProjectClans.getInstance(), 60 * 20);
                    invitedPlayers.put(invited, clan);
                }
            }
        }

    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
