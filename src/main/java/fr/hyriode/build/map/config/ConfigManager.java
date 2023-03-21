package fr.hyriode.build.map.config;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.build.HyriBuild;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by AstFaster
 * on 01/06/2022 at 13:49
 */
public class ConfigManager {

    private final List<ConfigProcess<?>> processes = new ArrayList<>();

    public ConfigManager() {
        HyriBuild.get().getServer().getPluginManager().registerEvents(new Handler(), HyriBuild.get());
    }

    public <T extends IHyriConfig> ConfigProcess<T> initProcess(Player player, Class<T> configClass) {
        if (this.getProcess(player.getUniqueId()) != null) {
            throw new RuntimeException(player.getName() + "(" + player.getUniqueId().toString() + ") is already running a process!");
        }

        try {
            final Constructor<T> emptyConstructor = configClass.getConstructor();
            final T config = emptyConstructor.newInstance();
            final ConfigData configData = config.getClass().getAnnotation(ConfigData.class);

            if (configData == null) {
                throw new RuntimeException("Configuration class must have the ConfigData annotation!");
            }

            final ConfigProcess<T> process = new ConfigProcess<>(player, config, configData);

            this.loadFields(process, config, null, null, this.getAllFields(configClass));

            this.processes.add(process);

            process.start();

            return process;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Auto-configuration creation requires an empty constructor in the config class!");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFields(ConfigProcess<?> process, IHyriConfig config, ConfigOptionCategory category, Object categoryObject, List<Field> fields) {
        try {
            for (Field field : fields) {
                final ConfigOptionCategory nestedCategory = field.getAnnotation(ConfigOptionCategory.class);

                if (nestedCategory != null) {
                    field.setAccessible(true);

                    final Object nestedCategoryObject = field.get(config);
                    final Class<?> clazz = nestedCategoryObject.getClass();

                    this.loadFields(process, config, nestedCategory, nestedCategoryObject, this.getAllFields(clazz));
                    continue;
                }

                final ConfigOption option = field.getAnnotation(ConfigOption.class);

                if (option == null) {
                    continue;
                }

                process.addStep(new ConfigStep(category, categoryObject, option, field));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Field> getAllFields(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        final List<Field> result = new ArrayList<>(this.getAllFields(clazz.getSuperclass()));

        Collections.addAll(result, clazz.getDeclaredFields());

        return result;
    }

    public ConfigProcess<?> getProcess(UUID player) {
        for (ConfigProcess<?> process : this.processes) {
            if (process.getPlayer().getUniqueId().equals(player)) {
                return process;
            }
        }
        return null;
    }

    public void deleteProcess(ConfigProcess<?> process) {
        this.processes.remove(process);
    }

    public List<ConfigProcess<?>> getProcesses() {
        return this.processes;
    }

    private class Handler implements Listener {

        @EventHandler(priority = EventPriority.LOWEST)
        public void onQuit(PlayerQuitEvent event) {
            final Player player = event.getPlayer();
            final ConfigProcess<?> process = getProcess(player.getUniqueId());

            if (process == null) {
                return;
            }

            processes.remove(process);
        }

        @EventHandler
        public void onClick(InventoryClickEvent event) {
            final Player player = (Player) event.getWhoClicked();
            final ConfigProcess<?> process = getProcess(player.getUniqueId());

            if (process == null) {
                return;
            }

            event.setCancelled(true);
        }

        @EventHandler
        public void onPickup(PlayerPickupItemEvent event) {
            final Player player = event.getPlayer();
            final ConfigProcess<?> process = getProcess(player.getUniqueId());

            if (process == null) {
                return;
            }

            event.setCancelled(true);
        }

    }

}
