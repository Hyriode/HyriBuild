package fr.hyriode.build.map.config.models;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.build.map.config.ConfigOptionCategory;
import fr.hyriode.build.map.config.models.nested.WRConfig;

/**
 * Created by AstFaster
 * on 14/03/2023 at 21:31
 */
public abstract class GameConfig implements IHyriConfig {

    @ConfigOptionCategory(
            id = "wr",
            name = "Waiting Room"
    )
    private final WRConfig waitingRoom = new WRConfig();

    public WRConfig getWaitingRoom() {
        return this.waitingRoom;
    }

}
