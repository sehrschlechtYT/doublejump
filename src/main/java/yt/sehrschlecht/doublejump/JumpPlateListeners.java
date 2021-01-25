package yt.sehrschlecht.doublejump;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class JumpPlateListeners implements Listener {
    @EventHandler
    public static void onInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.PHYSICAL)) {
            FileConfiguration config = Doublejump.getPlugin().getConfig();
            if(config.getBoolean("jumpplates.activated")) {
                if(event.getClickedBlock().getType().equals(Material.valueOf(config.getString("jumpplates.type").toUpperCase()))) {
                    if(event.getPlayer().hasPermission("jumpplates.use")) {
                        Player player = event.getPlayer();
                        if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                            event.setCancelled(true);
                            player.setVelocity(event.getPlayer().getLocation().getDirection().multiply(config.getDouble("jumpplates.intensity")));
                        }
                    }
                }
            }
        }
    }
}
