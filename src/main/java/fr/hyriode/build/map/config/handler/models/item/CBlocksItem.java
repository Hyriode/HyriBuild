package fr.hyriode.build.map.config.handler.models.item;

import fr.hyriode.api.language.HyriLanguageMessage;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.ConfigStep;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.*;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.inventory.HyriInventory;
import fr.hyriode.hyrame.item.HyriItem;
import fr.hyriode.hyrame.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by AstFaster
 * on 02/06/2022 at 18:41
 */
public class CBlocksItem extends HyriItem<HyriBuild> {

    public CBlocksItem(HyriBuild plugin) {
        super(plugin, "config.blocks", () -> HyriLanguageMessage.from("§bDéfinir les blocs §7(Clic-droit)"), null, Material.NETHER_STAR);
    }

    @Override
    public void onRightClick(IHyrame hyrame, PlayerInteractEvent event) {
        new GUI(event.getPlayer()).open();
    }

    private static class GUI extends HyriInventory {

        public GUI(Player owner) {
            super(owner, "Ajouter un item", 9);

            this.setHorizontalLine(0, 8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 9).withName(" ").build());
            this.setItem(4, null);
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            final ItemStack item = event.getCursor();

            if (event.getClickedInventory() instanceof PlayerInventory) {
                event.setCancelled(false);
            }

            if (event.getSlot() == 4 && item != null && item.getType() != Material.AIR && item.getType().isBlock()) {
                final Player player = (Player) event.getWhoClicked();
                final ConfigProcess<?> process = HyriBuild.get().getConfigManager().getProcess(player.getUniqueId());

                if (process == null) {
                    return;
                }

                final ConfigStep step = process.currentStep();

                if (step == null) {
                    return;
                }

                ((BlocksHandler) step.getHandler()).provideBlock(item);
            }
        }
    }

}
