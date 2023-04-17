package fr.hyriode.build.map.config.models;

import fr.hyriode.build.map.config.ConfigData;
import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.build.map.config.models.nested.WRConfig;
import fr.hyriode.hyrame.utils.AreaWrapper;
import fr.hyriode.hyrame.utils.LocationWrapper;

/**
 * Created by AstFaster
 * on 17/04/2023 at 11:31
 */
@ConfigData(name = "PearlControl")
public class PearlControlConfig extends GameConfig {

    @ConfigOption(
            type = ConfigOptionType.PLAYER_LOCATION,
            id = "spawn",
            name = "Spawn des joueurs",
            description = "Lieu d'apparation des joueurs quand la partie démarre et quand ils respawn."
    )
    private LocationWrapper spawn;

    @ConfigOption(
            type = ConfigOptionType.AREA,
            id = "game-area",
            name = "Zone de jeu",
            description = "Représente la zone de jeu."
    )
    private AreaWrapper gameArea;

    @ConfigOption(
            type = ConfigOptionType.AREA,
            id = "middle-area",
            name = "Zone de capture",
            description = "Représente la zone à capturer pour gagner la partie."
    )
    private AreaWrapper middleArea;

    public PearlControlConfig() {
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("network-leveling", "pearlcontrol-experience"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("pearlcontrol", "captured-areas"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("pearlcontrol", "victories"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("pearlcontrol", "kills"));
    }

}
