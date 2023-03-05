package fr.hyriode.build.gui;

import fr.hyriode.build.util.BuildHead;
import fr.hyriode.hyrame.inventory.pagination.PaginatedInventory;
import fr.hyriode.hyrame.inventory.pagination.PaginatedItem;
import fr.hyriode.hyrame.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by AstFaster
 * on 23/02/2023 at 15:28
 */
public abstract class BuildGUI extends PaginatedInventory {

    private BuildGUI last;

    protected int goBackSlot = 49;
    protected boolean header = true;

    public BuildGUI(Player owner, String name) {
        super(owner,  "§3Build §8 ┃ §7" + name, 6 * 9);
    }

    public void init() {
        this.setHorizontalLine(0, 8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 9).withName(" ").build());
        this.setHorizontalLine(45, 53, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 9).withName(" ").build());

        if (this.header) {
            this.setItem(4, ItemBuilder.asHead(BuildHead.BOB_THE_BUILDER)
                    .withName("§bHyriBuild")
                    .withLore("§7Panel permettant de gérer les", "§7différentes maps présentes", "§7sur le serveur.")
                    .build());
        }

        if (this.last != null) {
            this.setItem(this.goBackSlot, new ItemBuilder(Material.ARROW)
                            .withName("§8Retour")
                            .build(),
                            event -> this.goBack());
        }
    }

    protected void goBack() {
        this.last.init();
        this.last.open();
    }

    public void openSubGUI(BuildGUI inventory) {
        inventory.setLast(this);
        inventory.open();
    }

    @Override
    public void updatePagination(int page, List<PaginatedItem> items) {}

    @Override
    public void open() {
        this.init();
        super.open();
    }

    private void setLast(BuildGUI last) {
        this.last = last;
    }

}
