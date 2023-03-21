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
import java.util.Map;

/**
 * Created by AstFaster
 * on 14/03/2023 at 19:03
 */
public class WRLeaderboardsHandler extends ConfigOptionHandler<Map<String, LocationWrapper>> {

    private WRConfig waitingRoom;

    private String currentLeaderboard;
    private int index = 0;

    public WRLeaderboardsHandler(ConfigProcess<?> process) {
        super(process);
    }

    @Override
    public void handle() {
        this.waitingRoom = ((GameConfig) this.process.getConfig()).getWaitingRoom();

        if (this.waitingRoom.getLeaderboards().size() > 0) {
            this.currentLeaderboard = new ArrayList<>(this.waitingRoom.getLeaderboards().keySet()).get(this.index);

            this.process.sendMessage(builder -> builder.append("§7Classement à définir: §b" + this.currentLeaderboard + "§7."));

            Title.sendTitle(this.player,
                    ChatColor.AQUA + "Classement " + this.index + "/" + this.waitingRoom.getLeaderboards().size(),
                    this.currentLeaderboard,
                    2, 25, 2);

            IHyrame.get().getItemManager().giveItem(this.player, 0, CLocationItem.class);
            return;
        }

        this.complete(this.waitingRoom.getLeaderboards());
    }

    public void provideLocation(Location location) {
        location.add(location.getBlockX() > 0 ? 0.5 : -0.5, 0.0, location.getBlockZ() > 0 ? 0.5 : -0.5);

        final LocationWrapper result = new LocationWrapper(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        this.currentLeaderboard = new ArrayList<>(this.waitingRoom.getLeaderboards().keySet()).get(this.index);
        this.index += 1;

        this.process.sendMessage(builder -> builder.append("§7Classement à définir: §b" + this.currentLeaderboard + "§7."));

        Title.sendTitle(this.player,
                ChatColor.AQUA + "Classement " + this.index + "/" + this.waitingRoom.getLeaderboards().size(),
                this.currentLeaderboard,
                2, 25, 2);

        this.waitingRoom.getLeaderboards().put(currentLeaderboard, result);

        if (this.index == this.waitingRoom.getLeaderboards().size()) {
            this.player.getInventory().clear();

            this.complete(this.waitingRoom.getLeaderboards());
        }
    }

}
