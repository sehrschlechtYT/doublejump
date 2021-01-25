package yt.sehrschlecht.doublejump;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Doublejump extends JavaPlugin {
    private static Doublejump plugin;
    @Override
    public void onEnable() {
        plugin = this;
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new DoubleJumpListeners(), this);
        pluginManager.registerEvents(new JumpPlateListeners(), this);

        FileConfiguration config = getConfig();

        config.addDefault("doublejump.intensity", 1.5D);
        config.addDefault("doublejump.activateOnJoin", true);
        config.addDefault("doublejump.cooldown", 1);
        config.addDefault("jumpplates.activated", true);
        config.addDefault("jumpplates.type", "LIGHT_WEIGHTED_PRESSURE_PLATE");
        config.addDefault("jumpplates.intensity", 1.5D);
        config.addDefault("message.prefix", "§7[§6DoubleJump§7] ");
        config.addDefault("message.isNotAPlayer", "§cThis command can only be run by players.");
        config.addDefault("message.noPerms", "§cYou do not have permission to execute this command.");
        config.addDefault("message.disabled", "§cYou disabled the DoubleJump.");
        config.addDefault("message.enabled", "§aYou enabled the DoubleJump.");

        saveDefaultConfig();
        config.options().copyHeader(true);
        config.options().copyDefaults(true);
        saveConfig();

        getCommand("doublejump").setExecutor(new DoubleJumpCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static Doublejump getPlugin() {
        return plugin;
    }

}
