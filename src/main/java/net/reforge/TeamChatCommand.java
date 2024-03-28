package net.reforge;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
            return false;

        final Player player = (Player) sender;

        if (args.length == 0) {

            for (Player all : Bukkit.getOnlinePlayers()) {

                final PlayerInfo playerInfo = ReforgeTeamChat.getInstance().getPlayerInfo(all.getUniqueId());
                player.sendMessage(ReforgeTeamChat.PREFIX + "§e " + all.getName() + "§8(" + (playerInfo.isLoggedIn() ? "§aEingeloggt" : "§cAusgeloggt") + "§8)");

            }

            return false;
        }

        if (args.length == 1 && (args[0].equalsIgnoreCase("login") || args[0].equalsIgnoreCase("logout") || args[0].equalsIgnoreCase("notify"))) {

            switch (args[0].toLowerCase()) {

                case "login":

                    if (ReforgeTeamChat.getInstance().getPlayerInfo(player.getUniqueId()).isLoggedIn()) {

                        player.sendMessage(ReforgeTeamChat.PREFIX + "§7 Du bist bereits eingeloggt§8!");
                        return true;

                    }

                    for (Player all : Bukkit.getOnlinePlayers()) {

                        if (ReforgeTeamChat.getInstance().getPlayerInfo(all.getUniqueId()).isLoggedIn()) {
                            all.sendMessage(ReforgeTeamChat.PREFIX + "§6 " + player.getName() + "§7 hat sich in den Teamchat eingeloggt§8!");

                        }

                    }

                    for (Player all : Bukkit.getOnlinePlayers()) {

                        ReforgeTeamChat.getInstance().getPlayerInfo(player.getUniqueId()).setLoggedIn(true);
                        PlayerInfo playerInfo = ReforgeTeamChat.getInstance().getPlayerInfo(all.getUniqueId());
                        player.sendMessage(ReforgeTeamChat.PREFIX + "§e " + all.getName() + "§8(" +
                                (playerInfo.isLoggedIn() ? "§aEingeloggt" : "§cAusgeloggt") + "§8)");

                    }
                    return true;

                case "logout":

                    if (!ReforgeTeamChat.getInstance().getPlayerInfo(player.getUniqueId()).isLoggedIn()) {

                        player.sendMessage(ReforgeTeamChat.PREFIX + "§7 Du bist bereits ausgeloggt");
                        return false;

                    }

                    for (Player all : Bukkit.getOnlinePlayers()) {

                        PlayerInfo playerInfo = ReforgeTeamChat.getInstance().getPlayerInfo(all.getUniqueId());
                        ReforgeTeamChat.getInstance().getPlayerInfo(player.getUniqueId()).setLoggedIn(false);
                        player.sendMessage(ReforgeTeamChat.PREFIX + "§e " + all.getName() + "§8(" +
                                (playerInfo.isLoggedIn() ? "§aEingeloggt" : "§cAusgeloggt") + "§8)");

                        if (ReforgeTeamChat.getInstance().getPlayerInfo(all.getUniqueId()).isLoggedIn()) {
                            all.sendMessage(ReforgeTeamChat.PREFIX + "§6 " + player.getName() + "§7 hat sich vom Teamchat entfernt");

                        }
                        return false;
                    }


                    ReforgeTeamChat.getInstance().getPlayerInfo(player.getUniqueId()).setLoggedIn(false);
                    player.sendMessage(ReforgeTeamChat.PREFIX + " §7Du hast dich ausgeloggt");

                    break;

                case "notify":

                    if (ReforgeTeamChat.getInstance().getPlayerInfo(player.getUniqueId()).isLoggedIn()) {

                        if (!ReforgeTeamChat.getInstance().getPlayerInfo(player.getUniqueId()).isNotify()) {

                            player.sendMessage(ReforgeTeamChat.PREFIX + "§7 Du hast die Benachrichtigungen aktiviert");
                            ReforgeTeamChat.getInstance().getPlayerInfo(player.getUniqueId()).setNotify(true);


                        } else {

                            player.sendMessage(ReforgeTeamChat.PREFIX + "§7 Du hast die Benachrichtigungen deaktiviert");
                            ReforgeTeamChat.getInstance().getPlayerInfo(player.getUniqueId()).setNotify(false);

                        }
                        return true;
                    }

            }

        }

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i] + " ");
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (ReforgeTeamChat.getInstance().getPlayerInfo(all.getUniqueId()).isLoggedIn()) {
                all.sendMessage(ReforgeTeamChat.PREFIX + "§e " + player.getName() + "§f > §7" + stringBuilder.toString());
                if (ReforgeTeamChat.getInstance().getPlayerInfo(all.getUniqueId()).isNotify()) {
                    all.playSound(all.getLocation(), Sound.ENTITY_VILLAGER_TRADING, 10, 0.1f);
                }
            } else {
                //
                player.sendMessage(ReforgeTeamChat.PREFIX + "§c !! Du bist nicht eingeloggt !!");

            }
        }
        return false;
    }
}
