package io.Revenger1.AutoStop;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Level;

//@SuppressWarnings("unused")
public class AutoStop extends JavaPlugin implements Listener {

    private final StopTimer timer = StopTimer.getInstance();
    private boolean enabled;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "onEnable called");
        if(enabled) {
            getLogger().log(Level.INFO, "onEnable cancelled");
            return;
        }

        enabled = true;

        FileConfiguration config = getConfig();

        config.addDefault("enabled", false);
        config.addDefault("shutDownMessage", "Restarting Server...");
        config.addDefault("shutdownWarningMessage", "Restarting Server in @");
        config.addDefault("shutDownTime", 86400);
        config.addDefault("shutDownWarningTimes", Arrays.asList(86340, 300, 60, 15, 5, 4, 3, 2, 1));
        config.options().copyDefaults(true);
        saveConfig();

        if(config.getBoolean("enabled")) {
            getLogger().log(Level.INFO, "Plugin Enabled");
            getCommand("autostop").setExecutor(new CommandAutoStop(this));

            timer.setConfig(config);
            timer.startTimer();

            getServer().getPluginManager().registerEvents(new AutoStopListener(this), this);
        } else {
            getLogger().log(Level.SEVERE, "Plugin Disabled - Please look at AutoStop config");
            setEnabled(false);
        }

        getLogger().log(Level.INFO, "onEnable Finished");
    }

    @Override
    public void onDisable() {
        timer.stopTimer();
        getLogger().info("onDisable is called!");
    }
}
