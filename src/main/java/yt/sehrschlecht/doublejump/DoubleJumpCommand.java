package yt.sehrschlecht.doublejump;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class DoubleJumpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = Doublejump.getPlugin().getConfig();
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("doublejump.command")) {
                if(config.getBoolean("player." + player.getName() + ".toggled")) {
                    player.setAllowFlight(false);
                    player.sendMessage(config.getString("message.prefix") + config.getString("message.disabled"));
                    config.set("player." + player.getName() + ".toggled", false);
                } else {
                    player.setAllowFlight(true);
                    player.sendMessage(config.getString("message.prefix") + config.getString("message.enabled"));
                    config.set("player." + player.getName() + ".toggled", true);
                }
            } else {
                player.sendMessage(config.getString("message.prefix") + config.getString("message.noPerms"));
            }
        } else {
            sender.sendMessage(config.getString("message.prefix") + config.getString("message.isNotAPlayer"));
        }
        return false;
    }
}
