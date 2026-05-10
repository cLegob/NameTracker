package bradenm.nameTracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

public class ReloadCommand implements CommandExecutor {

    private final NameTracker plugin;

    public ReloadCommand(NameTracker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String label,
                             String @NonNull [] args) {

        if (!sender.hasPermission("nametracker.reload")) {
            sender.sendMessage(
                    Component.text("You do not have permission to use this command.", NamedTextColor.RED)
            );
            return true;
        }

        plugin.reloadData();

        sender.sendMessage(
                Component.text("NameTracker database reloaded.", NamedTextColor.GREEN)
        );

        return true;
    }
}