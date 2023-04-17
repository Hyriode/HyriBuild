package fr.hyriode.build.map.config.models.bedwars;

import fr.hyriode.build.map.config.*;
import fr.hyriode.build.map.config.models.GameConfig;
import fr.hyriode.hyrame.utils.AreaWrapper;
import fr.hyriode.hyrame.utils.LocationWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AstFaster
 * on 15/04/2023 at 19:19
 */
@ConfigData(name = "BedWars Trio")
public class BedWarsConfig_TRIO extends GameConfig {

    @ConfigOptionCategory(id = "teams", name = "Equipes")
    private final List<Team> teams = new ArrayList<Team>(){{
        this.add(new Team("red", "§cRouge"));
        this.add(new Team("blue", "§9Bleu"));
        this.add(new Team("green", "§aVert"));
        this.add(new Team("yellow", "§eJaune"));
    }};

    @ConfigOption(
            type = ConfigOptionType.AREA,
            id = "game-area",
            name = "Zone de jeu",
            description = "Défini la zone de jeu."
    )
    private AreaWrapper gameArea;

    private final List<Generator> generatorsBase = new ArrayList<Generator>(){{
        this.add(new Generator(0,
                new Generator.Drop(Generator.Item.IRON, 30),
                new Generator.Drop(Generator.Item.GOLD, 5 * 20)));
        this.add(new Generator(1,
                new Generator.Drop(Generator.Item.IRON, 23),
                new Generator.Drop(Generator.Item.GOLD, 3 * 20)));
        this.add(new Generator(2,
                new Generator.Drop(Generator.Item.IRON, 18),
                new Generator.Drop(Generator.Item.GOLD, 2 * 20),
                new Generator.Drop(Generator.Item.EMERALD, 70 * 20)));
        this.add(new Generator(3,
                new Generator.Drop(Generator.Item.IRON, 15),
                new Generator.Drop(Generator.Item.GOLD, 30),
                new Generator.Drop(Generator.Item.EMERALD, 60 * 20)));
    }};
    private final List<Generator> generatorsDiamond = new ArrayList<Generator>(){{
        this.add(new Generator(0, new Generator.Drop(Generator.Item.DIAMOND, 30 * 20)));
        this.add(new Generator(1, new Generator.Drop(Generator.Item.DIAMOND, 23 * 20)));
        this.add(new Generator(2, new Generator.Drop(Generator.Item.DIAMOND, 12 * 20)));
    }};
    private final List<Generator> generatorsEmerald = new ArrayList<Generator>(){{
        this.add(new Generator(0, new Generator.Drop(Generator.Item.EMERALD, 65 * 20)));
        this.add(new Generator(1, new Generator.Drop(Generator.Item.EMERALD, 50 * 20)));
        this.add(new Generator(2, new Generator.Drop(Generator.Item.EMERALD, 30 * 20)));
    }};

    @ConfigOption(
            type = ConfigOptionType.LOCATIONS,
            id = "diamond-generators",
            name = "Générateurs de Diamant",
            description = "Défini la position des générateurs de diamants."
    )
    private final List<LocationWrapper> diamondGeneratorLocations = new ArrayList<>();

    @ConfigOption(
            type = ConfigOptionType.LOCATIONS,
            id = "emerald-generators",
            name = "Générateurs d'Emeraude",
            description = "Défini la position des générateurs d'émeraudes."
    )
    private final List<LocationWrapper> emeraldGeneratorLocations = new ArrayList<>();

    @ConfigOption(
            type = ConfigOptionType.AREAS,
            id = "protection-areas",
            name = "Zones de de protection (optionels)",
            description = "Défini des zones de protection optionnels sur la map."
    )
    private final List<AreaWrapper> protectionArea = new ArrayList<>();

    public static class Generator {

        private final int tier;
        private final List<Drop> drops;

        public Generator(int tier, Drop... drops) {
            this.tier = tier;
            this.drops = Arrays.asList(drops);
        }

        public static class Drop {

            private final Item itemName;
            private final int spawnBetween;

            public Drop(Item itemName, int spawnBetween) {
                this.itemName = itemName;
                this.spawnBetween = spawnBetween;
            }

        }

        public enum Item {
            IRON, GOLD, EMERALD, DIAMOND
        }

    }

    public static class Team {

        private final String name;

        @ConfigListMemberId
        private final transient String display;

        @ConfigOption(
                type = ConfigOptionType.PLAYER_LOCATION,
                id = "spawn",
                name = "Spawn de la base",
                description = "Défini la position où apparaîtront les joueurs de l'équipe."
        )
        private LocationWrapper respawnLocation;

        @ConfigOption(
                type = ConfigOptionType.AREA,
                id = "base-area",
                name = "Zone de la base",
                description = "Défini la zone de la base."
        )
        private AreaWrapper baseArea;

        @ConfigOption(
                type = ConfigOptionType.AREA,
                id = "protection-areas",
                name = "Zone de la protection de la base.",
                description = "Défini la zone de protection de la base (où les joueurs ne pourront pas poser de blocs)."
        )
        private AreaWrapper baseAreaProtection;

        @ConfigOption(
                type = ConfigOptionType.LOCATION,
                id = "generator",
                name = "Générateur de la base",
                description = "Défini la position du générateur de la base."
        )
        private LocationWrapper generatorLocation;

        @ConfigOption(
                type = ConfigOptionType.PLAYER_LOCATION,
                id = "npc-shop-location",
                name = "NPC Boutique",
                description = "Défini la position du NPC permettant d'accéder à la boutique."
        )
        private LocationWrapper shopNPCLocation;

        @ConfigOption(
                type = ConfigOptionType.PLAYER_LOCATION,
                id = "npc-upgrade-location",
                name = "NPC Améliorations",
                description = "Défini la position du NPC permettant d'accéder aux améliorations."
        )
        private LocationWrapper upgradeNPCLocation;

        public Team(String name, String display) {
            this.name = name;
            this.display = display;
        }

    }

}
