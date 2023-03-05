package fr.hyriode.build.map.config.handler.models.item.bool;

import fr.hyriode.api.language.HyriLanguageMessage;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.ConfigStep;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.BooleanHandler;
import fr.hyriode.hyrame.IHyrame;
import fr.hyriode.hyrame.item.HyriItem;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by AstFaster
 * on 03/06/2022 at 19:44
 */
public abstract class CBooleanItem extends HyriItem<HyriBuild> {

    private final boolean value;

    public CBooleanItem(HyriBuild plugin, String name, String displayName, byte data, boolean value) {
        super(plugin, name, () -> HyriLanguageMessage.from(displayName), null, new ItemStack(Material.INK_SACK, 1, data));
        this.value = value;
    }

    @Override
    public void onRightClick(IHyrame hyrame, PlayerInteractEvent event) {
        final ConfigProcess<?> process = HyriBuild.get().getConfigManager().getProcess(event.getPlayer().getUniqueId());

        if (process == null) {
            return;
        }

        final ConfigStep step = process.currentStep();

        if (step == null) {
            return;
        }

        final ConfigOptionHandler<?> handler = step.getHandler();

        if (handler instanceof BooleanHandler) {
            ((BooleanHandler) handler).complete(this.value);
        }
    }


}
