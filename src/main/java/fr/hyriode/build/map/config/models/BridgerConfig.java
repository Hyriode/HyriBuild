package fr.hyriode.build.map.config.models;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.build.map.config.ConfigData;
import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.hyrame.utils.AreaWrapper;
import fr.hyriode.hyrame.utils.LocationWrapper;

/**
 * Created by AstFaster
 * on 01/04/2023 at 13:33
 */
@ConfigData(name = "Bridger")
public class BridgerConfig implements IHyriConfig {

    @ConfigOption(
            type = ConfigOptionType.PLAYER_LOCATION,
            id = "island-spawn",
            name = "Spawn",
            description = "Spawn où apparaîtront les joueurs sur l'île."
    )
    private LocationWrapper islandSpawn;

    @ConfigOption(
            type = ConfigOptionType.PLAYER_LOCATION,
            id = "island-npc",
            name = "NPC",
            description = "Position du NPC permettant aux joueurs de changer de blocs, d'îles, etc."
    )
    private LocationWrapper islandNpc;

    @ConfigOption(
            type = ConfigOptionType.LOCATION,
            id = "island-hologram",
            name = "Hologramme",
            description = "Position de l'hologramme contenant les informations du joueur."
    )
    private LocationWrapper islandHologram;

    @ConfigOption(
            type = ConfigOptionType.AREA,
            id = "island-area",
            name = "Zone de l'île",
            description = "Zone dans laquelle les joueurs pourront intéragir."
    )
    private AreaWrapper islandArea;

    @ConfigOption(
            type = ConfigOptionType.INTEGER,
            id = "minimum-y",
            name = "Y minimum",
            description = "Position Y minimum avant d'être re-téléporté."
    )
    private double minY;

    @ConfigOption(
            type = ConfigOptionType.LOCATIONS_DIFFERENCE,
            id = "islands-difference",
            name = "Ecart entre les îles",
            description = "Définit l'écart entre 2 îles en prenant 1 location précise sur chacune des îles."
    )
    private LocationWrapper diffBetweenIslands;

}
