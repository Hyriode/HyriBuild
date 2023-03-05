package fr.hyriode.build.config;

import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.config.nested.MongoDBConfigImpl;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by AstFaster
 * on 22/02/2023 at 16:40
 */
public class Config {

    private final ConfigEntry.StringEntry webhook = new ConfigEntry.StringEntry("discord-webhook", HyriBuild.get().getConfig());

    private final File file;
    private final FileConfiguration configuration;

    private final MongoDBConfigImpl mongoDBConfig;

    public Config() {
        this.file = new File(HyriBuild.get().getDataFolder(), "config.yml");
        this.configuration = HyriBuild.get().getConfig();
        this.mongoDBConfig = new MongoDBConfigImpl();
    }

    public void load() {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();

            HyriBuild.get().saveResource(this.file.getName(), false);
        }

        try {
            this.configuration.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getWebhook() {
        return this.webhook.get();
    }

    public MongoDBConfigImpl getMongoDBConfig() {
        return this.mongoDBConfig;
    }

}
