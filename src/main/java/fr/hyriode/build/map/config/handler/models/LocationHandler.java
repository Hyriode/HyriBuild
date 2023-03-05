package fr.hyriode.build.map.config.handler.models;

import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.item.CLocationItem;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.utils.LocationWrapper;
import org.bukkit.Location;

/**
 * Created by AstFaster
 * on 01/06/2022 at 13:36
 */
public class LocationHandler extends ConfigOptionHandler<LocationWrapper> {

    private final boolean direction;
    private final boolean rounded;

    public LocationHandler(ConfigProcess<?> process, boolean direction, boolean rounded) {
        super(process);
        this.direction = direction;
        this.rounded = rounded;
    }

    @Override
    public void handle() {
        IHyrame.get().getItemManager().giveItem(this.player, 0, CLocationItem.class);
    }

    public void provideLocation(Location location) {
        final LocationWrapper result = new LocationWrapper(
                this.rounded ? location.getBlockX() : location.getX(),
                this.rounded ? location.getBlockY() : location.getY(),
                this.rounded ? location.getBlockZ() : location.getZ());

        if (this.direction) {
            result.setYaw(location.getYaw());
            result.setPitch(location.getPitch());
        }

        this.player.getInventory().clear();

        this.complete(result);
    }


}
