package io.Revenger1.AutoStop;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class StopTimer extends TimerTask {

    private static StopTimer instance = new StopTimer();

    private final Timer timer;

    private long startTime;
    private int shutdownTime;
    private List<WarningTask> warningTasks = new ArrayList<>();
    private FileConfiguration config;

    public static StopTimer getInstance() {
        return instance;
    }

    private StopTimer() {
        timer = new Timer();
    }

    @Override
    public void run() {
        if(shutdownTime == 0) return;
        long timeToShutdown = startTime + (shutdownTime * 1000) - System.currentTimeMillis();

        warningTasks.forEach(task -> task.fire(timeToShutdown));

        if(timeToShutdown < 1) {
            String message = config.getString("shutDownMessage");
            if(message == null) message = "ERROR";

            Bukkit.broadcastMessage(message);
            Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("Server Automatic Shutdown"));
            Bukkit.shutdown();
        }
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;

        warningTasks.clear();

        config.getIntegerList("shutDownWarningTimes").forEach(time ->
            warningTasks.add(new WarningTask(time))
        );

        shutdownTime = config.getInt("shutDownTime");
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
        timer.scheduleAtFixedRate(this, 0, 1000);
    }

    public void stopTimer() {
        timer.cancel();
    }

    private final class WarningTask {
        public final int time;
        private boolean fired;

        public WarningTask(int time){
            this.time = time;
        }

        public void fire(long timeToShutDown) {
            if(config != null && !fired
                && timeToShutDown < time * 1000 + 1) {

                fired = true;

                String message = config.getString("shutdownWarningMessage");

                if(message == null) {
                    Bukkit.getLogger().log(Level.SEVERE, "No Shutdown Warning Message - AutoStop Plugin");
                    return;
                }

                if(time > 3600) {
                    message = message.replaceAll("@", String.format("%.0f Hours", time / 3600f));
                } else if(time > 60) {
                    message = message.replaceAll("@", String.format("%.0f Minutes", time / 60f));
                } else {
                    message = message.replaceAll("@", String.format("%d Seconds", time));
                }

                Bukkit.broadcastMessage(message);

                fired = true;
            }
        }

    }
}
