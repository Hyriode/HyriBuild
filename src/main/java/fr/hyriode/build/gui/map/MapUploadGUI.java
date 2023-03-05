package fr.hyriode.build.gui.map;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.api.world.IHyriWorld;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.discord.DiscordColor;
import fr.hyriode.build.gui.BuildGUI;
import fr.hyriode.build.gui.ConfirmGUI;
import fr.hyriode.build.map.Category;
import fr.hyriode.build.map.Environment;
import fr.hyriode.build.map.Map;
import fr.hyriode.build.util.BuildHead;
import fr.hyriode.build.map.MapUtils;
import fr.hyriode.hyrame.anvilgui.AnvilGUI;
import fr.hyriode.hyrame.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by AstFaster
 * on 24/02/2023 at 11:27
 */
public class MapUploadGUI extends BuildGUI {

    private String name = "";
    private String type;
    private final List<String> authors = new ArrayList<>();

    private final Category category;

    public MapUploadGUI(Player owner, Category category) {
        super(owner, "Upload");
        this.goBackSlot = 0;
        this.header = false;
        this.category = category;
        this.type = category.getTypes() == null ? "" : category.getTypes().get(0);
    }

    @Override
    public void init() {
        super.init();

        this.setItem(4, ItemBuilder.asHead(BuildHead.UPLOAD)
                .withName("§bUpload")
                .withLore("§7La map sera dans un premier", "§7temps upload vers l'environement", "§7de §b" + Environment.DEVELOPMENT.getDisplay() + "§7.")
                .build());

        this.setItem(49, new ItemBuilder(Material.STAINED_GLASS, 1, 5)
                .withName("§aUpload la map")
                .withLore("§7La map sera upload à partir du monde", "§b" + this.owner.getWorld().getName() + "§7.")
                .build(),
                event -> {
                    if (this.name.isEmpty() || this.type.isEmpty()) {
                        this.owner.sendMessage("§3Build §f ┃ Paramètres §cinvalides §f!");
                        return;
                    }

                    new ConfirmGUI(this.owner).withConfirmCallback(e -> {
                        final IHyriWorld map = HyriAPI.get().getWorldManager().newWorld()
                                .withName(this.name)
                                .withAuthors(this.authors)
                                .withDatabase(this.category.getDatabase())
                                .withCategory(this.type)
                                .build();
                        map.setEnabled(false);
                        map.save(this.owner.getWorld().getUID());

                        HyriBuild.get().getWebhook().sendMapMessage(this.owner, new Map(Environment.DEVELOPMENT, this.category, map), "Nouvelle map", "Une nouvelle map a été upload vers l'environement de développement.", DiscordColor.GREEN);

                        this.owner.sendMessage("§3Build §f ┃ La map '§b" + this.name + "§f' a correctement été upload §8(monde: " + this.owner.getWorld().getName() + ")§f.");
                        this.owner.playSound(this.owner.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

                        this.goBack();
                    }).withCancelCallback(e -> {
                        this.owner.sendMessage("§3Build §f ┃ Action §cannulée§f.");

                        this.open();
                    }).open();
                });

        this.addNameItem();
        this.addAuthorsItem();
        this.addCategoryItem();
    }

    private void addNameItem() {
        this.setItem(21, new ItemBuilder(Material.NAME_TAG)
                .withName("§bNom")
                .withLore("§7Le nom inscrit ici sera celui", "§7affiché aux joueurs.", "", "§8Actuel:", "§8 ▪ §b" + this.name, "", "§3Cliquer pour modifier")
                .build(),
                event -> new AnvilGUI(HyriBuild.get(), this.owner, this.name, null, false, e -> Bukkit.getScheduler().runTaskLater(HyriBuild.get(), this::open, 1L), null, null, (p, input) -> {
                    this.name = input;

                    this.addNameItem();
                    this.open();

                    return null;
                }).open());
    }


    private void addAuthorsItem() {
        this.setItem(22, new ItemBuilder(Material.BOOK_AND_QUILL)
                .withName("§bBuilder(s)")
                .withLore("§7Les valeurs inscritent ici sont les", "§7crédits affichés en utlisant la commande", "§b/map §7sur le serveur.", "", "§8Actuels:", "§8 ▪ §b" + MapUtils.formatAuthors(this.authors), "", "§3Clic-gauche pour ajouter", "§cClic-droit pour enlever")
                .build(),
                event -> {
                    if (!event.isLeftClick() && !event.isRightClick()) {
                        return;
                    }

                    new AnvilGUI(HyriBuild.get(), this.owner, "", null, false, e -> Bukkit.getScheduler().runTaskLater(HyriBuild.get(), this::open, 1L), null, null, (p, input) -> {
                        if (event.isLeftClick()) {
                            this.authors.add(input);
                        } else if (event.isRightClick()) {
                            this.authors.remove(input);
                        }

                        this.addAuthorsItem();
                        this.open();

                        return null;
                    }).open();
                });
    }

    private void addCategoryItem() {
        if (this.category.getTypes() == null) {
            return;
        }

        final List<String> lore = new ArrayList<>();

        Collections.addAll(lore, "§7Définis dans quelle catégorie est", "§7rangée la map.", "", "§8Statut:");

        for (String type : this.category.getTypes()) {
            lore.add("§8 ▪ " + (Objects.equals(this.type, type) ? "§b" : "§7") + type);
        }

        Collections.addAll(lore, "", "§3Cliquer pour modifier");

        this.setItem(23, new ItemBuilder(this.category.getIcon())
                .withName("§bCatégorie")
                .withLore(lore)
                .build(),
                event -> {
                    final int currentIndex = this.category.getTypes().indexOf(this.type);

                    if (currentIndex + 1 >= this.category.getTypes().size()) {
                        this.type = this.category.getTypes().get(0);
                    } else {
                        this.type = this.category.getTypes().get(currentIndex + 1);
                    }

                    this.addCategoryItem();

                    this.owner.playSound(this.owner.getLocation(), Sound.CLICK, 0.5F, 2.0F);
                });
    }


}
