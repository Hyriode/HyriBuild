package fr.hyriode.build.map.config.models;

import fr.hyriode.build.map.config.ConfigData;
import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionCategory;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.build.map.config.models.nested.WRConfig;
import fr.hyriode.hyrame.utils.AreaWrapper;
import fr.hyriode.hyrame.utils.LocationWrapper;

import java.util.List;

/**
 * Created by AstFaster
 * on 20/03/2023 at 11:01
 */
@ConfigData(name = "RushTheFlag")
public class RushTheFlagConfig extends GameConfig {

    @ConfigOption(
            type = ConfigOptionType.AREA,
            id = "game-area",
            name = "Zone de jeu",
            description = "Représente la zone de jeu."
    )
    private AreaWrapper area;

    @ConfigOptionCategory(
            id = "first-team",
            name = "Première équipe"
    )
    private Team firstTeam = new Team();

    @ConfigOptionCategory(
            id = "second-team",
            name = "Deuxième équipe"
    )
    private Team secondTeam = new Team();

    public static class Team {

        @ConfigOption(
                type = ConfigOptionType.PLAYER_LOCATION,
                id = "team-spawn",
                name = "Spawn de l'équipe",
                description = "Lieu d'apparation des joueurs de l'équipe quand la partie démarre."
        )
        private LocationWrapper spawn;

        @ConfigOption(
                type = ConfigOptionType.AREA,
                id = "team-spawn-area",
                name = "Zone du spawn de l'équipe",
                description = "Zone représentant le spawn d'une équipe."
        )
        private AreaWrapper spawnArea;

        @ConfigOption(
                type = ConfigOptionType.BLOCK_LOCATIONS,
                id = "team-flags",
                name = "Drapeaux de l'équipe",
                description = "Positions des drapeaux de l'équipe."
        )
        private List<LocationWrapper> flags;

    }

    public RushTheFlagConfig() {
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("network-leveling", "rushtheflag-experience"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("rushtheflag", "flags-brought-back"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("rushtheflag", "victories"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("rushtheflag", "kills"));
    }

}
