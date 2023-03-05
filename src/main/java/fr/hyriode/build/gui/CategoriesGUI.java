package fr.hyriode.build.gui;

import fr.hyriode.build.gui.map.MapsGUI;
import fr.hyriode.build.map.Category;
import fr.hyriode.build.util.Filters;
import fr.hyriode.hyrame.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by AstFaster
 * on 23/02/2023 at 15:39
 */
public class CategoriesGUI extends BuildGUI {

    public CategoriesGUI(Player owner) {
        super(owner, "Catégories");
    }

    @Override
    public void init() {
        super.init();

        final List<Category> categories = Arrays.stream(Category.values())
                .sorted(Comparator.comparing(Category::isGame))
                .collect(Collectors.toList());

        int index = 0;
        for (int y = 2; y <= 3; y++) {
            for (int x = 1; x <= 7; x++) {
                if (index == categories.size()) {
                    break;
                }

                final Category category = categories.get(index);

                this.setItem(y * 9 + x, new ItemBuilder(category.getIcon())
                        .withName(ChatColor.AQUA + category.getDisplay())
                        .withLore("§7Affiche les maps disponibles", "§7en §b" +  category.getDisplay() + "§7.", "", "§3Cliquer pour voir")
                        .build(), event -> this.openSubGUI(new MapsGUI(this.owner, category, Filters.DEFAULT_FILTERS)));
                index++;
            }
        }
    }

}
