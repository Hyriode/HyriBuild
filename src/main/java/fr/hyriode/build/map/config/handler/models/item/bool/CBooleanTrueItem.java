package fr.hyriode.build.map.config.handler.models.item.bool;

import fr.hyriode.build.HyriBuild;
import fr.hyriode.hyrame.utils.Symbols;
import org.bukkit.ChatColor;

/**
 * Created by AstFaster
 * on 03/06/2022 at 11:05
 */
public class CBooleanTrueItem extends CBooleanItem {

    public CBooleanTrueItem(HyriBuild plugin) {
        super(plugin, "config.boolean-yes", ChatColor.GREEN + Symbols.TICK_BOLD + "Oui §7(Clic-droit)", (byte) 10, true);
    }

}
