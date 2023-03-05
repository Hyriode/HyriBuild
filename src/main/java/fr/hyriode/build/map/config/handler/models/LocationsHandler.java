package fr.hyriode.build.map.config.handler.models;

import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.item.CLocationItem;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.signgui.SignGUI;
import fr.hyriode.hyrame.title.Title;
import fr.hyriode.hyrame.utils.LocationWrapper;
import fr.hyriode.hyrame.utils.PrimitiveType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AstFaster
 * on 01/06/2022 at 13:36
 */
public class LocationsHandler extends ConfigOptionHandler<List<LocationWrapper>> {

    private final List<LocationWrapper> result = new ArrayList<>();

    private int size;

    private final boolean direction;
    private final boolean rounded;

    public LocationsHandler(ConfigProcess<?> process, boolean direction, boolean rounded) {
        super(process);
        this.direction = direction;
        this.rounded = rounded;
    }

    @Override
    public void handle() {
        new SignGUI((player, lines) -> {
            final String input = lines[0];

            if (PrimitiveType.INTEGER.isValid(input) && PrimitiveType.INTEGER.parse(input) > 0) {
                this.size = PrimitiveType.INTEGER.parse(input);

                IHyrame.get().getItemManager().giveItem(this.player, 0, CLocationItem.class);
            } else {
                this.process.sendMessage(builder -> builder.append("§fLe nombre maximal de §blocations §fest §cinvalide §f!"));

                Bukkit.getScheduler().runTaskLater(HyriBuild.get(), this::handle, 1L);
            }
        }).withLines("", "^^^^^^^^", "Nombre de", "locations").open(this.player);
    }

    public void provideLocation(Location location) {
        final LocationWrapper newLocation = new LocationWrapper(
                this.rounded ? location.getBlockX() : location.getX(),
                this.rounded ? location.getBlockY() : location.getY(),
                this.rounded ? location.getBlockZ() : location.getZ());

        if (this.direction) {
            newLocation.setYaw(location.getYaw());
            newLocation.setPitch(location.getPitch());
        }

        this.result.add(newLocation);

        Title.sendTitle(this.player,
                ChatColor.AQUA + String.valueOf(this.result.size()) + "/" + this.size,
                "X: " + newLocation.getX() + "; Y: " + newLocation.getY() + "; Z: " + newLocation.getZ(),
                2, 25, 2);

        if (this.result.size() == this.size) {
            this.player.getInventory().clear();

            this.complete(this.result);
        }
    }

}
