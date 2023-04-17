package fr.hyriode.build.map;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.api.impl.common.world.WorldCompression;
import fr.hyriode.api.world.IHyriWorld;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

/**
 * Created by AstFaster
 * on 24/02/2023 at 14:58
 */
public class Map {

    private IHyriWorld initialHandle;

    private final Environment environment;
    private final Category category;
    private IHyriWorld handle;
    private IHyriConfig config;

    public Map(Environment environment, Category category, IHyriWorld handle) {
        this.environment = environment;
        this.category = category;
        this.handle = handle;

        this.loadInitial();

        if (this.category.hasConfig(this.handle.getCategory())) {
            this.config = this.environment.getConfigManager().getConfig(this.category.getConfigClass(this.handle.getCategory()), this.category.getDatabase(), this.handle.getCategory(), this.handle.getName());
        }
    }

    private void loadInitial() {
        this.initialHandle = HyriAPI.get().getWorldManager().newWorld()
                .withDatabase(this.handle.getDatabase())
                .withName(this.handle.getName())
                .withCategory(this.handle.getCategory())
                .withAuthors(new ArrayList<>(this.handle.getAuthors()))
                .build();
        this.initialHandle.setEnabled(this.handle.isEnabled());
    }

    public boolean refreshData() {
        this.handle = this.environment.getWorldManager().getWorld(this.category.getDatabase(), this.handle.getCategory(), this.handle.getName());

        this.loadInitial();

        if (this.handle == null) {
            this.config = null;
            return false;
        }

        if (this.category.hasConfig(this.handle.getCategory())) {
            this.config = this.environment.getConfigManager().getConfig(this.category.getConfigClass(this.handle.getCategory()), this.category.getDatabase(), this.handle.getCategory(), this.handle.getName());
        }
        return true;
    }

    public void transfer() throws IOException {
        if (this.environment == Environment.DEVELOPMENT) {
            final String mapName = UUID.randomUUID().toString().split("-")[0];
            final File file = new File("./" + mapName);

            this.load(file);
            Environment.PRODUCTION.getWorldManager().saveWorld(this.handle, file);

            Files.walk(file.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    public void load(File destination) {
        this.environment.getWorldManager().loadWorld(this.handle, destination);
    }

    public void delete() {
        this.environment.getWorldManager().deleteWorld(this.handle);
    }

    public void update() {
        this.environment.getWorldManager().updateWorld(this.handle);
    }

    public void save(UUID worldId) {
        this.environment.getWorldManager().saveWorld(this.handle, worldId);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public Category getCategory() {
        return this.category;
    }

    public IHyriWorld getHandle() {
        return this.handle;
    }

    public IHyriWorld getInitialHandle() {
        return this.initialHandle;
    }

    public IHyriConfig getConfig() {
        return this.config;
    }

    public boolean hasConfig() {
        return this.config != null;
    }

    public void saveConfig(IHyriConfig config) {
        this.config = config;

        this.environment.getConfigManager().saveConfig(this.config, this.category.getDatabase(), this.handle.getCategory(), this.handle.getName());
    }

    public void updateConfig() {
        if (this.config == null) {
            return;
        }

        this.environment.getConfigManager().deleteConfig(this.category.getDatabase(), this.initialHandle.getCategory(), this.initialHandle.getName());
        this.environment.getConfigManager().saveConfig(this.config, this.category.getDatabase(), this.handle.getCategory(), this.handle.getName());
    }

    public void deleteConfig() {
        this.environment.getConfigManager().deleteConfig(this.category.getDatabase(), this.handle.getCategory(), this.handle.getName());
    }

    public void transferConfig() {
        if (this.environment == Environment.DEVELOPMENT && this.config != null) {
            Environment.PRODUCTION.getConfigManager().saveConfig(this.config, this.category.getDatabase(), this.handle.getCategory(), this.handle.getName());
        }
    }

}
