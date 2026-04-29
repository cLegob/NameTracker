package bradenm.nameTracker;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class NameTracker extends JavaPlugin {

    private File file;
    private FileConfiguration data;

    @Override
    public void onEnable() {
        createDataFile();
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getCommand("nametracker").setExecutor(new NameTrackerCommand(this));
    }

    private void createDataFile() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        file = new File(getDataFolder(), "nametracker.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        data = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getData() {
        return data;
    }

    public void saveData() {
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
