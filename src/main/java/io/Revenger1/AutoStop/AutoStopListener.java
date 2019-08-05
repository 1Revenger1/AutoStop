package io.Revenger1.AutoStop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class AutoStopListener implements Listener {

    private final JavaPlugin plugin;

    public AutoStopListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        if(!event.getType().equals(ServerLoadEvent.LoadType.RELOAD)) return;

        plugin.getLogger().log(Level.INFO, "Reloading AutoStop Config");

        StopTimer.getInstance().setConfig(plugin.getConfig());
    }

}
