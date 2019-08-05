package io.Revenger1.AutoStop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandAutoStop implements CommandExecutor {

    JavaPlugin plugin;

    public CommandAutoStop(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.isOp()) {
            commandSender.sendMessage("Requires OP to run");
        }

        if(strings.length < 1) {
            commandSender.sendMessage("Placeholder");
        } else {
            switch(strings[0].toLowerCase()){
                case "list":
            }
        }

        return true;
    }

}
