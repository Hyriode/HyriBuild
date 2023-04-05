package fr.hyriode.build.map.config.models.getdown;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.build.map.config.ConfigData;
import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.build.map.config.models.nested.BlockTexture;
import fr.hyriode.hyrame.utils.AreaWrapper;
import fr.hyriode.hyrame.utils.LocationWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AstFaster
 * on 02/04/2023 at 21:10
 */
@ConfigData(name = "GetDown - Jump")
public class GDJumpConfig implements IHyriConfig {

    @ConfigOption(
            type = ConfigOptionType.PLAYER_LOCATION,
            id = "players-spawn",
            name = "Spawn des joueurs",
            description = "Spawn où apparaîtront les joueurs."
    )
    private LocationWrapper spawn;

    @ConfigOption(
            type = ConfigOptionType.BLOCKS,
            id = "blocks",
            name = "Blocs",
            description = "Définis les blocs qui seront générés sur la map."
    )
    private List<BlockTexture> blocks = new ArrayList<>();

    @ConfigOption(
            type = ConfigOptionType.AREA,
            id = "area",
            name = "Zone de jump",
            description = "Zone où les blocs seront générés."
    )
    private AreaWrapper area;

    @ConfigOption(
            type = ConfigOptionType.INTEGER,
            id = "maximum-attack-height",
            name = "Hauteur de dégâts",
            description = "Hauteur à partir de laquelle les joueurs pourront se taper."
    )
    private int maximumAttackHeight;

    @ConfigOption(
            type = ConfigOptionType.INTEGER,
            id = "end-height",
            name = "Hauteur de fin",
            description = "Hauteur à partir de laquelle un joueur est considéré comme 'en bas'."
    )
    private int endHeight;

}
