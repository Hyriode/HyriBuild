package fr.hyriode.build.gui.map;

import fr.hyriode.build.gui.BuildGUI;
import fr.hyriode.build.map.Category;
import fr.hyriode.build.map.Environment;
import fr.hyriode.build.map.Map;
import fr.hyriode.build.util.BuildHead;
import fr.hyriode.build.util.Filters;
import fr.hyriode.hyrame.item.ItemBuilder;
import fr.hyriode.hyrame.utils.Symbols;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by AstFaster
 * on 23/02/2023 at 15:59
 */
public class MapFiltersGUI extends BuildGUI {

    private final Filters filters;
    private final Category category;

    public MapFiltersGUI(Player owner, Filters filters, Category category) {
        super(owner, "Filtres");
        this.filters = filters;
        this.category = category;
    }

    @Override
    public void init() {
        super.init();

        this.addEnvironmentItem();
        this.addDateOrderItem();
        this.addStateItem();
        this.addConfigItem();
    }

    private void addEnvironmentItem() {
        final Environment[] environments = Environment.values();
        final List<String> lore = new ArrayList<>();

        Collections.addAll(lore, "§7Définis de quel environement", "§7proviennent les maps.", "", "§8Statut:");

        for (Environment environment : environments) {
            lore.add(ChatColor.DARK_GRAY + Symbols.DOT_BOLD + " " + (this.filters.getEnvironment() == environment ? ChatColor.AQUA : ChatColor.GRAY) + environment.getDisplay());
        }

        Collections.addAll(lore, "", "§3Cliquer pour modifier");

        this.setItem(21, ItemBuilder.asHead(BuildHead.GLOBE)
                .withName("§bEnvironement")
                .withLore(lore)
                .build(),
                event -> {
                    final int currentIndex = this.filters.getEnvironment().ordinal();

                    if (currentIndex + 1 >= environments.length) {
                        this.filters.setEnvironment(environments[0]);
                    } else {
                        this.filters.setEnvironment(environments[currentIndex + 1]);
                    }

                    this.addEnvironmentItem();

                    this.owner.playSound(this.owner.getLocation(), Sound.CLICK, 0.5F, 2.0F);
                });
    }

    private void addDateOrderItem() {
        final Filters.Order[] orders = Filters.Order.values();
        final List<String> lore = new ArrayList<>();

        Collections.addAll(lore, "§7Définis si les maps doivent être", "§7triées par ordre de création.", "", "§8Statut:");

        for (Filters.Order order : orders) {
            lore.add(ChatColor.DARK_GRAY + Symbols.DOT_BOLD + " " + (this.filters.getDateOrder() == order ? ChatColor.AQUA : ChatColor.GRAY) + order.getDisplay());
        }

        Collections.addAll(lore, "", "§3Cliquer pour modifier");

        this.setItem(22, new ItemBuilder(Material.WATCH)
                .withName("§bOrdre de création")
                .withLore(lore)
                .build(),
                event -> {
                    final int currentIndex = this.filters.getDateOrder().ordinal();

                    if (currentIndex + 1 >= orders.length) {
                        this.filters.setDateOrder(orders[0]);
                    } else {
                        this.filters.setDateOrder(orders[currentIndex + 1]);
                    }

                    this.addDateOrderItem();

                    this.owner.playSound(this.owner.getLocation(), Sound.CLICK, 0.5F, 2.0F);
                });
    }

    private void addStateItem() {
        final Filters.State[] states = Filters.State.values();
        final List<String> lore = new ArrayList<>();

        Collections.addAll(lore, "§7Définis quelles maps doivent", "§7être affichées.", "", "§8Statut:");

        for (Filters.State state : states) {
            lore.add(ChatColor.DARK_GRAY + Symbols.DOT_BOLD + " " + (this.filters.getState() == state ? ChatColor.AQUA : ChatColor.GRAY) + state.getDisplay());
        }

        Collections.addAll(lore, "", "§3Cliquer pour modifier");

        this.setItem(23, ItemBuilder.asHead(BuildHead.STICKY_PISTON)
                .withName("§bEtat")
                .withLore(lore)
                .build(),
                event -> {
                    final int currentIndex = this.filters.getState().ordinal();

                    if (currentIndex + 1 >= states.length) {
                        this.filters.setState(states[0]);
                    } else {
                        this.filters.setState(states[currentIndex + 1]);
                    }

                    this.addStateItem();

                    this.owner.playSound(this.owner.getLocation(), Sound.CLICK, 0.5F, 2.0F);
                });
    }

    private void addConfigItem() {
        final Filters.Config[] configs = Filters.Config.values();
        final List<String> lore = new ArrayList<>();

        Collections.addAll(lore, "§7Définis quelles maps doivent", "§7être affichées.", "", "§8Statut:");

        for (Filters.Config config : configs) {
            lore.add(ChatColor.DARK_GRAY + Symbols.DOT_BOLD + " " + (this.filters.getConfig() == config ? ChatColor.AQUA : ChatColor.GRAY) + config.getDisplay());
        }

        Collections.addAll(lore, "", "§3Cliquer pour modifier");

        this.setItem(31, new ItemBuilder(Material.REDSTONE_COMPARATOR)
                .withName("§bConfigurations")
                .withLore(lore)
                .build(),
                event -> {
                    final int currentIndex = this.filters.getConfig().ordinal();

                    if (currentIndex + 1 >= configs.length) {
                        this.filters.setConfig(configs[0]);
                    } else {
                        this.filters.setConfig(configs[currentIndex + 1]);
                    }

                    this.addConfigItem();

                    this.owner.playSound(this.owner.getLocation(), Sound.CLICK, 0.5F, 2.0F);
                });
    }

}
