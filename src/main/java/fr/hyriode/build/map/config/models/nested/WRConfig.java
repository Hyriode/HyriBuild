package fr.hyriode.build.map.config.models.nested;

import fr.hyriode.build.map.config.ConfigOption;
import fr.hyriode.build.map.config.ConfigOptionType;
import fr.hyriode.hyrame.utils.LocationWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ConfigOption(
            type = ConfigOptionType.WAITING_ROOM_LEADERBOARDS,
            id = "wr-leaderboards",
            name = "Classements waiting room",
            description = "Lieux où seront placés les classements du mini-jeu."
    )
    private List<Leaderboard> leaderboards = new ArrayList<>();

    public void registerLeaderboard(Leaderboard leaderboard) {
        this.leaderboards.add(leaderboard);
    }

    public List<Leaderboard> getLeaderboards() {
        return this.leaderboards;
    }

    public static class Leaderboard {

        private final String type;
        private final String name;
        private LocationWrapper location;

        public Leaderboard(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public Leaderboard(String type, String name, LocationWrapper location) {
            this.type = type;
            this.name = name;
            this.location = location;
        }

        public String getType() {
            return this.type;
        }

        public String getName() {
            return this.name;
        }

        public LocationWrapper getLocation() {
            return this.location;
        }

    }

}
