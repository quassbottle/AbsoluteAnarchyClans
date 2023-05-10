package ru.quassbottle.events.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.quassbottle.XProjectClans;
import ru.quassbottle.events.impl.*;
import ru.quassbottle.models.Clan;
import ru.quassbottle.models.MemberPair;
import ru.quassbottle.utils.XPClansUtils;

import java.lang.reflect.Array;

public class ClanListener implements Listener {
    @EventHandler
    public void onClanJoin(ClanJoinEvent event) {
        // TODO
        event.getPlayer().sendMessage(XPClansUtils.getFormat("&aВы вступили в клан " + event.getClan().getName() + "&a!"));
        event.getClan().broadcast(XPClansUtils.getFormat("&6[КЛАН] &aИгрок "  + event.getPlayer().getName() + " вступил в клан!"));
    }

    @EventHandler
    public void onClanLeave(ClanLeaveEvent event) {
        // TODO
        event.getPlayer().sendMessage(XPClansUtils.getFormat("&cВы покинули клан " + event.getClan().getName() + "&c!"));
        event.getClan().broadcast(XPClansUtils.getFormat("&6[КЛАН] &cИгрок "  + event.getPlayer().getName() + " покинул клан!"));
    }

    @EventHandler
    public void onClanKick(ClanKickEvent event) {
        // TODO
        if (event.getPlayer().getPlayer() != null)
            event.getPlayer().getPlayer().sendMessage(XPClansUtils.getFormat("&cИгрок " + event.getWhoKicked().getName() + " исключил вас из клана " + event.getClan().getName() + "&c!"));
        event.getClan().broadcast(XPClansUtils.getFormat("&6[КЛАН] &aИгрок "  + event.getPlayer().getName() + " был исключен из клана игроком " + event.getWhoKicked().getName() + "!"));
    }

    @EventHandler
    public void onClanInvited(ClanInvitedEvent event) {
        event.getPlayer().sendMessage(XPClansUtils.getFormat("&aИгрок " + event.getWhoInvite().getName() + " пригласил вас в клан \"" + event.getClan().getName() + "\"!\n" +
                "&bУ вас есть одна минута, чтобы принять приглашение (/clan join " + event.getClan().getLeader().getName() + ")"));
        event.getClan().broadcast(XPClansUtils.getFormat("&6[КЛАН] &aИгрок "  + event.getWhoInvite().getName() + " пригласил в клан игрока " + event.getPlayer().getName() + "."));
    }

    @EventHandler
    public void onClanDisband(ClanDisbandEvent event) {
        event.getClan().broadcast(XPClansUtils.getFormat("&cВаш клан был распущен"));
    }

    @EventHandler
    public void onClanPlayerPromoted(ClanPlayerPromotedEvent event) {
        event.getClan().broadcast(XPClansUtils.getFormat("&6[КЛАН] &aИгрок " + event.getPlayer().getName() + " был повышен в звании!"));
    }

    @EventHandler
    public void onClanPlayerDemoted(ClanPlayerDemotedEvent event) {
        event.getClan().broadcast(XPClansUtils.getFormat("&6[КЛАН] &cИгрок " + event.getPlayer().getName() + " был понижен в звании!"));
    }

    @EventHandler
    public void onClanRenamed(ClanRenameEvent event) {
        event.getClan().broadcast(XPClansUtils.getFormat("&6[КЛАН] &aНазвание клана было изменено на \"" + event.getNewName() + "&a\"."));
    }

    @EventHandler
    public void onClanDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Object source = event.getDamager();
            if (source instanceof Arrow || source instanceof ThrownPotion) {
                source = ((Projectile) source).getShooter();
            }
            if (source instanceof Player) {
                Player damager = (Player) source;
                Player whoDamaged = (Player) event.getEntity();

                Clan damagedClan = XProjectClans.getInstance().getClansHandler().getClanStorage().get(whoDamaged);
                if (damagedClan != null && damagedClan.getMember(damager) != null) {
                    damager.sendMessage(XPClansUtils.getFormat("&cВы не можете нанести урон своему соклановцу."));
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onLevelUp(ClanLevelUpEvent event) {
        for (MemberPair member : event.getClan().getInternalMembers()) {
            if (member.getPlayer().getPlayer() != null) {
                Player pl = member.getPlayer().getPlayer();
                pl.playSound(pl.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                pl.sendMessage(XPClansUtils.getCenteredMessage(XPClansUtils.getFormat("&c---)====(---")));
                pl.sendMessage(XPClansUtils.getCenteredMessage(XPClansUtils.getFormat("&a&k% &6LEVEL UP &a&k%")));
                pl.sendMessage(XPClansUtils.getCenteredMessage(XPClansUtils.getFormat("&aТеперь у вашего клана &l" + event.getLevel() + " &aуровень!")));
                pl.sendMessage(XPClansUtils.getCenteredMessage(XPClansUtils.getFormat("&aРазблокировано &l" + (XPClansUtils.GetMaxMembers(event.getLevel()) -
                        XPClansUtils.GetMaxMembers(event.getLevel() - 1)) + " &aновых слотов участников")));
                pl.sendMessage(XPClansUtils.getCenteredMessage(XPClansUtils.getFormat("&c---)====(---")));
            }
        }
    }

    @EventHandler
    public void onClanChat(AsyncPlayerChatEvent event) {
        Clan c = XProjectClans.getInstance().getClansHandler().getClanStorage().get(event.getPlayer());
        if (event.getMessage().startsWith("$") && c != null) {
            String msg = event.getMessage().substring(1);
            event.setCancelled(true);
            c.broadcast(ChatColor.GOLD + "[КЛАН] " + ChatColor.GREEN + event.getPlayer().getName() + ChatColor.RESET + ": " + msg);
        }
    }
}


