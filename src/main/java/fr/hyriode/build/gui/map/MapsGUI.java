package fr.hyriode.build.gui.map;

import fr.hyriode.build.gui.BuildGUI;
import fr.hyriode.build.map.Category;
import fr.hyriode.build.map.Environment;
import fr.hyriode.build.map.Map;
import fr.hyriode.build.permission.Permissions;
import fr.hyriode.build.permission.PermissionsProvider;
import fr.hyriode.build.util.Filters;
import fr.hyriode.build.map.MapUtils;
import fr.hyriode.hyrame.inventory.pagination.PaginatedItem;
import fr.hyriode.hyrame.inventory.pagination.PaginationArea;
import fr.hyriode.hyrame.item.ItemBuilder;
import fr.hyriode.hyrame.utils.HyrameHead;
import fr.hyriode.hyrame.utils.Pagination;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by AstFaster
 * on 23/02/2023 at 15:52
 */
public class MapsGUI extends BuildGUI {

    private final Category category;
    private final Filters filters;

    public MapsGUI(Player owner, Category category, Filters filters) {
        super(owner, category.getDisplay());
        this.category = category;
        this.filters = filters;
        this.header = false;
        this.goBackSlot = 0;
        this.paginationManager.setArea(new PaginationArea(20, 33));
    }

    @Override
    public void init() {
        super.init();

        this.setItem(4, new ItemBuilder(this.category.getIcon())
                .withName(ChatColor.AQUA + this.category.getDisplay())
                .withLore("§7Voici la liste des maps disponibles", "§7en §b" + this.category.getDisplay() + "§7.")
                .build());

        this.setItem(49, ItemBuilder.asHead(HyrameHead.MONITOR_PLUS)
                .withName("§bUpload une map")
                .withLore("§7Permet d'upload une map vers", "§7l'environement de §b" + Environment.DEVELOPMENT.getDisplay() + "§7.", "", "§3Cliquer pour voir")
                .build(),
                event -> {
                    if (PermissionsProvider.getPermissions(this.owner).hasWithError(Permissions.Action.UPLOAD, null)) {
                        this.openSubGUI(new MapUploadGUI(this.owner, this.category));
                    }
                });

        this.setItem(52, new ItemBuilder(Material.HOPPER)
                .withName("§bFiltres")
                .withLore("§7Modifie les filtres à appliquer", "§7sur les maps.", "", "§3Cliquer pour modifier")
                .build(), event -> this.openSubGUI(new MapFiltersGUI(this.owner, this.filters, this.category)));

        final List<Map> maps = this.getMaps().stream()
                .filter(map -> (this.filters.getState() == Filters.State.ENABLED && map.getHandle().isEnabled()) || (this.filters.getState() == Filters.State.DISABLED && !map.getHandle().isEnabled()) || (this.filters.getState() == Filters.State.ALL))
                .filter(map -> (this.filters.getConfig() == Filters.Config.DONE && map.hasConfig()) || (this.filters.getConfig() == Filters.Config.TODO && !map.hasConfig()) || (this.filters.getConfig() == Filters.Config.ALL))
                .sorted((o1, o2) -> this.filters.getDateOrder() == Filters.Order.NONE ? 0 : Math.toIntExact(this.filters.getDateOrder() == Filters.Order.ASCENDING ? o1.getHandle().getCreationDate() - o2.getHandle().getCreationDate() : o2.getHandle().getCreationDate() - o1.getHandle().getCreationDate()))
                .sorted(Comparator.comparing(map -> map.getHandle().getCategory()))
                .sorted((o1, o2) -> this.category.getTypes().indexOf(o1.getHandle().getCategory()) - this.category.getTypes().indexOf(o2.getHandle().getCategory()))
                .collect(Collectors.toList());

        final Pagination<PaginatedItem> pagination = this.paginationManager.getPagination();

        pagination.clear();

        for (Map map : maps) {
            pagination.add(PaginatedItem.from(this.createItem(map), event -> {
                if (map.refreshData()) {
                    this.openSubGUI(new MapGUI(this.owner, map));
                }
            }));
        }

        this.paginationManager.updateGUI();

        if (maps.size() == 0) {
            // Clear pagination items
            this.setItem(18, null);
            this.setItem(26, null);

            this.setItem(22, new ItemBuilder(Material.BARRIER)
                    .withName("§cAucune map n'a été trouvée")
                    .build());
        }
    }

    private ItemStack createItem(Map map) {
        final ItemStack itemStack = new ItemBuilder(MapUtils.createItem(map)).appendLore("", "§3Cliquer pour modifier").build();

        itemStack.setAmount(this.category.getTypes() == null ? 0 : this.category.getTypes().indexOf(map.getHandle().getCategory()) + 1);

        return itemStack;
    }

    private List<Map> getMaps() {
        if (this.filters.getEnvironment() == Environment.ALL) {
            final List<Map> maps = new ArrayList<>();

            for (Environment environment : Environment.values()) {
                if (environment == Environment.ALL) {
                    continue;
                }

                maps.addAll(environment.getWorldManager().getWorlds(this.category.getDatabase()).stream().map(world -> new Map(environment, this.category, world)).collect(Collectors.toList()));
            }
            return maps;
        }
        return this.filters.getEnvironment().getWorldManager().getWorlds(this.category.getDatabase()).stream().map(world -> new Map(this.filters.getEnvironment(), this.category, world)).collect(Collectors.toList());
    }

    @Override
    public void updatePagination(int page, List<PaginatedItem> items) {
        if (items.size() > 0) {
            this.addDefaultPagesItems(18, 26);
        }
    }

}
