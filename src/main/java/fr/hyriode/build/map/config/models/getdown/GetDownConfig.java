package fr.hyriode.build.map.config.models.getdown;

import fr.hyriode.build.map.config.ConfigData;
import fr.hyriode.build.map.config.models.GameConfig;
import fr.hyriode.build.map.config.models.nested.WRConfig;

/**
 * Created by AstFaster
 * on 02/04/2023 at 21:06
 */
@ConfigData(name = "GetDown")
public class GetDownConfig extends GameConfig {

    public GetDownConfig() {
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("network-leveling", "rotating-game-experience"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("getdown", "successful-jumps"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("getdown", "victories"));
        this.getWaitingRoom().registerLeaderboard(new WRConfig.Leaderboard("getdown", "kills"));
    }

}
