package fr.hyriode.build.map;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.api.config.IHyriConfigManager;
import fr.hyriode.api.world.IHyriWorldManager;
import fr.hyriode.build.HyriBuild;

import java.util.function.Supplier;

/**
 * Created by AstFaster
 * on 23/02/2023 at 13:38
 */
public enum Environment {

    ALL("Tous", null, null),
    PRODUCTION("Production", () -> HyriBuild.get().getProdWorldManager(), () -> HyriBuild.get().getProdConfigManager()),
    DEVELOPMENT("Développement", () -> HyriAPI.get().getWorldManager(), () -> HyriAPI.get().getConfigManager());

    private final String display;
    private final Supplier<IHyriWorldManager> worldManagerSupplier;
    private final Supplier<IHyriConfigManager> configManagerSupplier;

    Environment(String display, Supplier<IHyriWorldManager> worldManagerSupplier, Supplier<IHyriConfigManager> configManagerSupplier) {
        this.display = display;
        this.worldManagerSupplier = worldManagerSupplier;
        this.configManagerSupplier = configManagerSupplier;
    }

    public String getDisplay() {
        return this.display;
    }

    public IHyriWorldManager getWorldManager() {
        return this.worldManagerSupplier.get();
    }

    public IHyriConfigManager getConfigManager() {
        return this.configManagerSupplier.get();
    }

}
