package ru.quassbottle.commands.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

public class UuidCommand extends BasicCommand {
    @Override
    public String getName() {
        return "admin";
    }

    @Override
    public String getDescription() {
        return "&cКоманда для отладки кланов, позволяющая менять некоторые его значения внутри игры";
    }

    @Override
    public String getArgs() {
        return "<uuid> <exp/points> <add/remove/set> <value>";
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(XPClansUtils.getFormat("&cНеверные аргументы."));
        }
        Clan clan = XProjectClans.getInstance().getClansHandler().getClanStorage().get(args[0]);
        if (clan == null) {
            sender.sendMessage(XPClansUtils.getFormat("&cКлан с данным UUID не найден."));
            return;
        }
        int value = 0;
        try {
            value = Integer.parseInt(args[3]);
        }
        catch (Exception ex) {
            sender.sendMessage(XPClansUtils.getFormat("&cНеизвестное значение \"" + args[3] + "\"!"));
            return;
        }
        switch (args[1]) {
            case "exp": {
                switch (args[2]) {
                    case "add": {
                        XProjectClans.getInstance().getClansHandler().giveExperience(clan, value);
                        break;
                    }
                    case "remove": {
                        clan.setExperience(clan.getExperience() - value);
                        break;
                    }
                    case "set": {
                        clan.setExperience(value);
                        break;
                    }
                    default: {
                        sender.sendMessage(XPClansUtils.getFormat("&cНеверные аргументы."));
                        return;
                    }
                }
                sender.sendMessage(XPClansUtils.getFormat("&aНовое значение: " + clan.getExperience()));
                return;
            }
            case "points": {
                switch (args[2]) {
                    case "add": {
                        clan.setPoints(clan.getPoints() + value);
                        break;
                    }
                    case "remove": {
                        clan.setPoints(clan.getPoints() - value);
                        break;
                    }
                    case "set": {
                        clan.setPoints(value);
                        break;
                    }
                    default: {
                        sender.sendMessage(XPClansUtils.getFormat("&cНеверные аргументы."));
                        return;
                    }
                }
                sender.sendMessage(XPClansUtils.getFormat("&aНовое значение: " + clan.getPoints()));
                return;
            }
            default: {
                sender.sendMessage(XPClansUtils.getFormat("&cНеверные аргументы."));
            }
        }
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }
}
