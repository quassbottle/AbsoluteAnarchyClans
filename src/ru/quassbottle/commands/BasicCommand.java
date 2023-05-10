package ru.quassbottle.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BasicCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getArgs();
    protected abstract void execute(CommandSender sender, Command command, String label, String[] args);
    public abstract boolean isPlayerOnly();

    public String getPermission() {
        return "xpclans." + getName();
    }

    public boolean tryExecute(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && isPlayerOnly()) {
            return false;
        }
        if (sender.hasPermission(getPermission())) {
            execute(sender, command, label, args);
            return true;
        }
        return false;
    }
}
