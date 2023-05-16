package fr.hyriode.build.map.config.models;

import fr.hyriode.build.map.config.*;
import fr.hyriode.hyrame.utils.AreaWrapper;
import fr.hyriode.hyrame.utils.LocationWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AstFaster
 * on 17/04/2023 at 13:14
 */
@ConfigData(name = "LaserGame")
public class LaserGameConfig extends GameConfig {

    @ConfigOptionCategory(id = "teams", name = "Equipes")
    private final List<Team> teams = new ArrayList<Team>(){{
        this.add(new Team("red", "§cRouge"));
        this.add(new Team("blue", "§9Bleu"));
    }};

    @ConfigOption(
            type = ConfigOptionType.LOCATIONS,
            id = "bonuses",
            name = "Bonus",
            description = "Défini les emplacements des bonus."
    )
    private final List<LocationWrapper> bonusLocation = new ArrayList<>();

    public static class Team {

        private final String name;

        @ConfigListMemberId
        private final transient String display;

        @ConfigOption(
                type = ConfigOptionType.AREAS,
                id = "base-doors",
                name = "Portes de la base",
                description = "Défini les portes de la base."
        )
        private final List<AreaWrapper> doors = new ArrayList<>();

        @ConfigOption(
                type = ConfigOptionType.AREA,
                id = "base-area",
                name = "Zone de la base",
                description = "Défini la zone de la base."
        )
        private AreaWrapper baseArea;

        @ConfigOption(
                type = ConfigOptionType.PLAYER_LOCATION,
                id = "base-spawn",
                name = "Spawn de la base",
                description = "Défini le spawn de la base."
        )
        private LocationWrapper spawnLocation;

        @ConfigOption(
                type = ConfigOptionType.PLAYER_LOCATION,
                id = "base-spawn-door-closed",
                name = "Spawn de la base (porte fermée)",
                description = "Défini le spawn de la base une fois la porte fermée"
        )
        private LocationWrapper spawnCloseDoorLocation;

        public Team(String name, String display) {
            this.name = name;
            this.display = display;
        }

    }

}
