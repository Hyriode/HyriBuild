package fr.hyriode.build.gui;

import fr.hyriode.build.util.BuildHead;
import fr.hyriode.hyrame.item.ItemBuilder;
import org.bukkit.entity.Player;

/**
 * Created by AstFaster
 * on 23/02/2023 at 14:58
 */
public class MainGUI extends BuildGUI {

    public MainGUI(Player owner) {
        super(owner, "Menu Principal");
    }

    @Override
    public void init() {
        super.init();

        this.setItem(22, ItemBuilder.asHead(BuildHead.GLOBE)
                .withName("§bMaps")
                .withLore("§7Permet de gérer les différentes", "§7maps présentes sur le serveur.", "", "§3Cliquer pour voir")
                .build(), event -> this.openSubGUI(new CategoriesGUI(this.owner)));
    }

}
