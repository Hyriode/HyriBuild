package fr.hyriode.build.map.config.models;

import fr.hyriode.build.map.config.ConfigData;
import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionCategory;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.build.map.config.models.nested.WRConfig;
import fr.hyriode.hyrame.game.waitingroom.HyriWaitingRoom;
import fr.hyriode.hyrame.utils.AreaWrapper;
import fr.hyriode.hyrame.utils.LocationWrapper;

/**
 * Created by AstFaster
 * on 16/05/2023 at 21:33
 */
@ConfigData(name = "TeamFight")
public class TeamFightConfig extends GameConfig {

    @ConfigOption(
            type = ConfigOptionType.AREA,
            id = "middle-area",
            name = "Zone de capture",
            description = "Représente la zone à capturer pour gagner la partie."
    )
    private AreaWrapper middleArea;

    @ConfigOption(
            type = ConfigOptionType.AREA,
            id = "game-area",
            name = "Zone de jeu",
            description = "Représente la zone de jeu."
    )
    private AreaWrapper gameArea;

    @ConfigOption(
            type = ConfigOptionType.LOCATION,
            id = "centre",
            name = "Centre de la carte",
            description = "Centre de la carte (utile pour les fireworks)"
    )
    private LocationWrapper center;

    @ConfigOptionCategory(
            id = "first-team",
            name = "Première équipe"
    )
    private Team firstTeam = new Team();

    @ConfigOptionCategory(
            id = "second-team",
            name = "Seconde équipe"
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

    }

    public TeamFightConfig() {
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("network-leveling", "teamfight-experience"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("pearlcontrol", "captured-areas"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("pearlcontrol", "victories"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("pearlcontrol", "kills"));
    }

}
