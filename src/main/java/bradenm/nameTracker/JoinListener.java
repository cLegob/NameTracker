package bradenm.nameTracker;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JoinListener implements Listener {

    private final NameTracker plugin;

    public JoinListener(NameTracker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        // Get the player who just joined
        Player player = e.getPlayer();

        UUID uuid = player.getUniqueId();
        String name = player.getName();

        FileConfiguration data = plugin.getData();
        String path = uuid.toString();

        // If this player has never been tracked before,
        // create a new entry with their current name
        if (!data.contains(path)) {
            List<String> names = new ArrayList<>();
            names.add(name);

            data.set(path, names);
            plugin.saveData();
            return;
        }

        // Retrieve existing name history for this player
        List<String> names = data.getStringList(path);

        // If this name is already recorded, do nothing
        if (names.contains(name)) {
            return;
        }

        // Add new username to history
        names.add(name);

        // Save updated name list back to config
        data.set(path, names);
        plugin.saveData();
    }
}