package fr.hyriode.build.map.config.models;

import fr.hyriode.build.map.config.ConfigData;
import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionCategory;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.hyrame.utils.AreaWrapper;
import fr.hyriode.hyrame.utils.LocationWrapper;

import java.util.List;

/**
 * Created by AstFaster
 * on 15/03/2023 at 07:10
 */
@ConfigData(name = "SheepWars")
public class SheepWarsConfig extends GameConfig {

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

    @ConfigOption(
            type = ConfigOptionType.INTEGER,
            id = "minmum-y",
            name = "Y minimum",
            description = "Position Y à partir de laquelle les joueurs meurent."
    )
    private int minY;

    @ConfigOption(
            type = ConfigOptionType.AREA,
            id = "bonus-area",
            name = "Zone d'apparition des bonus",
            description = "Zone dans laquelle pourront apparaître les bonus."
    )
    private AreaWrapper bonusArea;

    public static class Team {

        @ConfigOption(
                type = ConfigOptionType.PLAYER_LOCATIONS,
                id = "team-spawns",
                name = "Spawns de l'équipe",
                description = "Lieux d'apparation des joueurs de l'équipe quand la partie démarre."
        )
        private List<LocationWrapper> spawns;

    }

}
