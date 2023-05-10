package ru.quassbottle.commands.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

public class CreateCommand extends BasicCommand {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "создать клан";
    }

    @Override
    public String getArgs() {
        return "<название>";
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        Clan already = XProjectClans.getInstance().getClansHandler().getClanStorage().get((Player) sender);
        if (already != null) {
            sender.sendMessage(XPClansUtils.getFormat("&cВы уже состоите в клане \"" + already.getName() + "\"&c!"));
            return;
        }
        if (args.length != 1) {
            sender.sendMessage(XPClansUtils.getFormat("&cНеверные аргументы"));
            return;
        }
        if (args[0].length() > 24) { // TODO
            sender.sendMessage(XPClansUtils.getFormat("&cСлишком длинное имя (" + args[0].length() + " > 24)"));
            return;
        }
        Clan clan = new Clan((Player) sender, XPClansUtils.getFormat(args[0]));
        XProjectClans.getInstance().getClansHandler().getClanStorage().addClan(clan, (Player) sender);
        sender.sendMessage(XPClansUtils.getFormat("&aКлан \"" + clan.getName() + "&a\" был успешно создан!"));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
