package ru.quassbottle.commands.impl;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.commands.BasicCommand;
import ru.quassbottle.models.Clan;
import ru.quassbottle.utils.XPClansUtils;

public class ChatCommand extends BasicCommand {
    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public String getDescription() {
        return "написать сообщение в клановый чат";
    }

    @Override
    public String getArgs() {
        return "<текст>";
    }

    @Override
    protected void execute(CommandSender sender, Command command, String label, String[] args) {
        Player pl = (Player) sender;
        Clan clan = XProjectClans.getInstance().getClansHandler().getClanStorage().get(pl);
        if (clan == null) {
            pl.sendMessage(XPClansUtils.getFormat("&cВы не состоите в клане."));// TODO not in clan message
            return;
        }
        if (args.length > 0) {
            String text = "";
            for (String s : args) {
                text += s + " ";
            }
            text = text.substring(0, text.length() - 1);
            clan.broadcast(ChatColor.GOLD + "[КЛАН] " + ChatColor.GREEN + pl.getName() + ChatColor.RESET + ": " + text);
        }
        else {
            sender.sendMessage(XPClansUtils.getFormat("&cВы не написали сообщение."));
        }
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
