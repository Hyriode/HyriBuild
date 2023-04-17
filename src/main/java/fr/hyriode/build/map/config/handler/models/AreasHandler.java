package fr.hyriode.build.map.config.handler.models;

import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.item.CLocationItem;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.signgui.SignGUI;
import fr.hyriode.hyrame.title.Title;
import fr.hyriode.hyrame.utils.AreaWrapper;
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
public class AreasHandler extends ConfigOptionHandler<List<AreaWrapper>> implements ILocationConsumer {

    private final List<AreaWrapper> result = new ArrayList<>();

    private AreaWrapper current;

    private int size;

    public AreasHandler(ConfigProcess<?> process) {
        super(process);
    }

    @Override
    public void handle() {
        new SignGUI((player, lines) -> {
            final String input = lines[0];

            if (PrimitiveType.INTEGER.isValid(input) && PrimitiveType.INTEGER.parse(input) >= 0) {
                this.size = PrimitiveType.INTEGER.parse(input);

                if (this.size == 0) {
                    this.complete(new ArrayList<>());
                    return;
                }

                IHyrame.get().getItemManager().giveItem(this.player, 0, CLocationItem.class);

                this.process.sendMessage(builder -> builder.append("§7Première §bposition §7de la §bzone §7à définir. (index: " + (this.result.size() + 1) + "/" + this.size + ")."));
            } else {
                this.process.sendMessage(builder -> builder.append("§fLe nombre maximal de §blocations §fest §cinvalide §f!"));

                Bukkit.getScheduler().runTaskLater(HyriBuild.get(), this::handle, 1L);
            }
        }).withLines("", "^^^^^^^^", "Nombre de", "zone").open(this.player);

        IHyrame.get().getItemManager().giveItem(this.player, 0, CLocationItem.class);
    }

    @Override
    public void provideLocation(Location location) {
        final LocationWrapper newLocation = new LocationWrapper(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        if (this.current == null) {
            this.current = new AreaWrapper(newLocation, null);

            Title.sendTitle(this.player,
                    ChatColor.AQUA + "Première position",
                    "X: " + newLocation.getX() + "; Y: " + newLocation.getY() + "; Z: " + newLocation.getZ(),
                    2, 25, 2);

            this.process.sendMessage(builder -> builder.append("§7Deuxième §bposition §7de la §bzone §7à définir. (index: " + this.result.size() + "/" + this.size + ")."));
            return;
        } else {
            Title.sendTitle(this.player,
                    ChatColor.AQUA + "Deuxième position",
                    "X: " + newLocation.getX() + "; Y: " + newLocation.getY() + "; Z: " + newLocation.getZ(),
                    2, 25, 2);

            this.current = new AreaWrapper(this.current.getMin(), newLocation);
            this.result.add(this.current);
            this.current = null;
        }

        if (this.result.size() == this.size) {
            this.player.getInventory().clear();

            this.complete(this.result);
        }
    }

}
