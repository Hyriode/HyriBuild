package fr.hyriode.build.map.config.handler.models;

import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.item.CLocationItem;
import fr.hyriode.build.map.config.models.GameConfig;
import fr.hyriode.build.map.config.models.nested.WRConfig;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.title.Title;
import fr.hyriode.hyrame.utils.LocationWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by AstFaster
 * on 14/03/2023 at 19:03
 */
public class WRLeaderboardsHandler extends ConfigOptionHandler<List<WRConfig.Leaderboard>> implements ILocationConsumer {

    private WRConfig waitingRoom;

    private final List<WRConfig.Leaderboard> result = new ArrayList<>();

    private WRConfig.Leaderboard currentLeaderboard;
    private int index = 0;

    public WRLeaderboardsHandler(ConfigProcess<?> process) {
        super(process);
    }

    @Override
    public void handle() {
        this.waitingRoom = ((GameConfig) this.process.getConfig()).getWaitingRoom();

        if (this.waitingRoom.getLeaderboards().size() > 0) {
            this.currentLeaderboard = this.waitingRoom.getLeaderboards().get(this.index);

            this.process.sendMessage(builder -> builder.append("§7Classement à définir: §b" + this.currentLeaderboard.getName() + "§7."));

            Title.sendTitle(this.player,
                    ChatColor.AQUA + "Classement " + this.index + "/" + this.waitingRoom.getLeaderboards().size(),
                    this.currentLeaderboard.getName(),
                    2, 25, 2);

            IHyrame.get().getItemManager().giveItem(this.player, 0, CLocationItem.class);
            return;
        }

        this.complete(this.waitingRoom.getLeaderboards());
    }

    @Override
    public void provideLocation(Location location) {
        location.setX(location.getBlockX() + (location.getBlockX() > 0 ? 0.5 : -0.5));
        location.setZ(location.getBlockZ() + (location.getBlockZ() > 0 ? 0.5 : -0.5));

        final LocationWrapper result = new LocationWrapper(location);

        this.result.add(new WRConfig.Leaderboard(this.currentLeaderboard.getType(), this.currentLeaderboard.getName(), result));

        this.index += 1;

        if (this.index == this.waitingRoom.getLeaderboards().size()) {
            this.player.getInventory().clear();

            this.complete(this.result);
        } else {
            this.currentLeaderboard = this.waitingRoom.getLeaderboards().get(this.index);

            this.process.sendMessage(builder -> builder.append("§7Classement à définir: §b" + this.currentLeaderboard.getName() + "§7."));

            Title.sendTitle(this.player,
                    ChatColor.AQUA + "Classement " + this.index + "/" + this.waitingRoom.getLeaderboards().size(),
                    this.currentLeaderboard.getName(),
                    2, 25, 2);
        }
    }

}
