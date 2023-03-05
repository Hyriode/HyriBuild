package fr.hyriode.build.map.config.models;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.build.map.config.ConfigData;
import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionCategory;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.build.map.config.models.nested.WRConfig;
import fr.hyriode.hyrame.utils.LocationWrapper;

import java.util.List;

/**
 * Created by AstFaster
 * on 23/07/2022 at 11:48
 */
@ConfigData(name = "Moutron")
public class MoutronConfig implements IHyriConfig {

    @ConfigOptionCategory(
            id = "wr",
            name = "Waiting Room"
    )
    private WRConfig waitingRoom = new WRConfig();

    @ConfigOption(
            type = ConfigOptionType.PLAYER_LOCATIONS,
            id = "spawns",
            name = "Spawns",
            description = "Lieux d'apparation des joueurs quand la partie démarre."
    )
    private List<LocationWrapper> spawns;

}
