package ru.quassbottle.commands.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.commands.CommandManager;
import ru.quassbottle.utils.XPClansUtils;

public class HelpCommand extends BasicCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "справка по командам плагина";
    }

    @Override
    public String getArgs() {
        return null; //XPClansUtils.getFormat("&8<cmd>");
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        String toSend = CommandManager.header;
        for (BasicCommand basicCommand : XProjectClans.getInstance().getCommandManager().getCommandsAvailable()) {
            String arg = basicCommand.getArgs() == null ? "" : " " + basicCommand.getArgs();
            if (sender.hasPermission(basicCommand.getPermission())) {
                toSend += XPClansUtils.getFormat("\n&6/clan " + basicCommand.getName() + "&8" + arg + "&r: " + basicCommand.getDescription());
            }
        }
        sender.sendMessage(toSend.equals(CommandManager.header) ? XPClansUtils.getFormat("&cНедостаточно прав.") : toSend);
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }
}
