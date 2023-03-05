package fr.hyriode.build.map.config.handler.models;

import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.build.map.config.handler.models.item.bool.CBooleanFalseItem;
import fr.hyriode.build.map.config.handler.models.item.bool.CBooleanTrueItem;
import fr.hyriode.hyrame.IHyrame;

/**
 * Created by AstFaster
 * on 03/06/2022 at 11:04
 */
public class BooleanHandler extends ConfigOptionHandler<Boolean> {

    public BooleanHandler(ConfigProcess<?> process) {
        super(process);
    }

    @Override
    public void handle() {
        IHyrame.get().getItemManager().giveItem(this.player, 0, CBooleanTrueItem.class);
        IHyrame.get().getItemManager().giveItem(this.player, 1, CBooleanFalseItem.class);
    }

    @Override
    public void complete(Boolean value) {
        this.player.getInventory().clear();

        super.complete(value);
    }

}
