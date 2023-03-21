package fr.hyriode.build;

import fr.hyriode.api.impl.common.config.HyriConfigManager;
import fr.hyriode.api.impl.common.mongodb.MongoDB;
import fr.hyriode.api.impl.server.world.SHyriWorldManager;
import fr.hyriode.build.config.Config;
import fr.hyriode.build.discord.DiscordWebhook;
import fr.hyriode.build.map.config.ConfigManager;
import fr.hyriode.build.map.config.models.SheepWarsConfig;
import fr.hyriode.hyrame.HyrameLoader;
import fr.hyriode.hyrame.plugin.IPluginProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by AstFaster
 * on 22/02/2023 at 16:11
 */
public class HyriBuild extends JavaPlugin implements IPluginProvider {

    private static final String PACKAGE = "fr.hyriode.build";

    private static HyriBuild instance;

    private Config config;
    private DiscordWebhook webhook;
    private MongoDB prodMongoDB;
    private SHyriWorldManager prodWorldManager;
    private HyriConfigManager prodConfigManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        HyrameLoader.load(this);

        this.config = new Config();
        this.config.load();

        this.webhook = new DiscordWebhook();
        this.prodMongoDB = new MongoDB(this.config.getMongoDBConfig());
        this.prodMongoDB.startConnection();
        this.prodWorldManager = new SHyriWorldManager(this.prodMongoDB);
        this.prodConfigManager = new HyriConfigManager(this.prodMongoDB);
        this.configManager = new ConfigManager();
    }

    @Override
    public void onDisable() {
        this.config.save();

        this.prodMongoDB.stopConnection();
        this.prodMongoDB = null;
    }

    public static HyriBuild get() {
        return instance;
    }

    public Config getConfiguration() {
        return this.config;
    }

    public DiscordWebhook getWebhook() {
        return this.webhook;
    }

    public SHyriWorldManager getProdWorldManager() {
        return this.prodWorldManager;
    }

    public HyriConfigManager getProdConfigManager() {
        return this.prodConfigManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }

    @Override
    public String getId() {
        return "build";
    }

    @Override
    public String[] getCommandsPackages() {
        return new String[] {PACKAGE};
    }

    @Override
    public String[] getListenersPackages() {
        return new String[] {PACKAGE};
    }

    @Override
    public String[] getItemsPackages() {
        return new String[] {PACKAGE};
    }

    @Override
    public String getLanguagesPath() {
        return "/lang/";
    }

}
