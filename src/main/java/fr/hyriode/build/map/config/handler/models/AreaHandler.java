package fr.hyriode.build.map.config.handler.models;

import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.item.CLocationItem;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.title.Title;
import fr.hyriode.hyrame.utils.AreaWrapper;
import fr.hyriode.hyrame.utils.LocationWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * Created by AstFaster
 * on 01/06/2022 at 13:36
 */
public class AreaHandler extends ConfigOptionHandler<AreaWrapper> {

    private LocationWrapper min;

    public AreaHandler(ConfigProcess<?> process) {
        super(process);
    }

    @Override
    public void handle() {
        IHyrame.get().getItemManager().giveItem(this.player, 0, CLocationItem.class);

        this.process.sendMessage(builder -> builder.append("§7Première §bposition §7de la §bzone §7à définir."));
    }

    public void provideLocation(Location location) {
        final LocationWrapper newLocation = new LocationWrapper(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        Title.sendTitle(this.player,
                ChatColor.AQUA + (this.min == null ? "Première position" : "Deuxième position"),
                "X: " + newLocation.getX() + "; Y: " + newLocation.getY() + "; Z: " + newLocation.getZ(),
                2, 25, 2);

        if (this.min == null) {
            this.min = newLocation;

            this.process.sendMessage(builder -> builder.append("§7Deuxième §bposition §7de la §bzone §7à définir."));
        } else {
            this.player.getInventory().clear();

            this.complete(new AreaWrapper(this.min, newLocation));
        }
    }

}
