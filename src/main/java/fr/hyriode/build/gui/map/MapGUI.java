package fr.hyriode.build.gui.map;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.api.impl.common.world.HyriWorld;
import fr.hyriode.api.mongodb.MongoSerializable;
import fr.hyriode.api.mongodb.MongoSerializer;
import fr.hyriode.api.world.IHyriWorld;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.discord.DiscordColor;
import fr.hyriode.build.gui.BuildGUI;
import fr.hyriode.build.gui.ConfirmGUI;
import fr.hyriode.build.map.Category;
import fr.hyriode.build.map.Environment;
import fr.hyriode.build.map.Map;
import fr.hyriode.build.permission.Permissions;
import fr.hyriode.build.permission.PermissionsProvider;
import fr.hyriode.build.util.BuildHead;
import fr.hyriode.build.map.MapUtils;
import fr.hyriode.hyrame.anvilgui.AnvilGUI;
import fr.hyriode.hyrame.item.ItemBuilder;
import fr.hyriode.hyrame.utils.HyrameHead;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by AstFaster
 * on 24/02/2023 at 11:27
 */
public class MapGUI extends BuildGUI {

    private final Map map;
    private final Category category;
    private IHyriConfig config;

    private boolean edited = false;

    public MapGUI(Player owner, Map map) {
        super(owner, map.getHandle().getName().length() > 17 ? map.getHandle().getName().substring(0, 18) : map.getHandle().getName());
        this.header = false;
        this.goBackSlot = 0;
        this.map = map;
        this.category = map.getCategory();
        this.config = map.getConfig();

        final IHyriWorld handle = this.map.getHandle();

        if (this.config == null && handle.isEnabled()) {
            handle.setEnabled(false);
            handle.update();
        }
    }

    @Override
    public void init() {
        super.init();

        this.setItem(4, MapUtils.createItem(this.map));

        this.setItem(49, new ItemBuilder(Material.STAINED_GLASS, 1, 5)
                .withName("§aAppliquer les modifications")
                .build(),
                event -> {
                    if (this.edited) {
                        new ConfirmGUI(this.owner).withConfirmCallback(e -> {
                            this.map.getHandle().update();
                            this.map.updateConfig();
                            this.owner.sendMessage("§3Build §f ┃ Les modifications sur la map '§b" + this.map.getHandle().getName() + "§f' ont été §aappliquées§f.");
                            this.owner.playSound(this.owner.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

                            HyriBuild.get().getWebhook().sendMapMessage(this.owner, this.map, "Modifications appliquées", MapUtils.formatChanges(this.map.getInitialHandle(), this.map.getHandle()), DiscordColor.BLUE);

                            this.goBack();
                        }).withCancelCallback(e -> {
                            this.owner.sendMessage("§3Build §f ┃ Action §cannulée§f.");

                            this.open();
                        }).open();
                    }
                });

        this.setItem(45, ItemBuilder.asHead(HyrameHead.MONITOR_PLUS)
                .withName("§bImporter la map")
                .withLore("§7Permet de télécharger la map pour", "§7faire des modifications dessus.", "", "§3Cliquer pour importer la map")
                .build(),
                event -> {
                    if (this.edited) {
                        this.owner.sendMessage("§3Build §f ┃ §cImpossible d'importer la map (raison: modifications effectuées) §c!");
                        return;
                    }

                    new AnvilGUI(HyriBuild.get(), this.owner, "Fichier", null, false, e -> Bukkit.getScheduler().runTaskLater(HyriBuild.get(), this::open, 1L), null, null, (p, input) -> {
                        this.map.getHandle().load(new File("./" + input));

                        new WorldCreator(input).environment(World.Environment.NORMAL).createWorld();

                        this.owner.sendMessage("§3Build §f ┃ La map '§b" + this.map.getHandle().getName() + "§f' a correctement été §achargée §8(monde: " + input + ")§f.");

                        this.open();

                        return null;
                    }).open();
                });

        if (this.map.getEnvironment() == Environment.DEVELOPMENT) {
            this.setItem(47, ItemBuilder.asHead(HyrameHead.MONITOR_FORWARD)
                    .withName("§bTransférer la map")
                    .withLore("§7Permet de transférer la map vers", "§7l'environement de §b" + Environment.PRODUCTION.getDisplay() + "§7.", "", "§3Cliquer pour transférer la map")
                    .build(),
                    event -> {
                        if (this.edited) {
                            this.owner.sendMessage("§3Build §f ┃ §cImpossible de transférer la map (raison: modifications effectuées) §c!");
                            return;
                        }

                        if (!PermissionsProvider.getPermissions(this.owner).hasWithError(Permissions.Action.TRANSFER, this.map.getEnvironment())) {
                            return;
                        }

                        try {
                            this.map.transfer();
                            this.map.transferConfig();

                            HyriBuild.get().getWebhook().sendMapMessage(this.owner, this.map, "Map transférée", "La map " + (this.map.hasConfig() ? "ainsi que sa configuration ont été transférées" : "a été transférée") + " vers l'environement de production.", DiscordColor.LIGHT_GREEN);

                            this.owner.sendMessage("§3Build §f ┃ La map '§b" + this.map.getHandle().getName() + "§f' a correctement été §atransférée§f.");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        this.setItem(53, ItemBuilder.asHead(HyrameHead.GARBAGE_CAN)
                .withName("§cSupprimer la map")
                .withLore("§c/!\\ §7Cette action est irréversible : la", "§7map ne pourra plus être récupérée !", "", "§cCliquer pour supprimer la map")
                .build(),
                event -> {
                    if (this.edited) {
                        this.owner.sendMessage("§3Build §f ┃ §cImpossible de supprimer la map (raison: modifications effectuées) §c!");
                        return;
                    }

                    if (PermissionsProvider.getPermissions(this.owner).hasWithError(Permissions.Action.DELETE, this.map.getEnvironment())) {
                        new ConfirmGUI(this.owner).withConfirmCallback(e -> {
                            this.map.getHandle().delete();
                            this.map.deleteConfig();

                            HyriBuild.get().getWebhook().sendMapMessage(this.owner, this.map, "Map supprimée", "La map " + (this.map.hasConfig() ? "ainsi que sa configuration ont été supprimées." : "a été supprimée."), DiscordColor.RED);

                            this.owner.sendMessage("§3Build §f ┃ La map '§b" + this.map.getHandle().getName() + "§f' a correctement été §csupprimée§f.");
                            this.owner.playSound(this.owner.getLocation(), Sound.FIZZ, 0.5F, 1.0F);

                            this.goBack();
                        }).withCancelCallback(e -> {
                            this.owner.sendMessage("§3Build §f ┃ Action §cannulée§f.");

                            this.open();
                        }).open();
                    }
                });

        if (this.category.hasConfig()) {
            this.setItem(25, new ItemBuilder(Material.REDSTONE_COMPARATOR)
                    .withName("§bConfiguration")
                    .withLore("§7Modifie et gère la configuration", "§7actuelle de la map.", "", "§8Statut:", "§8 ▪ " + (this.config != null ? "§aFaite" : "§cA faire"), "", (this.config != null ? "§cCliquer pour refaire" : "§3Cliquer pour faire"))
                    .build(),
                    event -> {
                        if (this.edited) {
                            this.owner.sendMessage("§3Build §f ┃ §cImpossible de configurer la map (raison: modifications effectuées) §c!");
                            return;
                        }

                        if (PermissionsProvider.getPermissions(this.owner).hasWithError(Permissions.Action.CREATE_CONFIG, this.map.getEnvironment())) {
                            this.owner.closeInventory();

                            this.category.newConfigProcess(this.owner).withFinishCallback(config -> new ConfirmGUI(this.owner).withConfirmCallback(e -> {
                                if (!this.map.hasConfig()) {
                                    HyriBuild.get().getWebhook().sendMapMessage(this.owner, this.map, "Configuration ajoutée", "La configuration de la map a été ajoutée.", DiscordColor.GREEN);
                                } else {
                                    HyriBuild.get().getWebhook().sendMapMessage(this.owner, this.map, "Configuration modifiée", "La configuration de la map a été modifiée.", DiscordColor.BLUE);
                                }

                                this.map.saveConfig(config);
                                this.config = this.map.getConfig();
                                this.owner.sendMessage("§3Build §f ┃ La §bconfiguration §fde la map '§b" + this.map.getHandle().getName() + "§f' a correctement été §amodifiée§f.");
                                this.owner.playSound(this.owner.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

                                this.open();
                            }).withCancelCallback(e -> {
                                this.owner.sendMessage("§3Build §f ┃ Action §cannulée§f.");

                                this.open();
                            }).open());
                        }
                    });
        }

        this.addNameItem();
        this.addAuthorsItem();
        this.addStateItem();
        this.addCategoryItem();
    }

    private void addNameItem() {
        this.setItem(21, new ItemBuilder(Material.NAME_TAG)
                .withName("§bNom")
                .withLore("§7Le nom inscrit ici sera celui", "§7affiché aux joueurs.", "", "§8Actuel:", "§8 ▪ §b" + this.map.getHandle().getName(), "", "§3Cliquer pour modifier")
                .build(),
                event -> new AnvilGUI(HyriBuild.get(), this.owner, this.map.getHandle().getName(), null, false, e -> Bukkit.getScheduler().runTaskLater(HyriBuild.get(), this::open, 1L), null, null, (p, input) -> {
                    this.map.getHandle().setName(input);
                    this.edited = true;

                    this.addNameItem();
                    this.open();

                    return null;
                }).open());
    }


    private void addAuthorsItem() {
        this.setItem(22, new ItemBuilder(Material.BOOK_AND_QUILL)
                .withName("§bBuilder(s)")
                .withLore("§7Les valeurs inscritent ici sont les", "§7crédits affichés en utlisant la commande", "§b/map §7sur le serveur.", "", "§8Actuels:", "§8 ▪ §b" + MapUtils.formatAuthors(this.map.getHandle().getAuthors()), "", "§3Clic-gauche pour ajouter", "§cClic-droit pour enlever")
                .build(),
                event -> {
                    if (!event.isLeftClick() && !event.isRightClick()) {
                        return;
                    }

                    if (!PermissionsProvider.getPermissions(this.owner).hasWithError(Permissions.Action.CHANGE_AUTHORS, this.map.getEnvironment())) {
                        return;
                    }

                    new AnvilGUI(HyriBuild.get(), this.owner, "", null, false, e -> Bukkit.getScheduler().runTaskLater(HyriBuild.get(), this::open, 1L), null, null, (p, input) -> {
                        if (event.isLeftClick()) {
                            this.map.getHandle().addAuthor(input);
                        } else if (event.isRightClick()) {
                            this.map.getHandle().removeAuthor(input);
                        }

                        this.edited = true;

                        this.addAuthorsItem();
                        this.open();

                        return null;
                    }).open();
                });
    }

    private void addStateItem() {
        this.setItem(23, ItemBuilder.asHead(BuildHead.STICKY_PISTON)
                .withName("§bEtat")
                .withLore("§7Définis si la map est activée", "§7sur le serveur ou non.", "", "§8Statut:", "§8 ▪ " + (this.map.getHandle().isEnabled() ? "§b" : "§7") + "Oui", "§8 ▪ " + (!this.map.getHandle().isEnabled() ? "§b" : "§7") + "Non", "", "§3Cliquer pour modifier")
                .build(),
                event -> {
                    if (PermissionsProvider.getPermissions(this.owner).hasWithError(Permissions.Action.CHANGE_STATE, this.map.getEnvironment())) {
                        if (this.config == null) {
                            this.owner.sendMessage("§3Build §f ┃ §cImpossible de changer l'état de la map (raison: configuration manquante) §c!");
                            return;
                        }

                        this.map.getHandle().setEnabled(!this.map.getHandle().isEnabled());
                        this.edited = true;

                        this.addStateItem();

                        this.owner.playSound(this.owner.getLocation(), Sound.CLICK, 0.5F, 2.0F);
                    }
                });
    }

    private void addCategoryItem() {
        if (this.category.getTypes() == null) {
            return;
        }

        final List<String> lore = new ArrayList<>();

        Collections.addAll(lore, "§7Définis dans quelle catégorie est", "§7rangée la map.", "", "§8Statut:");

        for (String type : this.category.getTypes()) {
            lore.add("§8 ▪ " + (this.map.getHandle().getCategory().equals(type) ? "§b" : "§7") + type);
        }

        Collections.addAll(lore, "", "§3Cliquer pour modifier");

        this.setItem(31, new ItemBuilder(this.category.getIcon())
                .withName("§bCatégorie")
                .withLore(lore)
                .build(),
                event -> {
                    final int currentIndex = this.category.getTypes().indexOf(this.map.getHandle().getCategory());

                    this.edited = true;

                    if (currentIndex + 1 >= this.category.getTypes().size()) {
                        this.map.getHandle().setCategory(this.category.getTypes().get(0));
                    } else {
                        this.map.getHandle().setCategory(this.category.getTypes().get(currentIndex + 1));
                    }

                    this.addCategoryItem();

                    this.owner.playSound(this.owner.getLocation(), Sound.CLICK, 0.5F, 2.0F);
                });
    }


}
