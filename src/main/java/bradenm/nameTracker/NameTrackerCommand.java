package bradenm.nameTracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NameTrackerCommand implements CommandExecutor {

    private final NameTracker plugin;

    public NameTrackerCommand(NameTracker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String label,
                             String @NonNull [] args) {

        if (!sender.hasPermission("nametracker")) {
            sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED));
            return true;
        }

        FileConfiguration data = plugin.getData();

        // ---------------------------------------------------
        // /nametracker lookup <name>
        // Searches stored name histories and returns latest stored name
        // ---------------------------------------------------
        if (args.length == 2 && args[0].equalsIgnoreCase("lookup")) {

            String searchName = args[1];

            sender.sendMessage(
                    Component.text("Searching for players with name: ", NamedTextColor.GRAY)
                            .append(Component.text(searchName, NamedTextColor.WHITE))
            );

            boolean found = false;

            for (String key : data.getKeys(false)) {

                List<String> names = data.getStringList(key);

                // Check if searchName exists anywhere in history
                boolean match = names.stream()
                        .anyMatch(n -> n.equalsIgnoreCase(searchName));

                if (!match) continue;

                String latestName = names.getLast();

                found = true;

                sender.sendMessage(
                        Component.text(" - ", NamedTextColor.GRAY)
                                .append(
                                        Component.text(
                                                        latestName != null ? latestName : "Unknown",
                                                        NamedTextColor.WHITE
                                                )
                                                .clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand(
                                                        "/nametracker " + latestName
                                                ))
                                )
                );
            }

            if (!found) {
                sender.sendMessage(
                        Component.text("No players found with that name.", NamedTextColor.GRAY)
                );
            }

            return true;
        }

        // ---------------------------------------------------
        // /nametracker <player>
        // Shows name history for a specific player
        // ---------------------------------------------------
        if (args.length != 1) {
            sender.sendMessage(
                    Component.text("Usage: /nametracker <player> or /nametracker lookup <name>", NamedTextColor.GRAY)
            );
            return true;
        }

        String requestedTarget = args[0];

        OfflinePlayer target = Bukkit.getOfflinePlayer(requestedTarget);

        String targetName = target.getName();
        UUID uuid = target.getUniqueId();

        String path = uuid.toString();

        if (!data.contains(path)) {
            sender.sendMessage(
                    Component.text(target.getName() + " is not in the NameTracker database for this server. Try /nametracker lookup?", NamedTextColor.GRAY)
                            .hoverEvent(Component.text("They may have never joined, have not joined since NameTracker was enabled, or have changed their name."))
            );
            return true;
        }

        List<String> names = data.getStringList(path);

        if (names.size() == 1) {
            sender.sendMessage(
                    Component.text(targetName + " has no other stored names for this server.", NamedTextColor.GRAY)
                            .hoverEvent(Component.text("They may have other usernames which have not been seen by NameTracker on this server."))
            );
            return true;
        }

        assert targetName != null;
        sender.sendMessage(
                Component.text("Name history for ", NamedTextColor.GRAY)
                        .append(Component.text(targetName, NamedTextColor.WHITE))
                        .append(Component.text(":", NamedTextColor.GRAY))
        );

        for (int i = names.size() - 2; i >= 0; i--) {
            String name = names.get(i);
            sender.sendMessage(
                    Component.text(" - ", NamedTextColor.GRAY)
                            .append(Component.text(name, NamedTextColor.WHITE))
            );
        }

        return true;
    }
}