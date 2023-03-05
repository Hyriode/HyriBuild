package fr.hyriode.build.map.config.models.nested;

import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.hyrame.utils.LocationWrapper;

/**
 * Created by AstFaster
 * on 27/02/2023 at 17:17
 */
public class WRConfig {

    @ConfigOption(
            type = ConfigOptionType.PLAYER_LOCATION,
            id = "wr-spawn",
            name = "Spawn waiting room",
            description = "Lieu d'apparition des joueurs à la connexion."
    )
    private LocationWrapper spawn;

    @ConfigOption(
            type = ConfigOptionType.BLOCK_LOCATION,
            id = "wr-first-pos",
            name = "Zone waiting room (pos1)",
            description = "Définis la première position de la zone de la waiting-room. Cette valeur permet notamment de supprimer la waiting-room une fois la partie démarrée."
    )
    private LocationWrapper firstPos;

    @ConfigOption(
            type = ConfigOptionType.BLOCK_LOCATION,
            id = "wr-second-pos",
            name = "Zone waiting room (pos2)",
            description = "Définis la seconde position de la zone de la waiting-room. Cette valeur permet notamment de supprimer la waiting-room une fois la partie démarrée."
    )
    private LocationWrapper secondPos;

    @ConfigOption(
            type = ConfigOptionType.PLAYER_LOCATION,
            id = "wr-npc",
            name = "NPC waiting room",
            description = "Lieu où sera placé le NPC contenant les statistiques."
    )
    private LocationWrapper npcLocation;

}