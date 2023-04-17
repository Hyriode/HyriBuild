package fr.hyriode.build.map;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.models.*;
import fr.hyriode.build.map.config.models.bedwars.*;
import fr.hyriode.build.map.config.models.getdown.GDDeathmatchConfig;
import fr.hyriode.build.map.config.models.getdown.GDJumpConfig;
import fr.hyriode.build.map.config.models.getdown.GetDownConfig;
import fr.hyriode.build.util.BuildHead;
import fr.hyriode.hyrame.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by AstFaster
 * on 23/02/2023 at 15:12
 */
public enum Category {

    // Games
    BED_WARS("BedWars", "bedwars",
            new ItemStack(Material.BED),
            types -> {
                types.put("SOLO", BedWarsConfig_SOLO.class);
                types.put("DOUBLES", BedWarsConfig_DOUBLES.class);
                types.put("TRIO", BedWarsConfig_TRIO.class);
                types.put("SQUAD", BedWarsConfig_SQUAD.class);
                types.put("ONE_ONE", BedWarsConfig_ONE_ONE.class);
            }),
    PEARL_CONTROL("PearlControl", "pearlcontrol",
            new ItemStack(Material.ENDER_PEARL),
            types -> types.put("NORMAL", PearlControlConfig.class)),
    BRIDGER("Bridger", "bridger",
            new ItemStack(Material.SANDSTONE),
            types -> {
                types.put("SHORT", BridgerConfig.class);
                types.put("NORMAL", BridgerConfig.class);
                types.put("DIAGONAL", BridgerConfig.class);
            }),
    SHEEP_WARS("SheepWars", "sheepwars",
            new ItemStack(Material.WOOL),
            types -> types.put("FIVE_FIVE", SheepWarsConfig.class)),
    LASER_GAME("LaserGame", "lasergame",
            new ItemStack(Material.IRON_HOE),
            types -> types.put("FIVE_FIVE", LaserGameConfig.class)),
    THE_RUNNER("TheRunner", "therunner",
            new ItemStack(Material.DIAMOND_BOOTS),
            types -> {
                types.put("SOLO", TheRunnerConfig.class);
                types.put("DOUBLES", TheRunnerConfig.class);
            }),
    RUSH_THE_FLAG("RushTheFlag", "rushtheflag",
            new ItemStack(Material.BANNER, 1, (short) 15),
            types -> {
                types.put("SOLO", RushTheFlagConfig.class);
                types.put("DOUBLES", RushTheFlagConfig.class);
                types.put("MDT", RushTheFlagConfig.class);
            }),
    GET_DOWN("GetDown", "getdown",
            new ItemStack(Material.SEA_LANTERN),
            types -> {
                types.put("NORMAL", GetDownConfig.class);
                types.put("maps-jump", GDJumpConfig.class);
                types.put("maps-deathmatches", GDDeathmatchConfig.class);
            }),
    MOUTRON("Moutron", "moutron",
            ItemBuilder.asHead(BuildHead.BLUE_SHEEP).build(),
            types -> types.put("SOLO", MoutronConfig.class)),
    PITCH_OUT("PitchOut", "pitchout",
            new ItemStack(Material.SNOW_BALL),
            types -> types.put("SOLO", null)),

    // Others
    LOBBY("Lobby", "lobby", new ItemStack(Material.NETHER_STAR), false, types -> types.put("DEFAULT", null))

    ;

    private final String display;
    private final String database;
    private final ItemStack icon;
    private final boolean game;

    private final Map<String, Class<? extends IHyriConfig>> types;

    Category(String display, String database, ItemStack icon, boolean game, Consumer<Map<String, Class<? extends IHyriConfig>>> types) {
        this.display = display;
        this.database = database;
        this.types = new LinkedHashMap<>();
        this.icon = icon;
        this.game = game;

        types.accept(this.types);
    }

    Category(String display, String database, ItemStack icon, Consumer<Map<String, Class<? extends IHyriConfig>>> types) {
        this(display, database, icon, true, types);
    }

    public String getDisplay() {
        return this.display;
    }

    public String getDatabase() {
        return this.database;
    }

    public List<String> getTypes() {
        return new ArrayList<>(this.types.keySet());
    }

    public ItemStack getIcon() {
        return this.icon.clone();
    }

    public boolean isGame() {
        return this.game;
    }

    public Class<? extends IHyriConfig> getConfigClass(String type) {
        return this.types.get(type);
    }

    public boolean hasConfig(String type) {
        return this.types.get(type) != null;
    }

    @SuppressWarnings("unchecked")
    public <T extends IHyriConfig> ConfigProcess<T> newConfigProcess(String type, Player player) {
        return (ConfigProcess<T>) HyriBuild.get().getConfigManager().initProcess(player, this.types.get(type));
    }

}
