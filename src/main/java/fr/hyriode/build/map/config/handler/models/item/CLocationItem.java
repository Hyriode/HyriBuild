package fr.hyriode.build.map.config.handler.models.item;

import fr.hyriode.api.language.HyriLanguageMessage;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.ConfigStep;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.*;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.item.HyriItem;
import fr.hyriode.hyrame.utils.Symbols;
import jdk.nashorn.internal.ir.Symbol;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by AstFaster
 * on 02/06/2022 at 18:41
 */
public class CLocationItem extends HyriItem<HyriBuild> {

    public CLocationItem(HyriBuild plugin) {
        super(plugin, "config.location", () -> HyriLanguageMessage.from("§bDéfinir la location §7(Clic-droit)"), null, Material.NETHER_STAR);
    }

    @Override
    public void onRightClick(IHyrame hyrame, PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ConfigProcess<?> process = HyriBuild.get().getConfigManager().getProcess(player.getUniqueId());

        if (process == null) {
            return;
        }

        final ConfigStep step = process.currentStep();

        if (step == null) {
            return;
        }

        final ConfigOptionHandler<?> handler = step.getHandler();

        if (handler instanceof ILocationConsumer) {
            ((ILocationConsumer) handler).provideLocation(player.getLocation());
        }
    }

}
