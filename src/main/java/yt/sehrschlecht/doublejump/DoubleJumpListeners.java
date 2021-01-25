package yt.sehrschlecht.doublejump;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;

public class DoubleJumpListeners implements Listener {
    private static ArrayList<String> cooldowns = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOW)
    public static void onPlayerJoin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission("doublejump.use")) {
            FileConfiguration config = Doublejump.getPlugin().getConfig();
            if(config.getBoolean("doublejump.activateOnJoin")) {
                config.set("player." + event.getPlayer().getName() + ".toggled", true);
                event.getPlayer().setAllowFlight(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public static void onPlayerToggleFly(PlayerToggleFlightEvent event) {
        if(event.getPlayer().hasPermission("doublejump.use")) {
            Player player = event.getPlayer();
            if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                FileConfiguration config = Doublejump.getPlugin().getConfig();
                if(config.getBoolean("player." + player.getName() + ".toggled")) {
                    event.setCancelled(true);
                    if(!cooldowns.contains(player.getName())) {
                        player.setVelocity(event.getPlayer().getLocation().getDirection().multiply(config.getDouble("doublejump.intensity")));
                        cooldowns.add(player.getName());
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Doublejump.getPlugin(), new Runnable() {
                            @Override
                            public void run() {
                                if(cooldowns.contains(player.getName())) {
                                    cooldowns.remove(player.getName());
                                }
                            }
                        }, (config.getInt("doublejump.cooldown") * 20L));
                    }
                }
            }
        }
    }
}
