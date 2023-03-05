package fr.hyriode.build.map;

import fr.hyriode.api.world.IHyriWorld;
import fr.hyriode.build.discord.DiscordEmoji;
import fr.hyriode.hyrame.item.ItemBuilder;
import fr.hyriode.hyrame.utils.Symbols;
import fr.hyriode.hyrame.utils.TimeUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;

/**
 * Created by AstFaster
 * on 24/02/2023 at 13:44
 */
public class MapUtils {

    public static String formatChanges(IHyriWorld oldMap, IHyriWorld newMap) {
        final StringBuilder builder = new StringBuilder();

        if (!oldMap.getName().equals(newMap.getName())) {
            builder.append("Nom: ").append(oldMap.getName()).append(" " + DiscordEmoji.ARROW_RIGHT + " ").append(newMap.getName()).append("\n");
        }

        if (!oldMap.getCategory().equals(newMap.getCategory())) {
            builder.append("Type: ").append(oldMap.getCategory()).append(" " + DiscordEmoji.ARROW_RIGHT + " ").append(newMap.getCategory()).append("\n");
        }

        if (!oldMap.getAuthors().equals(newMap.getAuthors())) {
            builder.append("Builder(s): ").append(formatAuthors(oldMap.getAuthors())).append(" " + DiscordEmoji.ARROW_RIGHT + " ").append(formatAuthors(newMap.getAuthors())).append("\n");
        }

        if (oldMap.isEnabled() != newMap.isEnabled()) {
            builder.append("Etat: ").append(oldMap.isEnabled() ? "Activée" : "Désactivée").append(" " + DiscordEmoji.ARROW_RIGHT + " ").append(newMap.isEnabled() ? "Activée" : "Désactivée").append("\n");
        }

        return builder.toString();
    }

    public static ItemStack createItem(Map map) {
        final ItemBuilder builder = new ItemBuilder(Material.MAP)
                .withName(ChatColor.AQUA + map.getHandle().getName())
                .withAllItemFlags()
                .withLore(
                        "",
                        "§8Informations:",
                        "§8 ▪ §7Type: §b" + map.getHandle().getCategory(),
                        "§8 ▪ §7Date de création: §b" + TimeUtil.formatDate(new Date(map.getHandle().getCreationDate()), "dd/MM/yyyy"),
                        "§8 ▪ §7Environnement: §b" + map.getEnvironment().getDisplay(),
                        "§8 ▪ §7Activée: " + (map.getHandle().isEnabled() ? ChatColor.GREEN + Symbols.TICK_BOLD : ChatColor.RED + Symbols.CROSS_STYLIZED_BOLD)
                );

        if (map.getCategory().hasConfig()) {
            builder.appendLore("§8 ▪ §7Configurée: " + (map.hasConfig() ? ChatColor.GREEN + Symbols.TICK_BOLD : ChatColor.RED + Symbols.CROSS_STYLIZED_BOLD));
        }

        builder.appendLore("§8 ▪ §7Builder(s): §b" + formatAuthors(map.getHandle().getAuthors()));

        return builder.build();
    }

    public static String formatAuthors(List<String> authors) {
        final StringBuilder builder = new StringBuilder();

        if (authors.size() == 0) {
            return "-";
        }

        for (String author : authors) {
            builder.append(author).append(", ");
        }
        return builder.substring(0, builder.toString().length() - 2);
    }

}
