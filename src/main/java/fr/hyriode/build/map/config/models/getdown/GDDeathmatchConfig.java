package fr.hyriode.build.map.config.models.getdown;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.build.map.config.ConfigData;
import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.hyrame.utils.LocationWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AstFaster
 * on 02/04/2023 at 21:10
 */
@ConfigData(name = "GetDown - Deathmatch")
public class GDDeathmatchConfig implements IHyriConfig {

    @ConfigOption(
            type = ConfigOptionType.PLAYER_LOCATIONS,
            id = "player-spawns",
            name = "Spawns des joueurs",
            description = "Spawns où apparaîtront les joueurs du deathmatch."
    )
    private List<LocationWrapper> playerSpawns = new ArrayList<>();

}
