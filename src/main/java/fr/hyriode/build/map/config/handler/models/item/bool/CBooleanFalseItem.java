package fr.hyriode.build.map.config.handler.models.item.bool;

import fr.hyriode.build.HyriBuild;
import fr.hyriode.hyrame.utils.Symbols;
import org.bukkit.ChatColor;

/**
 * Created by AstFaster
 * on 03/06/2022 at 11:05
 */
public class CBooleanFalseItem extends CBooleanItem {

    public CBooleanFalseItem(HyriBuild plugin) {
        super(plugin, "config.boolean-no", ChatColor.RED + Symbols.CROSS_STYLIZED_BOLD + "Non §7(Clic-droit)", (byte) 1, false);
    }

}
