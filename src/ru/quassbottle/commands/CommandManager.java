package ru.quassbottle.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.quassbottle.commands.impl.*;
import ru.quassbottle.utils.XPClansUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandExecutor {
    private List<BasicCommand> commandsAvailable = new ArrayList<>();

    public static final String header = XPClansUtils.getFormat("&6[################ &cXProjectClans &6################]");

    public CommandManager() {
        commandsAvailable.add(new HelpCommand());
        commandsAvailable.add(new ChatCommand());
        commandsAvailable.add(new CreateCommand());
        commandsAvailable.add(new InfoCommand());
        commandsAvailable.add(new InviteCommand());
        commandsAvailable.add(new JoinCommand());
        commandsAvailable.add(new KickCommand());
        commandsAvailable.add(new PromoteCommand());
        commandsAvailable.add(new RenameCommand());
        commandsAvailable.add(new LeaveCommand());
        commandsAvailable.add(new DisbandCommand());
        commandsAvailable.add(new UuidCommand());
    }

    public BasicCommand getByName(String name) {
        for (BasicCommand command : commandsAvailable) {
            if (command.getName().equals(name))
                return command;
        }
        return null;
    }

    public List<BasicCommand> getCommandsAvailable() {
        return commandsAvailable;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 0) {
            BasicCommand cmd = getByName(strings[0]);
            boolean result = false;
            if (cmd != null)
                result = cmd.tryExecute(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
            if (!result) {
                commandSender.sendMessage(XPClansUtils.getFormat("&cНеизвестная команда."));
            }
        }
        else {
            new HelpCommand().execute(commandSender, command, s, strings);
        }
        return true;
    }
}
