package fr.hyriode.build.map.config.handler.models;

import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.item.CLocationItem;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.title.Title;
import fr.hyriode.hyrame.utils.LocationWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * Created by AstFaster
 * on 01/06/2022 at 13:36
 */
public class LocationsDifferenceHandler extends ConfigOptionHandler<LocationWrapper> {

    private LocationWrapper first;

    public LocationsDifferenceHandler(ConfigProcess<?> process) {
        super(process);
    }

    @Override
    public void handle() {
        IHyrame.get().getItemManager().giveItem(this.player, 0, CLocationItem.class);
    }

    public void provideLocation(Location location) {
        final LocationWrapper newLocation = new LocationWrapper(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ());

        if (this.first == null) {
            this.first = newLocation;

            Title.sendTitle(this.player,
                    ChatColor.AQUA + "Première location",
                    "X: " + newLocation.getX() + "; Y: " + newLocation.getY() + "; Z: " + newLocation.getZ(),
                    2, 25, 2);
        } else {
            Title.sendTitle(this.player,
                    ChatColor.AQUA + "Deuxième location",
                    "X: " + newLocation.getX() + "; Y: " + newLocation.getY() + "; Z: " + newLocation.getZ(),
                    2, 25, 2);

            this.player.getInventory().clear();

            this.complete(new LocationWrapper(newLocation.getX() - this.first.getX(), newLocation.getY() - this.first.getY(), newLocation.getZ() - this.first.getZ()));
        }
    }

}
