package fr.hyriode.build.map;

import fr.hyriode.api.config.IHyriConfig;
import fr.hyriode.build.HyriBuild;
import fr.hyriode.build.map.config.ConfigProcess;
import fr.hyriode.build.map.config.models.*;
import fr.hyriode.build.map.config.models.getdown.GDDeathmatchConfig;
import fr.hyriode.build.map.config.models.getdown.GDJumpConfig;
import fr.hyriode.build.map.config.models.getdown.GetDownConfig;
import fr.hyriode.build.util.BuildHead;
import fr.hyriode.hyrame.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by AstFaster
 * on 23/02/2023 at 15:12
 */
public enum Category {

    // Games
    BED_WARS("BedWars", "bedwars", Arrays.asList("SOLO", "DOUBLES", "TRIO", "SQUAD", "ONE_ONE"),
            new ItemStack(Material.BED),
            null),
    PEARL_CONTROL("PearlControl", "pearlcontrol", Collections.singletonList("NORMAL"),
            new ItemStack(Material.ENDER_PEARL),
            null),
    BRIDGER("Bridger", "bridger", Arrays.asList("SHORT", "NORMAL", "DIAGONAL"),
            new ItemStack(Material.SANDSTONE),
            BridgerConfig.class),
    SHEEP_WARS("SheepWars", "sheepwars", Collections.singletonList("FIVE_FIVE"),
            new ItemStack(Material.WOOL),
            SheepWarsConfig.class),
    LASER_GAME("LaserGame", "lasergame", Collections.singletonList("FIVE_FIVE"),
            new ItemStack(Material.IRON_HOE),
            null),
    THE_RUNNER("TheRunner", "therunner", Arrays.asList("SOLO", "DOUBLES"),
            new ItemStack(Material.DIAMOND_BOOTS),
            TheRunnerConfig.class),
    RUSH_THE_FLAG("RushTheFlag", "rushtheflag", Arrays.asList("SOLO", "DOUBLES", "MDT"),
            new ItemStack(Material.BANNER, 1, (short) 15),
            RushTheFlagConfig.class),

    // GetDown part

    GET_DOWN("GetDown", "getdown", Collections.singletonList("NORMAL"),
            new ItemStack(Material.SEA_LANTERN),
            GetDownConfig.class),
    GET_DOWN_JUMP("GetDown - Jump", "getdown", Collections.singletonList("maps-jump"),
            new ItemStack(Material.SEA_LANTERN),
            GDJumpConfig.class),
    GET_DOWN_DEATHMATCH("GetDown - Deathmatch", "getdown", Collections.singletonList("maps-deathmatches"),
            new ItemStack(Material.SEA_LANTERN),
            GDDeathmatchConfig.class),

    //

    MOUTRON("Moutron", "moutron", Collections.singletonList("SOLO"),
            ItemBuilder.asHead(BuildHead.BLUE_SHEEP).build(),
            MoutronConfig.class),
    PITCH_OUT("PitchOut", "pitchout", Collections.singletonList("SOLO"),
            new ItemStack(Material.SNOW_BALL),
            null),

    // Others
    LOBBY("Lobby", "lobby", null, new ItemStack(Material.NETHER_STAR), false, null)

    ;

    private final String display;
    private final String database;
    private final List<String> types;
    private final ItemStack icon;
    private final boolean game;

    private final Class<? extends IHyriConfig> configClass;

    Category(String display, String database, List<String> types, ItemStack icon, boolean game, Class<? extends IHyriConfig> configClass) {
        this.display = display;
        this.database = database;
        this.types = types;
        this.icon = icon;
        this.game = game;
        this.configClass = configClass;
    }

    Category(String display, String database, List<String> types, ItemStack icon, Class<? extends IHyriConfig> configClass) {
        this(display, database, types, icon, true, configClass);
    }

    public String getDisplay() {
        return this.display;
    }

    public String getDatabase() {
        return this.database;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public ItemStack getIcon() {
        return this.icon.clone();
    }

    public boolean isGame() {
        return this.game;
    }

    public Class<? extends IHyriConfig> getConfigClass() {
        return this.configClass;
    }

    public boolean hasConfig() {
        return this.configClass != null;
    }

    @SuppressWarnings("unchecked")
    public <T extends IHyriConfig> ConfigProcess<T> newConfigProcess(Player player) {
        return (ConfigProcess<T>) HyriBuild.get().getConfigManager().initProcess(player, this.configClass);
    }

}
