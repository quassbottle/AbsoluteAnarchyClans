package ru.quassbottle.commands.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.events.EventCaller;
import ru.quassbottle.events.impl.ClanDisbandEvent;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

public class DisbandCommand extends BasicCommand {
    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getDescription() {
        return "распустить клан";
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        Clan c = XProjectClans.getInstance().getClansHandler().getClanStorage().get((Player) sender);
        if (c == null) {
            sender.sendMessage(XPClansUtils.getFormat("&cВы не состоите в клане."));// TODO not in clan message
            return;
        }
        if (c.getMember((Player) sender).getAccessLevel() < 10) {
            sender.sendMessage(XPClansUtils.getFormat("&cВы не являетесь лидером клана."));// TODO not leader message;
            return;
        }
        if (args.length > 0 && args[0].equals("confirm")) {
            EventCaller.call(new ClanDisbandEvent(c, (Player) sender));
            XProjectClans.getInstance().getClansHandler().getClanStorage().deleteClan(c);
        }
        else {
            sender.sendMessage(XPClansUtils.getFormat("&cЕсли вы действительно хотите распустить клан, напишите /clan disband confirm."));
        }
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
