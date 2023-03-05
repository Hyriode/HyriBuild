package fr.hyriode.build.map.config;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.handler.ConfigOptionHandler;
import fr.hyriode.hyrame.title.Title;
import fr.hyriode.hyrame.utils.Symbols;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by AstFaster
 * on 01/06/2022 at 13:58
 */
public class ConfigProcess<T extends IHyriConfig> {

    /** The current step running */
    private ConfigStep currentStep;

    /** The callback to call after the process execution */
    private Consumer<T> finishCallback;

    /** Whether the process is started or not */
    private boolean started;
    /** The initial size of the process (all steps to run) */
    private int initialSize;

    /** The player that started the process */
    private final Player player;
    /** The configuration object to edit */
    private final T config;
    /** The data of the config */
    private final ConfigData configData;
    /** The queue with all {@link ConfigStep} to run */
    private final Queue<ConfigStep> steps;

    /**
     * Default constructor of a {@linkplain ConfigProcess config process}
     *
     * @param player The player running the process
     * @param config The configuration to create during the process
     * @param configData The data of the configuration
     */
    public ConfigProcess(Player player, T config, ConfigData configData) {
        this.player = player;
        this.config = config;
        this.configData = configData;
        this.steps = new ConcurrentLinkedQueue<>();
    }

    /**
     * Start the configuration process
     */
    public void start() {
        if (this.started) {
            throw new IllegalStateException("Config process has already started!");
        }

        this.started = true;
        this.initialSize = this.steps.size();

        this.sendMessage(builder -> builder.append("§7Démarrage d'un processus de création de §bconfiguration§7...\n§7Informations:\n§8▪ §b" + this.initialSize + " §7valeur(s) à définir.\n§8▪ §7Type de configuration: §b" + this.configData.name()));
        Title.sendTitle(this.player, "§bCréation d'une configuration", this.configData.name(), 5, 3 * 20, 10);

        this.next();
    }

    /**
     * Switch to the next step
     */
    private void next() {
        this.currentStep = this.steps.poll();

        if (this.currentStep != null) {
            final ConfigOption option = this.currentStep.getOption();
            final ConfigOptionCategory category = this.currentStep.getCategory();
            final ConfigOptionHandler<?> handler = option.type().newHandler(this);

            this.currentStep.setHandler(handler);

            handler.getCompletion().whenComplete((BiConsumer<Object, Throwable>) (object, throwable) -> {
                Title.sendTitle(this.player, ChatColor.GREEN + Symbols.TICK_BOLD + " Validé", "", 2, 30, 2);

                this.write(this.currentStep, category != null ? this.currentStep.getCategoryObject() : this.config, object);
                this.next();
            });
            handler.handle();

            this.sendMessage(builder -> {
                builder.append(" ▪ ").color(ChatColor.DARK_GRAY)
                        .append("Nouvelle valeur à définir: ").color(ChatColor.GRAY)
                        .append(option.name()).color(ChatColor.AQUA).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Id: §b" + option.id())))
                        .append("\n").reset();

                if (category != null) {
                    builder.append(" ▪ ").color(ChatColor.DARK_GRAY)
                            .append("Catégorie: ").color(ChatColor.GRAY)
                            .append(category.name()).color(ChatColor.AQUA).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Id: §b" + category.id())))
                            .append("\n").reset();
                }

                builder.append(" ▪ ").color(ChatColor.DARK_GRAY)
                        .append("Description: ").color(ChatColor.GRAY)
                        .append(option.description()).color(ChatColor.WHITE);
            });
        } else {
            this.sendMessage(builder -> builder.append("§7La §bconfiguration §7de la map est §aterminée§7."));
            Title.sendTitle(this.player, ChatColor.GREEN + Symbols.TICK_BOLD +  " Terminée", "La configuration est prête à l'utilisation", 5, 3 * 20, 10);

            HyriBuild.get().getConfigManager().deleteProcess(this);

            this.finishCallback.accept(this.config);
        }
    }

    /**
     * Write a value for a step's field
     *
     * @param step The step
     * @param object The object with the field
     * @param value The value to write
     */
    private void write(ConfigStep step, Object object, Object value) {
        try {
            final Field field = step.getField();

            if (field == null) {
                return;
            }

            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send a config message to the player
     *
     * @param builderConsumer The consumer used to build the message
     */
    public void sendMessage(Consumer<ComponentBuilder> builderConsumer) {
        final ComponentBuilder builder = new ComponentBuilder(Symbols.HYPHENS_LINE + "\n").strikethrough(true).color(ChatColor.DARK_GRAY);

        builder.append("").strikethrough(false);

        builderConsumer.accept(builder);

        builder.strikethrough(false)
                .append("\n" + Symbols.HYPHENS_LINE).strikethrough(true).color(ChatColor.DARK_GRAY);

        this.player.spigot().sendMessage(builder.create());
    }

    /**
     * Set the finish callback that will be run when the process will end
     *
     * @param finishCallback The callback
     * @return This {@linkplain ConfigProcess process} instance
     */
    public ConfigProcess<T> withFinishCallback(Consumer<T> finishCallback) {
        this.finishCallback = finishCallback;
        return this;
    }

    /**
     * Get the player that started the process
     *
     * @return A {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the current running step.<br>
     * It can be null if the process has ended
     *
     * @return A {@link ConfigStep}
     */
    public ConfigStep currentStep() {
        return this.currentStep;
    }

    /**
     * Add a step to run during the process.<br>
     *
     * @throws IllegalStateException If the process has already started it will throw an exception
     * @param step The {@link ConfigStep} to add
     */
    public void addStep(ConfigStep step) {
        if (this.started) {
            throw new IllegalStateException("Cannot add more steps to configure after the process has started!");
        }

        this.steps.add(step);
    }

    /**
     * Get all the steps that will be run during the process.<br>
     * This queue changes each time a step is run because it will remove it.
     *
     * @return A queue of {@link ConfigStep}
     */
    public Queue<ConfigStep> getSteps() {
        return this.steps;
    }

    /**
     * Check whether the process has started or not
     *
     * @return <code>true</code> if yes
     */
    public boolean isStarted() {
        return this.started;
    }

    /**
     * Get the initial size of the process
     *
     * @return A number representing the size
     */
    public int initialSize() {
        return this.initialSize;
    }
    
}
