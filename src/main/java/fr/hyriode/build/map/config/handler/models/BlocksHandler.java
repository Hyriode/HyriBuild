package fr.hyriode.build.map.config.handler.models;

import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.item.CBlocksItem;
import fr.hyriode.build.map.config.handler.models.item.CLocationItem;
import fr.hyriode.build.map.config.models.nested.BlockTexture;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.signgui.SignGUI;
import fr.hyriode.hyrame.title.Title;
import fr.hyriode.hyrame.utils.LocationWrapper;
import fr.hyriode.hyrame.utils.PrimitiveType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AstFaster
 * on 01/06/2022 at 13:36
 */
public class BlocksHandler extends ConfigOptionHandler<List<BlockTexture>> {

    private final List<BlockTexture> result = new ArrayList<>();

    private int size;

    public BlocksHandler(ConfigProcess<?> process) {
        super(process);
    }

    @Override
    public void handle() {
        new SignGUI((player, lines) -> {
            final String input = lines[0];

            if (PrimitiveType.INTEGER.isValid(input) && PrimitiveType.INTEGER.parse(input) > 0) {
                this.size = PrimitiveType.INTEGER.parse(input);

                IHyrame.get().getItemManager().giveItem(this.player, 0, CBlocksItem.class);
            } else {
                this.process.sendMessage(builder -> builder.append("§fLe nombre maximal de §bblocs §fest §cinvalide §f!"));

                Bukkit.getScheduler().runTaskLater(HyriBuild.get(), this::handle, 1L);
            }
        }).withLines("", "^^^^^^^^", "Nombre de", "blocs").open(this.player);
    }

    @SuppressWarnings("deprecation")
    public void provideBlock(ItemStack block) {
        this.player.closeInventory();

        this.result.add(new BlockTexture(block.getType(), block.getData().getData()));

        Title.sendTitle(this.player,
                ChatColor.AQUA + String.valueOf(this.result.size()) + "/" + this.size,
                "",
                2, 25, 2);

        if (this.result.size() == this.size) {
            this.player.getInventory().clear();

            this.complete(this.result);
        }
    }

}
